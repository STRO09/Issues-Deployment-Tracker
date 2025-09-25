// components/layouts/DashboardLayout.tsx
"use client";

import { ReactNode } from "react";
import { Sidebar } from "./sidebar";
import { Header } from "./header";
import { User } from "@/types/user";
import { Role as UserRole } from "@/types/Role";

interface DashboardLayoutProps {
  children: ReactNode;
  user: User;
} 

export default function DashboardLayout( { children, user }: DashboardLayoutProps ) {
  return (
    <div className="flex h-screen">
      {/* Sidebar */}
      {/* <Sidebar  userRole={user.role} activeTab={activeTab} onTabChange={setActiveTab}  /> */}
      <Sidebar userRole={UserRole}/>

      <div className="flex flex-1 flex-col">
        {/* Header */}
        <Header user={user} onUserChange={handleUserChange} />


        {/* Main page content */}
        <main className="flex-1 overflow-y-auto p-6">{children}</main>
      </div>
    </div>
  );
}
