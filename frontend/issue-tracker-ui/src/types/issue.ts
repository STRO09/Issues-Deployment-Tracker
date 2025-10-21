import { Priority } from "./priority";
import { Status } from "./status";
import { User } from "./user";

export interface Issue {
  id: number;
  title: string;
  description: string;
  projectId: number;
  status: Status;
  priority: Priority;
  createdBy: User;
  assignedTo: User;
  createdAt?: string;
  updatedAt?: string;
  comments? : string[]
}
