package security;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dtos.user.RoleDTO;
import dtos.user.UserDTO;
import facades.UserFacade;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import rest.provider.Provider;
import security.errorhandling.AuthenticationException;
import utils.SetupTestUsers;
import utils.errorhandling.API_Exception;
import utils.errorhandling.GenericExceptionMapper;

@Path("login")
public class LoginEndpoint extends Provider {

    public static final int TOKEN_EXPIRE_TIME = 1000 * 60 * 30; //30 min
    public static final UserFacade USER_FACADE = UserFacade.getUserFacade(EMF);

    @GET
    @Path("/generate")
    public String generateUsers() {
        boolean isGenerated = SetupTestUsers.generateUsers();
        if (!isGenerated) {
            throw new WebApplicationException("Failed generation", 500);
        }
        return "{\"msg\": \"Yay it worked\"}";
    }

    @GET
    @Path("/validate-token")
    public Response validateToken(@Context ContainerRequestContext request) {
        String token = request.getHeaderString("x-access-token");
        if (token == null)
            throw new WebApplicationException("No token found", 403);
        try {
            UserPrincipal principal = JWTAuthenticationFilter.getUserPrincipalFromTokenIfValid(token);
            JsonObject responseJson = new JsonObject();
            responseJson.addProperty("username", principal.getName());
            responseJson.addProperty("roles", GSON.toJson(principal.getRoleDTOS()));
            responseJson.addProperty("token", token);
            return Response.ok(GSON.toJson(responseJson)).build();
        } catch (ParseException | JOSEException | AuthenticationException e) {
            throw new WebApplicationException("Invalid token", 403);
        }
    }

    @POST
    public Response login(String jsonString) throws AuthenticationException, API_Exception {
        String username;
        String password;
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            username = json.get("username").getAsString();
            password = json.get("password").getAsString();
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }

        try {
            UserDTO user = USER_FACADE.getVerifiedUser(username, password);
            String token = createToken(username, user.getRolesAsStrings(), user.getRoles());
            JsonObject responseJson = new JsonObject();
            responseJson.addProperty("username", username);
            responseJson.addProperty("roles", GSON.toJson(user.getRoles()));
            responseJson.addProperty("token", token);
            return Response.ok(GSON.toJson(responseJson)).build();

        } catch (JOSEException | AuthenticationException ex) {
            if (ex instanceof AuthenticationException) {
                throw (AuthenticationException) ex;
            }
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new AuthenticationException("Invalid username or password! Please try again");
    }

    private String createToken(String userName, List<String> roles, List<RoleDTO> roleDTOS) throws JOSEException {
        StringBuilder res = new StringBuilder();
        for (String string : roles) {
            res.append(string);
            res.append(",");
        }
        String rolesAsString = res.length() > 0 ? res.substring(0, res.length() - 1) : "";
        String issuer = "semesterstartcode-dat3";

        JWSSigner signer = new MACSigner(SharedSecret.getSharedKey());
        Date date = new Date();
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
            .subject(userName)
            .claim("username", userName)
            .claim("roles", rolesAsString)
            .claim("myRoles", roleDTOS)
            .claim("issuer", issuer)
            .issueTime(date)
            .expirationTime(new Date(date.getTime() + TOKEN_EXPIRE_TIME))
            .build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }
}
