export interface Deployment {
  id: number;
  version: string;
  description: string;
  url?:string;
  projectId: number;
  deployedAt?: string;
  deployedBy? : number;
  status?: string;
}
