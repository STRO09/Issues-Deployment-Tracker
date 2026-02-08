"use client";

export const dynamic = "force-dynamic";

import { useState } from "react";
import { Button } from "@/components/ui/button";
import { useRouter } from "next/navigation";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { User } from "@/types/user";
import { Role } from "@/types/Role";
import { UserX, Send, Clock, CheckCircle } from "lucide-react";
import { logoutUser } from "@/lib/api/auth";

interface PendingRolePageProps {
  user: User;
}

export default function PendingRolePage({ user }: PendingRolePageProps) {
  const [requestedRole, setRequestedRole] = useState<Role | "">("");
  const [reason, setReason] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [submitSuccess, setSubmitSuccess] = useState(false);
  const router = useRouter();

  if (!user) return <p>Loading...</p>;

  const availableRoles: { value: Role; label: string; description: string }[] =
    [
      {
        value: Role.DEVELOPER,
        label: "Developer",
        description: "Can view and manage issues, work on assigned tasks",
      },
      {
        value: Role.TESTER,
        label: "Tester",
        description: "Can test issues, update status, and report bugs",
      },
      {
        value: Role.PROJECT_MANAGER,
        label: "Project Manager",
        description:
          "Can manage projects, assign issues, and deploy applications",
      },
    ];

  const handleSubmitRequest = async () => {
    if (!requestedRole || !reason.trim()) return;

    setIsSubmitting(true);

    // Simulate API call
    await new Promise((resolve) => setTimeout(resolve, 1000));

    // Mock successful submission
    setSubmitSuccess(true);
    setRequestedRole("");
    setReason("");
    setIsSubmitting(false);

    // Reset success message after 3 seconds
    setTimeout(() => setSubmitSuccess(false), 3000);
  };

  async function handleLogout() {
    try {
      await logoutUser();
      router.push("/auth");
    } catch (err:unknown) {
      if(err instanceof Error) {
              console.error("Logout error", err);
      alert(err.message || "Logout failed");
      }
    }
  }

  return (
    <div className="relative">
      <div className="flex justify-end p-4">
        <Button
          onClick={handleLogout}
          variant="destructive" // makes it red, optional
          className="px-4 py-2"
        >
          Logout
        </Button>
      </div>
      <div className="p-6 max-w-4xl mx-auto space-y-6">
        {/* Welcome Message */}
        <div className="text-center space-y-4">
          <div className="flex justify-center">
            <div className="p-3 rounded-full bg-muted">
              <UserX className="h-8 w-8 text-muted-foreground" />
            </div>
          </div>
          <div>
            <h1 className="text-3xl font-bold text-balance">
              Welcome, {user.fullName} !
            </h1>
            <p className="text-lg text-muted-foreground mt-2 text-pretty">
              Your role is not assigned yet. Please request a role to access the
              system.
            </p>
          </div>
        </div>

        {/* Success Alert */}
        {submitSuccess && (
          <Alert className="border-green-500/20 bg-green-500/10">
            <CheckCircle className="h-4 w-4 text-green-500" />
            <AlertDescription className="text-green-700">
              Your role request has been submitted successfully! An admin will
              review it soon.
            </AlertDescription>
          </Alert>
        )}

        <div className="grid gap-6 md:grid-cols-1">
          {/* Role Request Form */}
          <Card>
            <CardHeader>
              <CardTitle className="flex items-center gap-2">
                <Send className="h-5 w-5" />
                Request Role Assignment
              </CardTitle>
              <CardDescription>
                Choose the role that best matches your responsibilities and
                provide a reason for your request.
              </CardDescription>
            </CardHeader>
            <CardContent className="space-y-4">
              <div className="space-y-2">
                <label className="text-sm font-medium">Requested Role</label>
                <Select
                  value={requestedRole}
                  onValueChange={(value) => setRequestedRole(value as Role)}
                >
                  <SelectTrigger>
                    <SelectValue placeholder="Select a role" />
                  </SelectTrigger>
                  <SelectContent>
                    {availableRoles.map((role) => (
                      <SelectItem key={role.value} value={role.value}>
                        <div className="flex flex-col">
                          <span className="font-medium">{role.label}</span>
                          <span className="text-xs text-muted-foreground">
                            {role.description}
                          </span>
                        </div>
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>

              {/* <div className="space-y-2">
              <label className="text-sm font-medium">Reason for Request</label>
              <Textarea
                placeholder="Please explain why you need this role and your relevant experience..."
                value={reason}
                onChange={(e) => setReason(e.target.value)}
                rows={4}
                className="resize-none"
              />
              <p className="text-xs text-muted-foreground">{reason.length}/500 characters</p>
            </div> */}

              <Button
                onClick={handleSubmitRequest}
                disabled={!requestedRole || isSubmitting}
                className="w-full"
              >
                {isSubmitting ? (
                  <>
                    <div className="mr-2 h-4 w-4 animate-spin rounded-full border-2 border-current border-t-transparent" />
                    Submitting...
                  </>
                ) : (
                  <>
                    <Send className="mr-2 h-4 w-4" />
                    Submit Request
                  </>
                )}
              </Button>
            </CardContent>
          </Card>
        </div>

        {/* Information Card */}
        <Card>
          <CardHeader>
            <CardTitle>What happens next?</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="grid gap-4 md:grid-cols-3">
              <div className="flex items-start gap-3">
                <div className="p-2 rounded-full bg-primary/10">
                  <Send className="h-4 w-4 text-primary" />
                </div>
                <div>
                  <h4 className="font-medium">1. Submit Request</h4>
                  <p className="text-sm text-muted-foreground">
                    Choose your role and provide a reason
                  </p>
                </div>
              </div>

              <div className="flex items-start gap-3">
                <div className="p-2 rounded-full bg-primary/10">
                  <Clock className="h-4 w-4 text-primary" />
                </div>
                <div>
                  <h4 className="font-medium">2. Admin Review</h4>
                  <p className="text-sm text-muted-foreground">
                    An admin will review your request
                  </p>
                </div>
              </div>

              <div className="flex items-start gap-3">
                <div className="p-2 rounded-full bg-primary/10">
                  <CheckCircle className="h-4 w-4 text-primary" />
                </div>
                <div>
                  <h4 className="font-medium">3. Access Granted</h4>
                  <p className="text-sm text-muted-foreground">
                    Once approved, you can access all features
                  </p>
                </div>
              </div>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
