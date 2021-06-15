import { Suspense } from "react";
import { lazy } from "react";
import FullPageSpinner from "./components/FullPageSpinner";
import { useUser } from "./context/UserProvider";

let AuthenticatedApp = lazy(() => import("./AuthenticatedApp"));
let UnauthenticatedApp = lazy(() => import("./UnauthenticatedApp"));

export default function App() {
  let { user } = useUser();

  return (
    <Suspense fallback={<FullPageSpinner />}>
      {user ? <AuthenticatedApp /> : <UnauthenticatedApp />}
    </Suspense>
  );
}
