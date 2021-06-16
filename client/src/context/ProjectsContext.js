import { createContext, useContext } from "react";
import { useQuery } from "../hooks/promise";
import { PROJECT } from "../settings";

let Context = createContext(null);

export function useProjects() {
  let context = useContext(Context);
  if (!context) throw Error("useProjects was used outside of it's provider");
  return context;
}

export function ProjectProvider({ children }) {
  let { value, error, run } = useQuery(PROJECT.ALL_PROJECTS);

  let values = {
    value,
    error,
    run,
  };

  return <Context.Provider value={values}>{children}</Context.Provider>;
}
