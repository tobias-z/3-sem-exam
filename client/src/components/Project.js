import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

export default function Project({ projects }) {
  let { projectId } = useParams();
  let [project, setProject] = useState(null);

  useEffect(() => {
    setProject(() =>
      projects.find(project => Number(project.id) === Number(projectId))
    );
  }, [projectId, projects]);

  return (
    <>
      {project ? (
        <section>
          <div className="text-center">
            <h3>{project.name}</h3>
            <p>{project.description}</p>
          </div>
        </section>
      ) : (
        <h3>Unable to find project with id: {projectId}</h3>
      )}
    </>
  );
}
