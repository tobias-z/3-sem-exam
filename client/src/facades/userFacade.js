import { fetchData, https } from "../apiUtils";
import { USER, INFO } from "../settings";

async function login(credentials) {
  let data = await fetchData(USER.LOGIN, https.POST, credentials);
  setToken(data.token);
  return data;
}

function getDataFromServer() {
  return fetchData(INFO.USER);
}

let setToken = token => localStorage.setItem("jwtToken", token);
let getToken = () => localStorage.getItem("jwtToken");
let isLoggedIn = () => getToken() != null;
let logout = () => localStorage.removeItem("jwtToken");

export { setToken, getToken, isLoggedIn, login, logout, getDataFromServer };
