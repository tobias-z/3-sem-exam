import { Jumbotron, Button } from "react-bootstrap";
import { useHistory } from "react-router-dom";
import Layout from "../components/Layout";
import WithAdmin from "../components/WithAdmin";
import WithUser from "../components/WithUser";
import { useUser } from "../context/UserProvider";

export default function HomePage() {
  let { user } = useUser();
  let { push } = useHistory();

  return (
    <Layout>
      <h1 className="text-center">Welcome to the project time tracker! 🎉</h1>
      <hr />
      <WithAdmin>
        <Jumbotron>
          <h1>Hello, {user.username}</h1>
          <p>
            This is a a cry for help. We need YOU, to create and implement
            projects. As many as you can come up with! 🚀
          </p>
          <p>
            <Button onClick={() => push("/admin")} variant="primary">
              Go to the admin dashboard
            </Button>
          </p>
        </Jumbotron>
      </WithAdmin>
      <WithUser>
        <Jumbotron>
          <h1>Hello, {user.username}</h1>
          <p>
            User stories are waiting JUST FOR YOU, to be implemented. What are
            you waiting for? Go and have a look 🙉
          </p>
          <p>
            <Button onClick={() => push("/user")} variant="primary">
              Go to your profile
            </Button>
          </p>
        </Jumbotron>
      </WithUser>
    </Layout>
  );
}
