import { User } from "@/types/user";

const BASE_URL = process.env.NEXT_PUBLIC_BACKEND_URL;

export async function fetchUsers() {
  const res = await fetch(`${BASE_URL}/api/users`, {
    method: "GET",
    credentials: "include",
  });

  if (!res.ok) {
    throw new Error("Failed to fetch Users");
  }
  const data: User[] = await res.json();
  return data;
}
