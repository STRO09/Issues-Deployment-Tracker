"use client";

import { useEffect, useState } from "react";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { User } from "@/types/user";
import { Role as UserRole } from "@/types/Role";
import {
  Users,
  UserCheck,
  FolderOpen,
  Bug,
  Rocket,
  CheckCircle,
} from "lucide-react";
import DashboardLayout from "@/components/DashboardLayout";
import { fetchUsers } from "@/lib/api/users";

interface AdminDashboardProps {
  user: User;
}

export function AdminDashboard({ user }: AdminDashboardProps) {
  // Mock data for demo
  const [users, setUsers] = useState<User[]>();

  const [successMessage, setSuccessMessage] = useState("");

  const handleRoleChange = (userId: number, newRole: UserRole) => {
    setUsers(
      users?.map((u) =>
        u.id === userId
          ? { ...u, role: newRole, updatedAt: new Date().toISOString() }
          : u
      )
    );
    setSuccessMessage(`User role updated successfully`);
    setTimeout(() => setSuccessMessage(""), 3000);
  };

  const getRoleColor = (role: UserRole) => {
    switch (role) {
      case "ADMIN":
        return "bg-red-500/10 text-red-500 border-red-500/20";
      case "PROJECT_MANAGER":
        return "bg-blue-500/10 text-blue-500 border-blue-500/20";
      case "DEVELOPER":
        return "bg-green-500/10 text-green-500 border-green-500/20";
      case "TESTER":
        return "bg-purple-500/10 text-purple-500 border-purple-500/20";
      case "UNASSIGNED":
        return "bg-gray-500/10 text-gray-500 border-gray-500/20";
      default:
        return "bg-gray-500/10 text-gray-500 border-gray-500/20";
    }
  };

  // Calculate stats
  const stats = {
    totalUsers: users?.length,
    totalProjects: 8, // Mock data
    activeIssues: 23, // Mock data
    deployments: 15, // Mock data
  };

  useEffect(() => {
    async function loadUsers() {
      try {
        const data = await fetchUsers();
        setUsers(data);

      } catch (err: any) {
        console.log(err.message, "Failed to fetch Users");
      }
    }

    loadUsers();
  }, []);

  return (
    <DashboardLayout user={user}>
      <div className="p-6 space-y-6">
        <div>
          <h1 className="text-3xl font-bold text-balance">Admin Dashboard</h1>
          <p className="text-muted-foreground mt-2">
            Manage users, roles, and system overview
          </p>
        </div>

        {/* Success Alert */}
        {successMessage && (
          <Alert className="border-green-500/20 bg-green-500/10">
            <CheckCircle className="h-4 w-4 text-green-500" />
            <AlertDescription className="text-green-700">
              {successMessage}
            </AlertDescription>
          </Alert>
        )}

        {/* Stats Cards */}
        <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-5">
          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Total Users</CardTitle>
              <Users className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{stats.totalUsers}</div>
              <p className="text-xs text-muted-foreground">
                Active system users
              </p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">
                Pending Requests
              </CardTitle>
              <UserCheck className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              {/* <div className="text-2xl font-bold">{stats.pendingRequests}</div> */}
              <p className="text-xs text-muted-foreground">Role assignments</p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Projects</CardTitle>
              <FolderOpen className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{stats.totalProjects}</div>
              <p className="text-xs text-muted-foreground">Active projects</p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">
                Active Issues
              </CardTitle>
              <Bug className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{stats.activeIssues}</div>
              <p className="text-xs text-muted-foreground">Open issues</p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Deployments</CardTitle>
              <Rocket className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{stats.deployments}</div>
              <p className="text-xs text-muted-foreground">This month</p>
            </CardContent>
          </Card>
        </div>

        <div className="grid gap-6 lg:grid-cols-2">
          {/* User Management */}
          <Card>
            <CardHeader>
              <CardTitle>User Management</CardTitle>
              <CardDescription>
                Manage user roles and permissions
              </CardDescription>
            </CardHeader>
            <CardContent>
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>User</TableHead>
                    <TableHead>Role</TableHead>
                    <TableHead>Actions</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {users?.map((user) => (
                    <TableRow key={user.id}>
                      <TableCell>
                        <div className="flex items-center gap-3">
                          <Avatar className="h-8 w-8">
                            <AvatarFallback className="bg-primary text-primary-foreground text-xs">
                              {user.fullName.charAt(0)}
                            </AvatarFallback>
                          </Avatar>
                          <div>
                            <div className="font-medium">{user.fullName}</div>
                            <div className="text-sm text-muted-foreground">
                              {user.email}
                            </div>
                          </div>
                        </div>
                      </TableCell>
                      <TableCell>
                        <Badge
                          variant="outline"
                          className={getRoleColor(user.role)}
                        >
                          {user.role.replace("_", " ")}
                        </Badge>
                      </TableCell>
                      <TableCell>
                        <Select
                          value={user.role}
                          onValueChange={(value) =>
                            handleRoleChange(user.id, value as UserRole)
                          }
                        >
                          <SelectTrigger className="w-[140px]">
                            <SelectValue />
                          </SelectTrigger>
                          <SelectContent>
                            <SelectItem value="NONE">None</SelectItem>
                            <SelectItem value="DEVELOPER">Developer</SelectItem>
                            <SelectItem value="TESTER">Tester</SelectItem>
                            <SelectItem value="PROJECT_MANAGER">
                              Project Manager
                            </SelectItem>
                            <SelectItem value="ADMIN">Admin</SelectItem>
                          </SelectContent>
                        </Select>
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </CardContent>
          </Card>

          {/* Role Requests */}
          <Card>
            <CardHeader>
              <CardTitle>Pending Role Requests</CardTitle>
              <CardDescription>
                Review and approve role assignment requests
              </CardDescription>
            </CardHeader>
            {/* <CardContent>
            {roleRequests.filter((r) => r.status === "PENDING").length === 0 ? (
              <div className="text-center py-8">
                <div className="p-3 rounded-full bg-muted w-fit mx-auto mb-3">
                  <UserCheck className="h-6 w-6 text-muted-foreground" />
                </div>
                <p className="text-muted-foreground">No pending requests</p>
              </div>
            ) : (
              <div className="space-y-4">
                {roleRequests
                  .filter((r) => r.status === "PENDING")
                  .map((request) => (
                    <div
                      key={request.id}
                      className="border rounded-lg p-4 space-y-3"
                    >
                      <div className="flex items-center justify-between">
                        <div>
                          <div className="font-medium">
                            {getUserName(request.userId)}
                          </div>
                          <div className="text-sm text-muted-foreground">
                            Requesting:{" "}
                            {request.requestedRole.replace("_", " ")}
                          </div>
                        </div>
                        <Badge
                          variant="outline"
                          className="bg-yellow-500/10 text-yellow-500 border-yellow-500/20"
                        >
                          <Clock className="mr-1 h-3 w-3" />
                          Pending
                        </Badge>
                      </div>

                      <p className="text-sm text-muted-foreground">
                        {request.reason}
                      </p>

                      <div className="flex gap-2">
                        <Button
                          size="sm"
                          onClick={() =>
                            handleRoleRequest(request.id, "APPROVED")
                          }
                          className="bg-green-600 hover:bg-green-700"
                        >
                          <CheckCircle className="mr-1 h-3 w-3" />
                          Approve
                        </Button>
                        <Button
                          size="sm"
                          variant="outline"
                          onClick={() =>
                            handleRoleRequest(request.id, "REJECTED")
                          }
                          className="border-red-500/20 text-red-500 hover:bg-red-500/10"
                        >
                          <XCircle className="mr-1 h-3 w-3" />
                          Reject
                        </Button>
                      </div>
                    </div>
                  ))}
              </div>
            )}
          </CardContent> */}
          </Card>
        </div>

        {/* Recent Activity */}
        <Card>
          <CardHeader>
            <CardTitle>Recent Activity</CardTitle>
            <CardDescription>
              Latest system activities and changes
            </CardDescription>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              <div className="flex items-center gap-3 text-sm">
                <div className="p-1 rounded-full bg-green-500/10">
                  <CheckCircle className="h-3 w-3 text-green-500" />
                </div>
                <span>New user "John Doe" registered</span>
                <span className="text-muted-foreground ml-auto">
                  2 hours ago
                </span>
              </div>
              <div className="flex items-center gap-3 text-sm">
                <div className="p-1 rounded-full bg-blue-500/10">
                  <FolderOpen className="h-3 w-3 text-blue-500" />
                </div>
                <span>Project "Mobile App" created</span>
                <span className="text-muted-foreground ml-auto">
                  4 hours ago
                </span>
              </div>
              <div className="flex items-center gap-3 text-sm">
                <div className="p-1 rounded-full bg-purple-500/10">
                  <Bug className="h-3 w-3 text-purple-500" />
                </div>
                <span>Issue #123 marked as resolved</span>
                <span className="text-muted-foreground ml-auto">
                  6 hours ago
                </span>
              </div>
              <div className="flex items-center gap-3 text-sm">
                <div className="p-1 rounded-full bg-orange-500/10">
                  <Rocket className="h-3 w-3 text-orange-500" />
                </div>
                <span>Deployment v2.1.0 completed</span>
                <span className="text-muted-foreground ml-auto">1 day ago</span>
              </div>
            </div>
          </CardContent>
        </Card>
      </div>
    </DashboardLayout>
  );
}
