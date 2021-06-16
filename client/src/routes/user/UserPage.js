import DisplayError from "../../components/DisplayError";
import { Route, useHistory } from "react-router-dom";
import Layout from "../../components/Layout";
import { useUser } from "../../context/UserProvider";
import { useQuery } from "../../hooks/promise";
import { PROJECT } from "../../settings";
import UserProject from "./UserProject";
import { Row, Col } from "react-bootstrap";

export default function UserPage() {
  let { user } = useUser();
  let { push } = useHistory();
  let { value, error, run } = useQuery(
    PROJECT.GET_DEVELOPERS_PROJECTS(user.username)
  );

  return (
    <Layout>
      <h1 className="text-center">Profile for {user.username}</h1>
      <hr />
      <DisplayError error={error} />
      {value && (
        <Row className="mt-4">
          <Col md="2">
            <ul>
              {value.projects.map(project => (
                <li
                  className="text-primary h5"
                  style={{ cursor: "pointer" }}
                  key={project.id}
                  onClick={() => push(`/user/${project.id}`)}>
                  {project.name}
                </li>
              ))}
            </ul>
          </Col>
          <Col>
            <Route path="/user/:projectId">
              <UserProject run={run} projects={value.projects} />
            </Route>{" "}
          </Col>
        </Row>
      )}
    </Layout>
  );
}
