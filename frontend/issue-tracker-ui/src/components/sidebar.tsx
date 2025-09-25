"use client"
import { Button } from "@/components/ui/button"
import  { Role as UserRole } from "@/types/Role"
import { LayoutDashboard, FolderOpen, Bug, Rocket, Users, UserCheck } from "lucide-react"

interface SidebarProps {
  userRole: UserRole
  activeTab: string
  onTabChange: (tab: string) => void
}

export function Sidebar({ userRole, activeTab, onTabChange }: SidebarProps) {
  const menuItems = [
    {
      id: "dashboard",
      label: "Dashboard",
      icon: LayoutDashboard,
      roles: ["ADMIN", "PROJECT_MANAGER", "DEVELOPER", "TESTER"],
    },
    {
      id: "projects",
      label: "Projects",
      icon: FolderOpen,
      roles: ["ADMIN", "PROJECT_MANAGER", "DEVELOPER", "TESTER"],
    },
    {
      id: "issues",
      label: "Issues",
      icon: Bug,
      roles: ["ADMIN", "PROJECT_MANAGER", "DEVELOPER", "TESTER"],
    },
    {
      id: "deployments",
      label: "Deployments",
      icon: Rocket,
      roles: ["ADMIN", "PROJECT_MANAGER", "DEVELOPER", "TESTER"],
    },
    {
      id: "users",
      label: "Users",
      icon: Users,
      roles: ["ADMIN"],
    },
    {
      id: "role-requests",
      label: "Role Requests",
      icon: UserCheck,
      roles: ["ADMIN"],
    },
  ]

  const visibleItems = menuItems.filter((item) => item.roles.includes(userRole))

  if (userRole === "UNASSIGNED") {
    return (
      <aside className="w-64 border-r border-border bg-card">
        <div className="p-6">
          <div className="space-y-4">
            <Button
              variant={activeTab === "pending-role" ? "secondary" : "ghost"}
              className="w-full justify-start"
              onClick={() => onTabChange("pending-role")}
            >
              <UserCheck className="mr-2 h-4 w-4" />
              Role Assignment
            </Button>
          </div>
        </div>
      </aside>
    )
  }

  return (
    <aside className="w-64 border-r border-border bg-card">
      <div className="p-6">
        <nav className="space-y-2">
          {visibleItems.map((item) => {
            const Icon = item.icon
            return (
              <Button
                key={item.id}
                variant={activeTab === item.id ? "secondary" : "ghost"}
                className="w-full justify-start"
                onClick={() => onTabChange(item.id)}
              >
                <Icon className="mr-2 h-4 w-4" />
                {item.label}
              </Button>
            )
          })}
        </nav>
      </div>
    </aside>
  )
}
