import { Route, Switch } from "react-router-dom";
import { ProjectProvider } from "./context/ProjectsContext";
import NoMatch from "./routes/404";
import AdminPage from "./routes/admin/AdminPage";
import HomePage from "./routes/HomePage";
import UserPage from "./routes/user/UserPage";

export default function AuthenticatedApp() {
  return (
    <>
      <Switch>
        <Route path="/" exact component={HomePage} />
        <Route path="/admin">
          <ProjectProvider>
            <AdminPage />
          </ProjectProvider>
        </Route>
        <Route path="/user" component={UserPage} />
        <Route path="/" component={NoMatch} />
      </Switch>
    </>
  );
}
