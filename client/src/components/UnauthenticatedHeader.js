import { Nav, Navbar, Container } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";

function UnauthenticatedHeader() {
  return (
    <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
      <Container>
        <LinkContainer to="/">
          <Navbar.Brand>Time Tracker</Navbar.Brand>
        </LinkContainer>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav>
            <LinkContainer to="/">
              <Nav.Link>Login</Nav.Link>
            </LinkContainer>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default UnauthenticatedHeader;
