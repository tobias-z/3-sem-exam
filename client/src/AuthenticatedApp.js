import { Route, Switch } from "react-router-dom";
import { ProjectProvider } from "./context/ProjectsContext";
import NoMatch from "./routes/404";
import AdminPage from "./routes/admin/AdminPage";
import HomePage from "./routes/HomePage";

export default function AuthenticatedApp() {
  return (
    <>
      <Switch>
        <Route path="/" exact component={HomePage} />
        <ProjectProvider>
          <Route path="/admin" component={AdminPage} />
        </ProjectProvider>
        <Route path="/" component={NoMatch} />
      </Switch>
    </>
  );
}
