import { useEffect, useState } from "react";
import { Button, Form, Table } from "react-bootstrap";
import { useParams } from "react-router-dom";
import { fetchData, https } from "../apiUtils";
import { useProjects } from "../context/ProjectsContext";
import { useMutation, useQuery } from "../hooks/promise";
import { DEVELOPER, PROJECT } from "../settings";
import DisplayError from "./DisplayError";

export default function Project({ projects }) {
  let { projectId } = useParams();
  let [project, setProject] = useState(null);
  let { value: developers, error: developerError } = useQuery(
    DEVELOPER.ALL_DEVELOPERS
  );

  useEffect(() => {
    setProject(() =>
      projects.find(project => Number(project.id) === Number(projectId))
    );
  }, [projectId, projects]);

  return (
    <>
      <DisplayError error={developerError} />
      {project ? (
        <section>
          <div className="d-flex justify-content-around">
            <div>
              <h3>{project.name}</h3>
              <p>{project.description}</p>
            </div>
            <div>
              {developers && (
                <AddDeveloperForm
                  developers={developers}
                  projectId={projectId}
                />
              )}
            </div>
          </div>
          <div>
            <h4>Active developers on project</h4>
            {project.developers[0] ? (
              <Table striped bordered hover>
                <thead>
                  <tr>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Billing per hour</th>
                  </tr>
                </thead>
                <tbody>
                  {project.developers.map(dev => (
                    <tr key={dev.name}>
                      <td>{dev.name}</td>
                      <td>{dev.email}</td>
                      <td>{dev.phone}</td>
                      <td>{dev.billingPerHour}</td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            ) : (
              <p>No active developers were found. Go ahead and add them!</p>
            )}
          </div>
        </section>
      ) : (
        <h3>Unable to find project with id: {projectId}</h3>
      )}
    </>
  );
}

function AddDeveloperForm({ developers, projectId }) {
  let [choosenDeveloper, setChoosenDeveloper] = useState(developers[0].name);
  let { run, error, value } = useMutation();
  let { run: reFetchProjects } = useProjects();

  function handleAddDeveloper(e) {
    e.preventDefault();
    run(
      () =>
        fetchData(
          PROJECT.ADD_DEVELOPER(choosenDeveloper, projectId),
          https.PUT
        ),
      {
        onSuccess() {
          reFetchProjects();
        },
      }
    );
    setChoosenDeveloper(developers[0]);
  }

  return (
    <>
      <DisplayError error={error} />
      {value && <pre>{JSON.stringify(value, null, 2)}</pre>}
      <Form onSubmit={handleAddDeveloper}>
        <Form.Group controlId="add-developer-select">
          <Form.Label>Developers available</Form.Label>
          <Form.Control
            as="select"
            value={choosenDeveloper}
            onChange={e => setChoosenDeveloper(e.target.value)}>
            {developers.map(user => (
              <option key={user.email}>{user.name}</option>
            ))}
          </Form.Control>
        </Form.Group>
        <Button size="sm" variant="success" type="submit">
          Add developer
        </Button>
      </Form>
    </>
  );
}
