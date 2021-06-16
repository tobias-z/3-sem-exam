import { Switch, Route } from "react-router-dom";
import NoMatch from "./routes/404";
import LoginPage from "./routes/LoginPage";

export default function UnauthenticatedApp() {
  return (
    <>
      <Switch>
        <Route path="/" exact component={LoginPage} />
        <Route path="/" component={NoMatch} />
      </Switch>
    </>
  );
}
