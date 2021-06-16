import { Form } from "react-bootstrap";

export default function MyInput({ name, label, ...props }) {
  return (
    <Form.Group controlId={name}>
      <Form.Label>{label && label}</Form.Label>
      <Form.Control name={name} {...props} />
    </Form.Group>
  );
}
