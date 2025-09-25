"use client";
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { LogOut, Triangle } from "lucide-react";
import { useRouter } from "next/navigation";

interface HeaderProps {
  user: any;
  onUserChange: () => void;
}

// export function Header({ user, onUserChange }: HeaderProps) {
export function Header() {
  const router = useRouter();
  async function handleLogout() {
    try {
      const res = await fetch(
        "http://localhost:8080/IssuesandDeploymentTracker/api/auth/logout",
        {
          method: "POST",
          credentials: "include", // send cookies
        }
      );

      if (res.ok) {
        // Optional: clear any client state
        localStorage.clear();
        sessionStorage.clear();

        router.push("/auth");
      } else {
        console.error("Logout failed");
      }
    } catch (err) {
      console.error("Logout error", err);
    }
  } // optional if you maintain server-side blacklist

//   const handleSwitchUser = (newUser: any) => {
//     localStorage.setItem("currentUser", JSON.stringify(newUser));
//     onUserChange();
//   };

  // Mock users for demo switching
  const mockUsers = [
    { id: "1", name: "Admin User", email: "admin@company.com", role: "ADMIN" },
    {
      id: "2",
      name: "Project Manager",
      email: "pm@company.com",
      role: "PROJECT_MANAGER",
    },
    { id: "3", name: "Developer", email: "dev@company.com", role: "DEVELOPER" },
    { id: "4", name: "Tester", email: "tester@company.com", role: "TESTER" },
    { id: "5", name: "New User", email: "newuser@company.com", role: "NONE" },
  ];

  return (
    <header className="border-b border-border bg-card">
      <div className="flex h-16 items-center justify-between px-6">
        <div className="flex items-center gap-3">
          <Triangle className="h-6 w-6 text-primary" />
          <h1 className="text-xl font-semibold text-foreground">
            Issue Tracker
          </h1>
        </div>

        <div className="flex items-center gap-4">
          {user && (
            <div className="flex items-center gap-2 text-sm text-muted-foreground">
              <span className="capitalize">{user.role.toLowerCase()}</span>
              <span>•</span>
              <span>{user.name}</span>
            </div>
          )}

          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button variant="ghost" className="relative h-8 w-8 rounded-full">
                <Avatar className="h-8 w-8">
                  <AvatarFallback className="bg-primary text-primary-foreground">
                    {user?.name?.charAt(0) || "?"}
                  </AvatarFallback>
                </Avatar>
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent className="w-56" align="end">
              {user && (
                <>
                  <div className="flex items-center justify-start gap-2 p-2">
                    <div className="flex flex-col space-y-1 leading-none">
                      <p className="font-medium">{user.name}</p>
                      <p className="w-[200px] truncate text-sm text-muted-foreground">
                        {user.email}
                      </p>
                    </div>
                  </div>
                  <DropdownMenuSeparator />
                </>
              )}

              {/* Demo user switching */}
              <div className="p-2">
                <p className="text-xs text-muted-foreground mb-2">
                  Switch User (Demo)
                </p>
                {mockUsers.map((mockUser) => (
                  <DropdownMenuItem
                    key={mockUser.id}
                    onClick={() => handleSwitchUser(mockUser)}
                    className="text-xs"
                  >
                    {mockUser.name} ({mockUser.role})
                  </DropdownMenuItem>
                ))}
              </div>

              {user && (
                <>
                  <DropdownMenuSeparator />
                  <DropdownMenuItem onClick={handleLogout}>
                    <LogOut className="mr-2 h-4 w-4" />
                    <span>Log out</span>
                  </DropdownMenuItem>
                </>
              )}
            </DropdownMenuContent>
          </DropdownMenu>
        </div>
      </div>
    </header>
  );
}
