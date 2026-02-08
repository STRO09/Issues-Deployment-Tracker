"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { PendingRolePage } from "./pending-page/page";
import { User } from "@/types/user";
import { AdminDashboard } from "./admin/page";
import { validateUser } from "@/lib/api/auth";
import { ProjectManagerDashboard } from "./projectmanager/page";

export default function Home() {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);
  const router = useRouter();

  async function fetchUser() {
    try {
      const data = await validateUser();
      console.log(data);
      setUser(data);
    } catch (err: unknown) {
      if(err instanceof Error)  console.log(err.message);
      setUser(null);
      router.push("/auth");
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    fetchUser();
  }, []);

  if (loading) return <p>Loading...</p>;
  if (!user) return <p>Please log in.</p>;

  // render PendingRolePage if role not assigned
  if (user.role == "UNASSIGNED") return <PendingRolePage user={user} />;
  if(user.role == "PROJECT_MANAGER") return <ProjectManagerDashboard user={user} />;
  if (user.role == "ADMIN") return <AdminDashboard user={user} />;
}
