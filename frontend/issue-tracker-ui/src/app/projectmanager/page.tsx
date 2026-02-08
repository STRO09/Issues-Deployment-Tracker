"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Progress } from "@/components/ui/progress";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import type { User } from "@/types/user";
import { Project } from "@/types/project";
import { Issue } from "@/types/issue";
import { Deployment } from "@/types/deployment";
import { Status } from "@/types/status";
import { Priority } from "@/types/priority";
import {
  FolderOpen,
  Bug,
  Rocket,
  ExternalLink,
  CheckCircle,
  AlertCircle,
  Clock,
} from "lucide-react";
import DashboardLayout from "@/components/DashboardLayout";
import { CreateProjectModal } from "@/components/createProjectModal";
import { fetchProjects } from "@/lib/api/projects";

interface ProjectManagerDashboardProps {
  user: User;
}

export default  function ProjectManagerDashboard({
  user,
}: ProjectManagerDashboardProps) {
  const router = useRouter();
  const [projects, setProjects] = useState<Project[]>([]);
  const [issues, setIssues] = useState<Issue[]>([]);
  const [deployments, setDeployments] = useState<Deployment[]>([]);
  
  useEffect(() => {
    if (!user) router.push("/auth");
  }, [user, router]);
  
  useEffect(() => {
    if (user) {
      setIssues([
        {
          id: 1,
          title: "Fix payment gateway integration",
          description: "Payment processing fails for certain card types",
          status: Status.IN_PROGRESS,
          priority: Priority.HIGH,
          projectId: 1,
          assignedTo: user,
          createdBy: user,
          createdAt: new Date(Date.now() - 2 * 24 * 60 * 60 * 1000).toISOString(),
          updatedAt: new Date().toISOString(),
          comments: ["Hallelujah"],
        },
      ]);
      setDeployments([
        {
          id: 1,
          projectId: 1,
          version: "v2.1.0",
          description: "Bug fixes and performance improvements",
          url: "https://ecommerce-prod.vercel.app",
          deployedBy: user.id,
          deployedAt: new Date(Date.now() - 6 * 60 * 60 * 1000).toISOString(),
          status: "SUCCESS",
        },
      ]);
    }
  }, [user]);
  
  useEffect(() => {
    loadProjects();
  }, []);
  
  if (!user) return null;

  async function loadProjects() {
    try {
      const data = await fetchProjects();
      setProjects(data);
    } catch (err: unknown) {
      if(err instanceof Error)
      console.log(err.message);
    }
  }

  const getProjectName = (projectId: number) => {
    const project = projects?.find((p) => p.id === projectId);
    return project?.projectname || "Unknown Project";
  };

  const getIssuesByProject = (projectId: number) => {
    return issues.filter((issue) => issue.projectId === projectId);
  };

  const getProjectProgress = (projectId: number) => {
    const projectIssues = getIssuesByProject(projectId);
    if (projectIssues.length === 0) return 100;

    const closedIssues = projectIssues.filter(
      (issue) => issue.status === "CLOSED"
    ).length;
    return Math.round((closedIssues / projectIssues.length) * 100);
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case "OPEN":
        return "bg-blue-500/10 text-blue-500 border-blue-500/20";
      case "IN_PROGRESS":
        return "bg-yellow-500/10 text-yellow-500 border-yellow-500/20";
      case "TESTING":
        return "bg-purple-500/10 text-purple-500 border-purple-500/20";
      case "CLOSED":
        return "bg-green-500/10 text-green-500 border-green-500/20";
      case "BLOCKED":
        return "bg-red-500/10 text-red-500 border-red-500/20";
      default:
        return "bg-gray-500/10 text-gray-500 border-gray-500/20";
    }
  };

  const getPriorityColor = (priority: string) => {
    switch (priority) {
      case "CRITICAL":
        return "bg-red-600/10 text-red-600 border-red-600/20";
      case "HIGH":
        return "bg-orange-500/10 text-orange-500 border-orange-500/20";
      case "MEDIUM":
        return "bg-yellow-500/10 text-yellow-500 border-yellow-500/20";
      case "LOW":
        return "bg-green-500/10 text-green-500 border-green-500/20";
      default:
        return "bg-gray-500/10 text-gray-500 border-gray-500/20";
    }
  };

  const getDeploymentStatusIcon = (status: string) => {
    switch (status) {
      case "SUCCESS":
        return <CheckCircle className="h-4 w-4 text-green-500" />;
      case "FAILED":
        return <AlertCircle className="h-4 w-4 text-red-500" />;
      case "PENDING":
        return <Clock className="h-4 w-4 text-yellow-500" />;
      default:
        return null;
    }
  };

  // Calculate stats
  const stats = {
    totalProjects: projects?.length || "0",
    activeIssues: issues.filter((i) => i.status !== "CLOSED").length,
    completedIssues: issues.filter((i) => i.status === "CLOSED").length,
    recentDeployments: deployments
      .filter((d) => d.deployedAt)
      .filter(
        (d) =>
          new Date(d.deployedAt!) >
          new Date(Date.now() - 7 * 24 * 60 * 60 * 1000)
      ).length,
  };

  return (
    <DashboardLayout user={user}>
      <div className="p-6 space-y-6">
        <div>
          <h1 className="text-3xl font-bold text-balance">
            Project Manager Dashboard
          </h1>
          <p className="text-muted-foreground mt-2">
            Overview of projects, issues, and deployments
          </p>
        </div>

        {/* Stats Cards */}
        <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">
                Active Projects
              </CardTitle>
              <FolderOpen className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{stats.totalProjects}</div>
              <p className="text-xs text-muted-foreground">
                Projects under management
              </p>
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
              <p className="text-xs text-muted-foreground">
                Open and in progress
              </p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">
                Completed Issues
              </CardTitle>
              <CheckCircle className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{stats.completedIssues}</div>
              <p className="text-xs text-muted-foreground">
                Resolved this month
              </p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Deployments</CardTitle>
              <Rocket className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">
                {stats.recentDeployments}
              </div>
              <p className="text-xs text-muted-foreground">This week</p>
            </CardContent>
          </Card>
        </div>

        <div className="grid gap-6 lg:grid-cols-2">
          {/* Project Overview */}
          <Card>
            <CardHeader className="flex flex-row items-center justify-between">
              <div>
                <CardTitle>Project Overview</CardTitle>
                <CardDescription>
                  Progress and status of your projects
                </CardDescription>
              </div>
              <CreateProjectModal
                user={user}
                addProject={(project) =>
                  setProjects((prev) => [...prev, project])
                }
              />
            </CardHeader>
            <CardContent className="space-y-4">
              {projects && projects.length > 0 ? (
                projects?.map((project) => {
                  const progress = getProjectProgress(project.id);
                  const projectIssues = getIssuesByProject(project.id);

                  return (
                    <div
                      key={project.id}
                      className="border rounded-lg p-4 space-y-3"
                    >
                      <div className="flex items-center justify-between">
                        <div>
                          <h4 className="font-medium">{project.projectname}</h4>
                          <p className="text-sm text-muted-foreground">
                            {project.description}
                          </p>
                        </div>
                        {/* <Badge variant="outline" className="bg-blue-500/10 text-blue-500 border-blue-500/20">
                      {project.members.length} members
                    </Badge> */}
                      </div>

                      <div className="space-y-2">
                        <div className="flex justify-between text-sm">
                          <span>Progress</span>
                          <span>{progress}%</span>
                        </div>
                        <Progress value={progress} className="h-2" />
                      </div>

                      <div className="flex items-center justify-between text-sm">
                        <div className="flex items-center gap-4">
                          <span className="text-muted-foreground">
                            {projectIssues.length} issues
                          </span>
                          <span className="text-muted-foreground">
                            Updated{" "}
                            {new Date(project.updatedAt!).toLocaleDateString()}
                          </span>
                        </div>
                        <Button variant="ghost" size="sm">
                          View Details
                        </Button>
                      </div>
                    </div>
                  );
                })
              ) : (
                <div>No Projects here</div>
              )}
            </CardContent>
          </Card>

          {/* Recent Issues */}
          <Card>
            <CardHeader>
              <CardTitle>Recent Issues</CardTitle>
              <CardDescription>
                Latest issues across your projects
              </CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                {issues.slice(0, 5).map((issue) => (
                  <div
                    key={issue.id}
                    className="flex items-center justify-between p-3 border rounded-lg"
                  >
                    <div className="space-y-1">
                      <div className="flex items-center gap-2">
                        <h4 className="font-medium text-sm">{issue.title}</h4>
                        <Badge
                          variant="outline"
                          className={getPriorityColor(issue.priority)}
                        >
                          {issue.priority}
                        </Badge>
                      </div>
                      <div className="flex items-center gap-2 text-xs text-muted-foreground">
                        <span>{getProjectName(issue.projectId)}</span>
                        <span>•</span>
                        <span>
                          {new Date(issue.createdAt!).toLocaleDateString()}
                        </span>
                      </div>
                    </div>
                    <Badge
                      variant="outline"
                      className={getStatusColor(issue.status)}
                    >
                      {issue.status.replace("_", " ")}
                    </Badge>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>
        </div>

        {/* Recent Deployments */}
        <Card>
          <CardHeader className="flex flex-row items-center justify-between">
            <div>
              <CardTitle>Recent Deployments</CardTitle>
              <CardDescription>
                Latest deployments across your projects
              </CardDescription>
            </div>
            <Button size="sm">
              <Rocket className="mr-2 h-4 w-4" />
              New Deployment
            </Button>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Project</TableHead>
                  <TableHead>Version</TableHead>
                  <TableHead>Status</TableHead>
                  <TableHead>Deployed</TableHead>
                  <TableHead>URL</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {deployments.map((deployment) => (
                  <TableRow key={deployment.id}>
                    <TableCell>
                      <div>
                        <div className="font-medium">
                          {getProjectName(deployment.projectId)}
                        </div>
                        <div className="text-sm text-muted-foreground">
                          {deployment.description}
                        </div>
                      </div>
                    </TableCell>
                    <TableCell>
                      <Badge variant="outline">{deployment.version}</Badge>
                    </TableCell>
                    <TableCell>
                      <div className="flex items-center gap-2">
                        {getDeploymentStatusIcon(
                          deployment.status ?? "UNKNOWN"
                        )}
                        <span className="text-sm">{deployment.status}</span>
                      </div>
                    </TableCell>
                    <TableCell className="text-sm text-muted-foreground">
                      {deployment.deployedAt &&
                        new Date(deployment.deployedAt).toLocaleString()}
                    </TableCell>
                    <TableCell>
                      <Button variant="ghost" size="sm" asChild>
                        <a
                          href={deployment.url}
                          target="_blank"
                          rel="noopener noreferrer"
                        >
                          <ExternalLink className="h-3 w-3" />
                        </a>
                      </Button>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </CardContent>
        </Card>

        {/* Team Performance */}
        <Card>
          <CardHeader>
            <CardTitle>Team Performance</CardTitle>
            <CardDescription>
              Overview of team productivity and metrics
            </CardDescription>
          </CardHeader>
          <CardContent>
            <div className="grid gap-4 md:grid-cols-3">
              <div className="text-center space-y-2">
                <div className="text-2xl font-bold text-green-500">85%</div>
                <div className="text-sm text-muted-foreground">
                  Issues Resolved On Time
                </div>
              </div>
              <div className="text-center space-y-2">
                <div className="text-2xl font-bold text-blue-500">12</div>
                <div className="text-sm text-muted-foreground">
                  Average Issues per Sprint
                </div>
              </div>
              <div className="text-center space-y-2">
                <div className="text-2xl font-bold text-purple-500">
                  2.3 days
                </div>
                <div className="text-sm text-muted-foreground">
                  Average Resolution Time
                </div>
              </div>
            </div>
          </CardContent>
        </Card>
      </div>
    </DashboardLayout>
  );
}
