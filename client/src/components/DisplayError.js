import { Alert } from "react-bootstrap";

export default function DisplayError({ error }) {
  return (
    <>
      {error && (
        <Alert variant="danger">
          <h3>Error</h3>
          <p>{error.message}</p>
        </Alert>
      )}
    </>
  );
}
