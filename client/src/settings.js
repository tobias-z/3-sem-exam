let BASE_URL = "http://localhost:8080/time-tracker/api";

let USER = {
  LOGIN: `${BASE_URL}/login`,
  VALIDATE_TOKEN: `${BASE_URL}/login/validate-token`,
};

let PROJECT = {
  ALL_PROJECTS: `${BASE_URL}/projects`,
  ADD_PROJECT: `${BASE_URL}/projects`,
};

export { USER, PROJECT };
