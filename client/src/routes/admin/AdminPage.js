import Layout from "../../components/Layout";
import { Nav } from "react-bootstrap";
import { Route, Switch } from "react-router-dom";
import { LinkContainer } from "react-router-bootstrap";
import Projects from "./Projects";
import AddProject from "./AddProject";

function AdminPageNav() {
  return (
    <Nav className="justify-content-center" activeKey="/admin">
      <LinkContainer exact to="/admin">
        <Nav.Link>Startpage</Nav.Link>
      </LinkContainer>
      <LinkContainer exact to="/admin/projects">
        <Nav.Link>All Projects</Nav.Link>
      </LinkContainer>
      <LinkContainer exact to="/admin/add-project">
        <Nav.Link>Add Project</Nav.Link>
      </LinkContainer>
    </Nav>
  );
}

export default function AdminPage() {
  return (
    <Layout>
      <h1 className="text-center">Project management</h1>
      <hr />
      <AdminPageNav />
      <div className="mt-4">
        <Switch>
          <Route path="/admin/projects" component={Projects} />
          <Route exact path="/admin/add-project" component={AddProject} />
          <Route path="/admin">
            <h4 className="mt-4 text-center">
              This is the admin dashbard, pick any management you'd like
            </h4>
          </Route>
        </Switch>
      </div>
    </Layout>
  );
}
