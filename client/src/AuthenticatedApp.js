import { Route, Switch } from "react-router-dom";
import NoMatch from "./routes/404";
import AdminPage from "./routes/AdminPage";
import HomePage from "./routes/HomePage";

export default function AuthenticatedApp() {
  return (
    <>
      <Switch>
        <Route path="/" exact component={HomePage} />
        <Route path="/admin" component={AdminPage} />
        <Route path="/" component={NoMatch} />
      </Switch>
    </>
  );
}
