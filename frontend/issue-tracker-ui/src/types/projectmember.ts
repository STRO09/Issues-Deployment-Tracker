import { ProjectRole } from "./projectrole";

export interface ProjectMember {
    id: number;
    projectid: number;
    userid:number;
    projectrole: ProjectRole;
    joinedAt: string
}