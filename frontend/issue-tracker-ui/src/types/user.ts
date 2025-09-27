import { Role } from "./Role";

export interface User {
  id: number;
  fullName: string;
  email: string;
  role: Role;
  createdAt?: string;
  updatedAt?: string;
}
