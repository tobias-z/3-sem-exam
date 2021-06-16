import { useEffect, useState } from "react";
import { Button, Card } from "react-bootstrap";
import { useParams } from "react-router-dom";
import { useUser } from "../../context/UserProvider";
import MyInput from "../../components/MyInput";
import { useMutation } from "../../hooks/promise";
import { fetchData, https } from "../../apiUtils";
import { PROJECT_USER_HOURS } from "../../settings";

export default function UserProject({ projects, run: reFetchProjects }) {
  let { user } = useUser();
  let { projectId } = useParams();
  let [project, setProject] = useState(null);
  let [hoursWorked, setHoursWorked] = useState(0);
  let { run } = useMutation();

  useEffect(() => {
    let tmpProject = projects.find(p => Number(p.id) === Number(projectId));
    tmpProject.developers = tmpProject.developers.filter(
      dev => dev.name === user.username
    );
    setProject(tmpProject);
  }, [projectId, projects, user.username]);

  function handleEditHours() {
    run(
      () =>
        fetchData(
          PROJECT_USER_HOURS.EDIT_HOURS(projectId, hoursWorked),
          https.PUT
        ),
      {
        afterRun() {
          reFetchProjects.current();
        },
      }
    );
  }

  return (
    <section>
      {project ? (
        <>
          <div className="text-center">
            <h3>{project.name}</h3>
            <h6>{project.description}</h6>
          </div>
          <div className="d-flex justify-content-end">
            <h5>
              Hours spend on all user stories:{" "}
              {project.developers.reduce((state, dev) => {
                return state + dev.hoursSpent;
              }, 0)}
            </h5>
          </div>
          <hr />
          <div className="d-flex flex-wrap flex-column justify-content-around mt-5">
            {project.developers.map(dev => (
              <Card key={dev.userStory} style={{ width: "24rem" }}>
                <Card.Header className="text-center">
                  <Card.Title>{dev.userStory}</Card.Title>
                  <Card.Subtitle className="mb-2 text-muted">
                    {dev.description}
                  </Card.Subtitle>
                </Card.Header>
                <Card.Body>
                  <Card.Text>Name: {dev.name}</Card.Text>
                  <Card.Text>Email: {dev.email}</Card.Text>
                  <Card.Text>Phone: {dev.phone}</Card.Text>
                  <Card.Text>
                    Billing per hour: {dev.billingPerHour} $
                  </Card.Text>
                  <Card.Text>
                    Hours spent on project: {dev.hoursSpent}
                  </Card.Text>
                  <MyInput
                    type="number"
                    label="Hours worked today"
                    value={hoursWorked}
                    onChange={e => setHoursWorked(e.target.value)}
                  />
                  <div className="d-flex" style={{ gap: "5px" }}>
                    <Button
                      variant="secondary"
                      type="submit"
                      onClick={handleEditHours}>
                      Add to hours worked
                    </Button>
                    <Button variant="success" type="submit">
                      Complete user story
                    </Button>
                  </div>
                </Card.Body>
              </Card>
            ))}
          </div>
        </>
      ) : (
        <h3>No project with that id was found</h3>
      )}
    </section>
  );
}
