import { Button } from "../ui/button";
import { Label } from "../ui/label";
import { Input } from "../ui/input";

type OrgLoginFormProps = {
  onBackToUser: () => void;
};

export function OrgLoginForm({ onBackToUser }: OrgLoginFormProps) {
  const inputCls =
    "bg-white/5 border-white/10 text-white placeholder:text-white/20 focus:border-emerald-500 focus:ring-emerald-500/30";
  const labelCls = "text-white/60 text-xs uppercase tracking-widest";

  return (
    <div className="flex flex-col gap-6 text-white">

      {/* Header */}
      <div className="flex flex-col items-center text-center gap-2">
        {/* Small org badge */}
        <div className="mb-1 flex items-center gap-2 px-3 py-1 rounded-full bg-emerald-900 border border-emerald-700/40">
          <svg className="w-3 h-3 text-emerald-400" fill="none" stroke="currentColor" strokeWidth={2} viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" d="M3.75 21h16.5M4.5 3h15M5.25 3v18m13.5-18v18M9 6.75h1.5m-1.5 3h1.5m-1.5 3h1.5m3-6H15m-1.5 3H15m-1.5 3H15M9 21v-3.375c0-.621.504-1.125 1.125-1.125h3.75c.621 0 1.125.504 1.125 1.125V21" />
          </svg>
          <span className="text-emerald-400 text-xs font-medium tracking-wide">Organization</span>
        </div>
        <h1 className="text-2xl font-bold tracking-tight">Enterprise Sign In</h1>
        <p className="text-sm text-white/40">Access your organization's workspace</p>
      </div>

      {/* Divider with SSO hint */}
      <div className="relative text-center text-sm text-black/80 sdark:text-white/25 after:absolute after:inset-0 after:top-1/2 after:border-t after:border-white/10">
        <span className="relative z-10 dark:bg-[#111715] px-3">Single Sign-On</span>
      </div>

      {/* SSO button */}
      <button
        type="button"
        disabled
        className="
          w-full flex items-center justify-center gap-2.5
          py-2.5 px-4 rounded-lg
          border border-emerald-700/50 bg-emerald-900/20
          text-emerald-300/70 text-sm font-medium
          cursor-not-allowed opacity-60
          transition-colors
        "
      >
        <svg className="w-4 h-4" fill="none" stroke="currentColor" strokeWidth={1.8} viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" d="M15.75 5.25a3 3 0 013 3m3 0a6 6 0 01-7.029 5.912c-.563-.097-1.159.026-1.563.43L10.5 17.25H8.25v2.25H6v2.25H2.25v-2.818c0-.597.237-1.17.659-1.591l6.499-6.499c.404-.404.527-1 .43-1.563A6 6 0 1121.75 8.25z" />
        </svg>
        Continue with SSO
        <span className="ml-auto text-xs text-white/25 font-normal">Coming soon</span>
      </button>

      <div className="relative text-center text-xs text-black/80 dark:text-white/25 after:absolute after:inset-0 after:top-1/2 after:border-t after:border-white/10">
        ----<span className="relative z-10 bg-sidebar-ring dark:bg-[#111715] px-3">or use credentials</span>----
      </div>

      {/* Form — no handlers yet */}
      <form onSubmit={(e) => e.preventDefault()} className="flex flex-col gap-3">
        <div className="flex flex-col gap-1.5">
          <Label htmlFor="org-domain" className={labelCls}>Organization Domain</Label>
          <div className="relative">
            <Input
              id="org-domain"
              type="text"
              name="domain"
              placeholder="yourcompany"
              className={`${inputCls} pr-28`}
            />
            <span className="absolute right-3 top-1/2 -translate-y-1/2 text-white/25 text-sm select-none">
              .example.com
            </span>
          </div>
        </div>

        <div className="flex flex-col gap-1.5">
          <Label htmlFor="org-email" className={labelCls}>Work Email</Label>
          <Input
            id="org-email"
            type="email"
            name="email"
            placeholder="you@yourcompany.com"
            className={inputCls}
          />
        </div>

        <div className="flex flex-col gap-1.5">
          <div className="flex items-center justify-between">
            <Label htmlFor="org-password" className={labelCls}>Password</Label>
            <button type="button" className="text-xs text-emerald-500/60 hover:text-emerald-400 transition-colors">
              Forgot password?
            </button>
          </div>
          <Input
            id="org-password"
            name="password"
            type="password"
            placeholder="••••••••"
            className={inputCls}
          />
        </div>

        {/* Not-implemented notice */}
        <div className="flex items-start gap-2 px-3 py-2.5 rounded-lg bg-emerald-950/60 border border-emerald-800/40 mt-1">
          <svg className="w-4 h-4 text-emerald-500/70 mt-0.5 shrink-0" fill="none" stroke="currentColor" strokeWidth={1.8} viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" d="M11.25 11.25l.041-.02a.75.75 0 011.063.852l-.708 2.836a.75.75 0 001.063.853l.041-.021M21 12a9 9 0 11-18 0 9 9 0 0118 0zm-9-3.75h.008v.008H12V8.25z" />
          </svg>
          <p className="text-xs text-emerald-500/60 leading-relaxed">
            Organization login is not yet implemented. Submissions will have no effect.
          </p>
        </div>

        <Button
          type="submit"
          disabled
          className="w-full mt-1 bg-emerald-700/50 text-emerald-200/50 font-semibold cursor-not-allowed border border-emerald-700/30"
        >
          Sign In to Organization
        </Button>
      </form>

      {/* Back link */}
      <p className="text-center text-sm text-white/30">
        Not part of an organization?{" "}
        <button
          type="button"
          onClick={onBackToUser}
          className="text-emerald-500/70 hover:text-emerald-400 underline underline-offset-4 transition-colors"
        >
          Personal login
        </button>
      </p>
    </div>
  );
}