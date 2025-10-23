import { Project } from "@/types/project";

const BASE_URL = process.env.NEXT_PUBLIC_BACKEND_URL;

export async function fetchProjects() {
  const res = await fetch(`${BASE_URL}/api/projects`, {
    method: "GET",
    credentials: "include",
  });

  if (!res.ok) {
    throw new Error("Failed to fetch Projects");
  }
  const data: Project[] = await res.json();
  return data;
}