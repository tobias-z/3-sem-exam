import Layout from "../components/Layout";
import { Row, Col, Alert } from "react-bootstrap";
import { useQuery } from "../hooks/promise";
import { PROJECT } from "../settings";
import { Route, useHistory } from "react-router-dom";
import Project from "../components/Project";

export default function AdminPage() {
  let { value, error } = useQuery(PROJECT.ALL_PROJECTS);
  let { push } = useHistory();

  return (
    <Layout>
      <h1 className="text-center">Project management</h1>
      <hr />
      {error && (
        <Alert variant="success">
          <h3>Error</h3>
          <p>{error.message}</p>
        </Alert>
      )}
      {value && (
        <Row>
          <Col md="3">
            <ul>
              {value.projects.map(project => (
                <li
                  className="text-primary h5"
                  style={{ cursor: "pointer" }}
                  key={project.id}
                  onClick={() => push(`/admin/${project.id}`)}>
                  {project.name}
                </li>
              ))}
            </ul>
          </Col>
          <Col>
            <Route path="/admin/:projectId" exact>
              <Project projects={value.projects} />
            </Route>
          </Col>
        </Row>
      )}
    </Layout>
  );
}
