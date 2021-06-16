import { useUser } from "../context/UserProvider";

export default function WithUser({ children }) {
  let { user } = useUser();
  if (user.roles.user) return children;
  return null;
}
