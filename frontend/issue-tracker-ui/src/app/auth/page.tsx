import { AuthForm } from "@/components/auth/auth-form";
export default function LoginPage() {
  return (
    <div className="bg-muted flex  flex-col items-center justify-center h-screen ">
      <div className="w-full max-w-sm md:max-w-3xl">
        <AuthForm />
      </div>
    </div>
  );
}
