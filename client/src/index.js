import ReactDOM from "react-dom";
import App from "./App";
import "bootstrap/dist/css/bootstrap.min.css";
import AppProvider from "./context/AppProvider";
import { BrowserRouter as Router } from "react-router-dom";

ReactDOM.render(
  <Router>
    <AppProvider>
      <App />
    </AppProvider>
  </Router>,
  document.getElementById("root")
);
