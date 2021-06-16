let BASE_URL = "http://localhost:8080/time-tracker/api";

let USER = {
  LOGIN: `${BASE_URL}/login`,
  VALIDATE_TOKEN: `${BASE_URL}/login/validate-token`,
};

let PROJECT = {
  ALL_PROJECTS: `${BASE_URL}/projects`,
  ADD_PROJECT: `${BASE_URL}/projects`,
  ADD_DEVELOPER: (developerName, projectId) =>
    `${BASE_URL}/projects?username=${developerName}&projectId=${projectId}`,
  GET_DEVELOPERS_PROJECTS: developerName =>
    `${BASE_URL}/projects/${developerName}`,
};

let DEVELOPER = {
  ALL_DEVELOPERS: `${BASE_URL}/users/`,
};

export { USER, PROJECT, DEVELOPER };
