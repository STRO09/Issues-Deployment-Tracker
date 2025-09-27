// components/layouts/DashboardLayout.tsx
"use client";

import { ReactNode } from "react";
import { useState } from "react";
import { Sidebar } from "./sidebar";
import { Header } from "./header";
import { User } from "@/types/user";


interface DashboardLayoutProps {
  children: ReactNode;
  user: User;
} 

export default function DashboardLayout( { children, user }: DashboardLayoutProps ) {

  const [activeTab, setActiveTab] = useState("dashboard");

  return (
    <div className="flex h-screen">
      {/* Sidebar */}
      {/* <Sidebar  userRole={user.role} activeTab={activeTab} onTabChange={setActiveTab}  /> */}
      <Sidebar userRole={user.role} activeTab={activeTab} onTabChange={setActiveTab}/>

      <div className="flex flex-1 flex-col">
        {/* Header */}
        <Header user={user} />


        {/* Main page content */}
        <main className="flex-1 overflow-y-auto p-6">{children}</main>
      </div>
    </div>
  );
}
