import { useState } from "react";
import { Button, Form } from "react-bootstrap";
import CenteredContainer from "../components/CenteredContainer";
import Layout from "../components/Layout";
import { useAuth } from "../context/AuthProvider";

let initialValues = {
  username: "",
  password: "",
};

export default function LoginPage() {
  let [loginCredentials, setLoginCredentials] = useState(initialValues);
  let [serverError, setServerError] = useState(null);
  let { login } = useAuth();

  function handleSubmit(event) {
    event.preventDefault();
    login(loginCredentials, setServerError);
    setLoginCredentials(initialValues);
  }

  function handleChange(event) {
    setLoginCredentials({
      ...loginCredentials,
      [event.target.name]: event.target.value,
    });
  }

  return (
    <Layout noHeader>
      <CenteredContainer>
        <h1>Sign in</h1>
        <Form style={{ width: "400px" }} onSubmit={handleSubmit}>
          {serverError ? (
            <h3 className="text-danger">{serverError.message}</h3>
          ) : null}
          <Form.Group controlId="username">
            <Form.Label>Username</Form.Label>
            <Form.Control
              type="text"
              name="username"
              value={loginCredentials.username}
              onChange={handleChange}
              placeholder="Enter username"
            />
          </Form.Group>

          <Form.Group controlId="formBasicPassword">
            <Form.Label>Password</Form.Label>
            <Form.Control
              type="password"
              name="password"
              value={loginCredentials.password}
              onChange={handleChange}
              placeholder="Enter password"
            />
          </Form.Group>
          <Button block type="submit">
            Sign in
          </Button>
        </Form>
      </CenteredContainer>
    </Layout>
  );
}
