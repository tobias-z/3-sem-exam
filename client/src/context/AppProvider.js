import { AuthProvider } from "./AuthProvider";
import { UserProvider } from "./UserProvider";

export default function AppProvider({ children }) {
  return (
    <UserProvider>
      <AuthProvider>{children}</AuthProvider>
    </UserProvider>
  );
}
