import { Role } from "./Role";

export interface User {
  id: number;
  fullname: string;
  email: string;
  role: Role;
  createdAt?: string;
  updatedAt?: string;
}
