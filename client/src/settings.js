let BASE_URL = "http://localhost:8080/starter/api";

let USER = {
  LOGIN: `${BASE_URL}/login`,
  VALIDATE_TOKEN: `${BASE_URL}/login/validate-token`,
};

let INFO = {
  USER: `${BASE_URL}/info/user`,
  ADMIN: `${BASE_URL}/info/admin`,
  FETCH_MANY: `${BASE_URL}/info/fetchMany`,
  FETCH_ONE: `${BASE_URL}/info/fetchData`,
};

export { USER, INFO };
