"use client";

import { useEffect, useState } from "react";
import { PendingRolePage } from "./pending-page/page";
import { User } from "@/types/user";

export default function Home() {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchUser() {
      try {
        const res = await fetch(
          "http://localhost:8080/IssuesandDeploymentTracker/api/auth/validate", // new endpoint
          {
            method: "GET",
            credentials: "include", // send cookie automatically
          }
        );

        if (!res.ok) throw new Error("Not logged in");

        const data = await res.json();
        setUser(data);
      } catch (err) {
        console.error(err);
        setUser(null);
      } finally {
        setLoading(false);
      }
    }

    fetchUser();
  }, []);

  if (loading) return <p>Loading...</p>;
  if (!user) return <p>Please log in.</p>;

  // render PendingRolePage if role not assigned
  if (user.role=="UNASSIGNED") return <PendingRolePage user={user} />;

  return <p>Welcome, {user.username}! Your role is: {user.role}</p>;
}
