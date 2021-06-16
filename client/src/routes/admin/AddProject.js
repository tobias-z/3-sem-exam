import { Alert } from "react-bootstrap";
import { Button, Form } from "react-bootstrap";
import { fetchData, https } from "../../apiUtils";
import DisplayError from "../../components/DisplayError";
import MyInput from "../../components/MyInput";
import { useProjects } from "../../context/ProjectsContext";
import { useMutation } from "../../hooks/promise";
import useForm from "../../hooks/useForm";
import { PROJECT } from "../../settings";

let initialValues = {
  name: "",
  description: "",
};

export default function AddProject() {
  let { values, handleChange, resetForm } = useForm(initialValues);
  let { value: project, run, error } = useMutation();
  let { run: reFetchProjects } = useProjects();

  function handleSubmit(e) {
    e.preventDefault();
    run(() => fetchData(PROJECT.ADD_PROJECT, https.POST, values), {
      onSuccess() {
        reFetchProjects();
      },
    });
    resetForm();
  }

  return (
    <>
      <h2 className="text-center">Add Project</h2>
      <DisplayError error={error} />
      {project && (
        <Alert variant="success">
          <h3>Successfully added project</h3>
          <hr />
          <p>Name: {project.name}</p>
          <p>Description: {project.description}</p>
        </Alert>
      )}
      <Form onSubmit={handleSubmit}>
        <MyInput
          name="name"
          onChange={handleChange}
          value={values.name}
          label="Project Name"
        />
        <MyInput
          name="description"
          onChange={handleChange}
          value={values.description}
          label="Description"
        />
        <div className="d-flex justify-content-end">
          <Button type="submit" size="lg">
            Add Project
          </Button>
        </div>
      </Form>
    </>
  );
}
