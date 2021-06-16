let BASE_URL = "https://api.tobias-z.com/exam/api";

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

let PROJECT_USER_HOURS = {
  EDIT_HOURS: (projectId, hoursWorked) =>
    `${BASE_URL}/project-user-hours?projectId=${projectId}&hoursWorked=${hoursWorked}`,
  COMPLETE: projectId => `${BASE_URL}/project-user-hours/${projectId}`,
};

export { USER, PROJECT, DEVELOPER, PROJECT_USER_HOURS };
