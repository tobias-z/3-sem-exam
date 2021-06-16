import { useEffect, useState } from "react";
import { Alert, Button, Form, Table } from "react-bootstrap";
import { useParams } from "react-router-dom";
import { fetchData, https } from "../apiUtils";
import { useProjects } from "../context/ProjectsContext";
import { useMutation, useQuery } from "../hooks/promise";
import useForm from "../hooks/useForm";
import { DEVELOPER, PROJECT } from "../settings";
import DisplayError from "./DisplayError";
import MyInput from "./MyInput";

export default function Project({ projects }) {
  let { projectId } = useParams();
  let [project, setProject] = useState(null);
  let {
    value: developers,
    error: developerError,
    run,
  } = useQuery(DEVELOPER.ALL_DEVELOPERS);

  useEffect(() => {
    setProject(() =>
      projects.find(project => Number(project.id) === Number(projectId))
    );
  }, [projectId, projects]);

  useEffect(() => {
    run.current();
  }, [run]);

  return (
    <>
      <DisplayError error={developerError} />
      {project ? (
        <section>
          <div className="d-flex justify-content-around">
            <div>
              <h2>{project.name}</h2>
              <h6>{project.description}</h6>
            </div>
            <div>
              {developers && (
                <AddDeveloperForm
                  run={run}
                  developers={developers}
                  projectId={projectId}
                />
              )}
            </div>
          </div>
          <div>
            <h4>Active developers on project</h4>
            {project.developers[0] ? (
              <>
                <Table striped bordered hover>
                  <thead>
                    <tr>
                      <th>Name</th>
                      <th>Email</th>
                      <th>Phone</th>
                      <th>Billing per hour</th>
                      <th>Hours spent</th>
                      <th>User story</th>
                      <th>Description</th>
                    </tr>
                  </thead>
                  <tbody>
                    {project.developers.map(dev => (
                      <tr key={dev.name}>
                        <td>{dev.name}</td>
                        <td>{dev.email}</td>
                        <td>{dev.phone}</td>
                        <td>{dev.billingPerHour}</td>
                        <td>{dev.hoursSpent}</td>
                        <td>{dev.userStory}</td>
                        <td>{dev.description}</td>
                      </tr>
                    ))}
                  </tbody>
                </Table>
                <hr className="my-4" style={{ border: "1px solid gray" }} />
                <div className="d-flex justify-content-end">
                  <h5>
                    Total:{" "}
                    {project.developers.reduce((state, dev) => {
                      return state + dev.hoursSpent * dev.billingPerHour;
                    }, 0)}
                  </h5>
                </div>
              </>
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

let initialState = {
  userStory: "",
  description: "",
};

function AddDeveloperForm({ developers, projectId, run: reRun }) {
  let [choosenDeveloper, setChoosenDeveloper] = useState(developers[0].name);
  let { values, handleChange, resetForm } = useForm(initialState);
  let { run, error, value } = useMutation();
  let { run: reFetchProjects } = useProjects();

  function handleAddDeveloper(e) {
    e.preventDefault();
    run(
      () =>
        fetchData(
          PROJECT.ADD_DEVELOPER(choosenDeveloper, projectId),
          https.PUT,
          values
        ),
      {
        onSuccess() {
          reRun.current();
          reFetchProjects.current();
        },
      }
    );
    setChoosenDeveloper(developers[0].name);
    resetForm();
  }

  return (
    <>
      <DisplayError error={error} />
      {value && (
        <Alert variant="success">
          <h4>Success!</h4>
          <p>Developer was added to your project</p>
        </Alert>
      )}
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
        <MyInput
          name="userStory"
          required
          value={values.userStory}
          onChange={handleChange}
          label="User story"
        />
        <MyInput
          name="description"
          required
          value={values.description}
          onChange={handleChange}
          label="Description"
        />
        <Button block variant="success" type="submit">
          Add developer
        </Button>
      </Form>
    </>
  );
}
