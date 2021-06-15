import { createContext, useContext } from "react";
import { createUserObject, useUser } from "./UserProvider";
import * as facade from "../facades/userFacade";
import { useHistory } from "react-router";
import { handleError } from "../apiUtils";
import { useMutation } from "../hooks/promise";

let AuthContext = createContext({
  login: async (userCredentials, setError) => {},
  logout: () => {},
});

export let useAuth = () => useContext(AuthContext);

export function AuthProvider({ children }) {
  let { setUser } = useUser();
  let { push } = useHistory();
  let { run } = useMutation();

  async function login(userCredentials, setError) {
    run(() => facade.login(userCredentials), {
      onSuccess(data) {
        push("/");
        setUser(createUserObject(data));
      },
      onError(err) {
        handleError(err, setError);
      },
    });
  }

  function logout() {
    facade.logout();
    setUser(null);
  }

  let values = {
    login,
    logout,
  };

  return <AuthContext.Provider value={values}>{children}</AuthContext.Provider>;
}
