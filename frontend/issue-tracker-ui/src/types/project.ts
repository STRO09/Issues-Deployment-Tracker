import { User } from "./user";

export interface Project {
  id: number;
  projectname: string;
  description: string;
  createdBy : User;
  createdAt?: string;
  updatedAt?: string;
}
