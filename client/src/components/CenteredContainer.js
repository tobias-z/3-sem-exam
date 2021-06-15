import { Container } from "react-bootstrap";

export default function CenteredContainer({ children }) {
  return (
    <Container
      className="d-flex justify-content-center align-items-center"
      style={{ minHeight: "90vh", flexDirection: "column" }}>
      {children}
    </Container>
  );
}
