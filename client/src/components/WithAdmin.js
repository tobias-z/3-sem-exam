import { useUser } from "../context/UserProvider";

export default function WithAdmin({ children }) {
  let { user } = useUser();
  if (user.roles.admin) return children;
  return null;
}
