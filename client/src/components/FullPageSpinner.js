import { Spinner } from "react-bootstrap";
import CenteredContainer from "./CenteredContainer";

export default function FullPageSpinner() {
  return (
    <CenteredContainer>
      <Spinner animation="border" variant="primary" role="status">
        <span className="sr-only">Loading...</span>
      </Spinner>
    </CenteredContainer>
  );
}
