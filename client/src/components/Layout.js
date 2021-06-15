import { useUser } from "../context/UserProvider";
import AuthenticatedHeader from "./AuthenticatedHeader";
import UnauthenticatedHeader from "./UnauthenticatedHeader";

export default function Layout({ children }) {
  let { user } = useUser();
  return (
    <>
      {user ? <AuthenticatedHeader /> : <UnauthenticatedHeader />}
      <main className="container mt-3">{children}</main>
    </>
  );
}
