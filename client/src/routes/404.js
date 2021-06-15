import { useRouteMatch } from "react-router";
import Layout from "../components/Layout";

export default function NoMatch() {
  let { path } = useRouteMatch();
  return (
    <Layout>
      <h1>404: The path {path} is not a known route</h1>
    </Layout>
  );
}
