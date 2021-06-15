import { Switch, Route } from "react-router-dom";
import NoMatch from "./routes/404";
import HomePage from "./routes/HomePage";
import LoginPage from "./routes/LoginPage";

export default function UnauthenticatedApp() {
  return (
    <>
      <Switch>
        <Route path="/" exact component={HomePage} />
        <Route path="/login" component={LoginPage} />
        <Route path="/" component={NoMatch} />
      </Switch>
    </>
  );
}
