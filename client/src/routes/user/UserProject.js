import { useEffect, useState } from "react";
import { Card } from "react-bootstrap";
import { useParams } from "react-router-dom";
import { useUser } from "../../context/UserProvider";

export default function UserProject({ projects }) {
  let { user } = useUser();
  let { projectId } = useParams();
  let [project, setProject] = useState(null);

  useEffect(() => {
    let tmpProject = projects.find(p => Number(p.id) === Number(projectId));
    tmpProject.developers = tmpProject.developers.filter(
      dev => dev.name === user.username
    );
    setProject(tmpProject);
  }, [projectId, projects, user.username]);

  return (
    <section>
      {project ? (
        <>
          <div className="text-center">
            <h3>{project.name}</h3>
            <h6>{project.description}</h6>
          </div>
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
                  <Card.Link href="#">Card Link</Card.Link>
                  <Card.Link href="#">Another Link</Card.Link>
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
