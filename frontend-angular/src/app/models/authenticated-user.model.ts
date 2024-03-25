export interface AuthenticatedUserModel {
  userId: number;
  firstName: string;
  lastName: string
  email: string;
  isLoggedIn: boolean;
  role: string;
}
