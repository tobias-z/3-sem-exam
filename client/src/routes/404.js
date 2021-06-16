import { useRouteMatch } from "react-router-dom";
import Layout from "../components/Layout";

export default function NoMatch() {
  let { url } = useRouteMatch();
  return (
    <Layout>
      <h1>404: The url {url} is not a known route</h1>
    </Layout>
  );
}
