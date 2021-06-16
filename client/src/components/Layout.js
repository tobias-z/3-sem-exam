import { useUser } from "../context/UserProvider";
import AuthenticatedHeader from "./AuthenticatedHeader";
import UnauthenticatedHeader from "./UnauthenticatedHeader";

export default function Layout({ children, noHeader }) {
  let { user } = useUser();
  return (
    <>
      {!noHeader && (
        <>{user ? <AuthenticatedHeader /> : <UnauthenticatedHeader />}</>
      )}
      <main className="container mt-3">{children}</main>
    </>
  );
}
