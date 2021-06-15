import FullPageSpinner from "../components/FullPageSpinner";
import { USER } from "../settings";
import { createContext, useContext, useState } from "react";
import { useQuery } from "../hooks/promise";

export function createUserObject(user) {
  let roles = {
    user: false,
    admin: false,
  };

  JSON.parse(user.roles).forEach(role => {
    if (role.name === "user") roles.user = true;
    if (role.name === "admin") roles.admin = true;
  });

  return {
    ...user,
    roles,
  };
}

let UserContext = createContext(null);

export let useUser = () => useContext(UserContext);

export function UserProvider({ children }) {
  let [isStillValidationgToken, setIsStillValidationgToken] = useState(true);
  let { value: user, setValue: setUser } = useQuery(USER.VALIDATE_TOKEN, {
    onSuccess(data) {
      setUser(createUserObject(data));
    },
    afterRun() {
      setIsStillValidationgToken(false);
    },
  });

  if (isStillValidationgToken) return <FullPageSpinner />;

  let values = {
    user,
    setUser,
  };

  return <UserContext.Provider value={values}>{children}</UserContext.Provider>;
}
