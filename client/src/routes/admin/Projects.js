import { Col, Row } from "react-bootstrap";
import { Route, useHistory } from "react-router-dom";
import DisplayError from "../../components/DisplayError";
import Project from "../../components/Project";
import { useProjects } from "../../context/ProjectsContext";

export default function Projects() {
  let { value, error } = useProjects();
  let { push } = useHistory();

  return (
    <>
      <DisplayError error={error} />
      {value && (
        <Row>
          <Col md="3">
            <ul>
              {value.projects.map(project => (
                <li
                  className="text-primary h5"
                  style={{ cursor: "pointer" }}
                  key={project.id}
                  onClick={() => push(`/admin/projects/${project.id}`)}>
                  {project.name}
                </li>
              ))}
            </ul>
          </Col>
          <Col>
            <Route path="/admin/projects/:projectId" exact>
              <Project projects={value.projects} />
            </Route>
          </Col>
        </Row>
      )}
    </>
  );
}
