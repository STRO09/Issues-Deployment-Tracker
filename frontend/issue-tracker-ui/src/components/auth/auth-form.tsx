"use client";
import { useState, useEffect } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { LoginForm } from "./LoginFormPanel";
import { RegisterForm } from "./RegisterFormPanel";
import { OrgLoginForm } from "./OrgLoginFormPanel";

function useIsMobile() {
  const [isMobile, setIsMobile] = useState(false);
  useEffect(() => {
    const check = () => setIsMobile(window.innerWidth < 768);
    check();
    window.addEventListener("resize", check);
    return () => window.removeEventListener("resize", check);
  }, []);
  return isMobile;
}

type Mode = "user" | "org";

export function AuthForm() {
  const [isLogin, setIsLogin] = useState(true);
  const [animating, setAnimating] = useState(false);
  const [mode, setMode] = useState<Mode>("user");
  const [cardVisible, setCardVisible] = useState(true);
  const isMobile = useIsMobile();

  const handleSwitch = () => {
    setAnimating(true);
    setTimeout(() => {
      setIsLogin((prev) => !prev);
      setAnimating(false);
    }, 50);
  };

  const handleModeSwitch = (next: Mode) => {
    if (next === mode) return;
    setCardVisible(false);
    setTimeout(() => {
      setMode(next);
      setIsLogin(true);
      setCardVisible(true);
    }, 420);
  };

  const isOrg = mode === "org";

  return (
    <div className="min-h-screen w-full flex flex-col bg-muted">

      {/* ── Marquee banner ── */}
      <div className={`w-full text-sm font-semibold py-1.5 overflow-hidden shrink-0 ${isOrg ? "bg-emerald-400 text-black" : "bg-amber-400 text-black"}`}>
        {/* Duplicate content so the seamless loop works: first copy scrolls out, second fills in */}
        <div className="marquee-container">
          <div className="marquee">
            {isOrg
              ? "🚧 Organization login is not yet implemented — check back soon \u00A0\u00A0\u00A0\u00A0\u00A0\u00A0 🚧 Organization login is not yet implemented — check back soon \u00A0\u00A0\u00A0\u00A0\u00A0\u00A0 🚧 Organization login is not yet implemented — check back soon \u00A0\u00A0\u00A0\u00A0\u00A0\u00A0 🚧 Organization login is not yet implemented — check back soon \u00A0\u00A0\u00A0\u00A0\u00A0\u00A0"
              : "⚠ Backend service is currently suspended — contact me to request a live demo \u00A0\u00A0\u00A0\u00A0\u00A0\u00A0 ⚠ Backend service is currently suspended — contact us to request a live demo \u00A0\u00A0\u00A0\u00A0\u00A0\u00A0 ⚠ Backend service is currently suspended — contact us to request a live demo \u00A0\u00A0\u00A0\u00A0\u00A0\u00A0 ⚠ Backend service is currently suspended — contact us to request a live demo \u00A0\u00A0\u00A0\u00A0\u00A0\u00A0"}
          </div>
        </div>
      </div>

      {/* ── Main area ── */}
      <div className="flex flex-1 items-center justify-center p-4">
        <div className="flex flex-col items-center gap-5 w-full max-w-4xl">

          {/* ── Card ── */}
          <motion.div
            animate={cardVisible ? { y: 0, opacity: 1, scale: 1 } : { y: -72, opacity: 0, scale: 0.95 }}
            transition={{ type: "spring", stiffness: 300, damping: 28 }}
            className="w-full"
          >
            <div
              className={`
                relative flex w-full overflow-hidden rounded-2xl shadow-xl border border-border
                flex-col md:flex-row
                min-h-[520px] md:h-[600px]
                bg-card
              `}
            >
              {/* ── Decorative side panel ── */}
              <motion.div
                animate={isMobile ? { y: 0 } : { x: !isOrg && isLogin ? 0 : "100%" }}
                transition={{ type: "spring", stiffness: 260, damping: 28 }}
                className={`
                  md:absolute md:inset-y-0 md:left-0 md:w-1/2
                  relative flex flex-col items-center justify-center
                  h-36 md:h-full overflow-hidden select-none
                  ${isOrg
                    ? "bg-gradient-to-br from-emerald-700 via-teal-600 to-cyan-700"
                    : "bg-gradient-to-br from-violet-600 via-indigo-500 to-blue-500"}
                `}
              >
                {/* Grid texture */}
                <div
                  className="absolute inset-0 opacity-20"
                  style={{
                    backgroundImage:
                      "linear-gradient(rgba(255,255,255,.18) 1px, transparent 1px), linear-gradient(90deg, rgba(255,255,255,.18) 1px, transparent 1px)",
                    backgroundSize: "40px 40px",
                  }}
                />
                <div className="absolute w-64 h-64 rounded-full bg-white/10 blur-3xl -bottom-12 -right-12" />

                {/* Desktop content */}
                <div className="relative z-10 text-center px-8 hidden md:flex flex-col items-center">
                  <AnimatePresence mode="wait">
                    <motion.div
                      key={`${mode}-${isLogin}`}
                      initial={{ opacity: 0, y: 14 }}
                      animate={{ opacity: 1, y: 0 }}
                      exit={{ opacity: 0, y: -14 }}
                      transition={{ duration: 0.28 }}
                      className="flex flex-col items-center"
                    >
                      {isOrg ? (
                        <>
                          <div className="mb-5 w-16 h-16 rounded-2xl bg-white/10 border border-white/20 flex items-center justify-center">
                            <svg className="w-8 h-8 text-white/80" fill="none" stroke="currentColor" strokeWidth={1.5} viewBox="0 0 24 24">
                              <path strokeLinecap="round" strokeLinejoin="round" d="M3.75 21h16.5M4.5 3h15M5.25 3v18m13.5-18v18M9 6.75h1.5m-1.5 3h1.5m-1.5 3h1.5m3-6H15m-1.5 3H15m-1.5 3H15M9 21v-3.375c0-.621.504-1.125 1.125-1.125h3.75c.621 0 1.125.504 1.125 1.125V21" />
                            </svg>
                          </div>
                          <h2 className="text-white text-2xl font-bold tracking-tight mb-2">Organization Portal</h2>
                          <p className="text-white/70 text-sm leading-relaxed">Secure access for teams<br />and enterprise accounts.</p>
                        </>
                      ) : (
                        <>
                          {/* <p className="text-5xl mb-4">{isLogin ? "" : ""}</p> */}
                          <h2 className="text-white text-2xl font-bold tracking-tight mb-2">
                            {isLogin ? "Welcome back!" : "Join us today!"}
                          </h2>
                          <p className="text-white/70 text-sm leading-relaxed">
                            {isLogin ? "Sign in to continue where\nyou left off." : "Create your account and\nget started in seconds."}
                          </p>
                        </>
                      )}
                    </motion.div>
                  </AnimatePresence>
                </div>

                {/* Mobile headline */}
                <div className="relative z-10 text-center md:hidden">
                  <p className="text-white font-bold text-lg">
                    {isOrg ? "🏢 Organization Portal" : isLogin ? "Welcome back 👋" : "Join us ✨"}
                  </p>
                </div>
              </motion.div>

              {/* ── Form panel ── */}
              <motion.div
                animate={isMobile ? { x: 0 } : { x: !isOrg && isLogin ? "100%" : 0 }}
                transition={{ type: "spring", stiffness: 260, damping: 28 }}
                className="
                  relative w-full
                  md:absolute md:inset-y-0 md:left-0 md:w-1/2
                  flex items-center justify-center
                  px-6 py-8 md:px-10 overflow-y-auto
                "
              >
                <AnimatePresence mode="wait">
                  {!animating && (
                    <motion.div
                      key={isOrg ? "org" : isLogin ? "login" : "register"}
                      initial={{ opacity: 0, y: 8 }}
                      animate={{ opacity: 1, y: 0 }}
                      exit={{ opacity: 0, y: -8 }}
                      transition={{ duration: 0.25 }}
                      className="w-full max-w-sm"
                    >
                      {isOrg ? (
                        <OrgLoginForm onBackToUser={() => handleModeSwitch("user")} />
                      ) : isLogin ? (
                        <LoginForm handleSwitch={handleSwitch} />
                      ) : (
                        <RegisterForm
                          handleSwitch={handleSwitch}
                          isLogin={isLogin}
                          setIsLogin={setIsLogin}
                        />
                      )}
                    </motion.div>
                  )}
                </AnimatePresence>
              </motion.div>
            </div>
          </motion.div>

          {/* ── Mode toggle link ── */}
          <motion.div
            animate={cardVisible ? { opacity: 1, y: 0 } : { opacity: 0, y: 8 }}
            transition={{ duration: 0.25, delay: cardVisible ? 0.1 : 0 }}
          >
            {isOrg ? (
              <button
                onClick={() => handleModeSwitch("user")}
                className="flex items-center gap-1.5 text-sm text-emerald-600 dark:text-emerald-400 hover:opacity-80 transition-opacity group"
              >
                <svg className="w-3.5 h-3.5 group-hover:-translate-x-0.5 transition-transform" fill="none" stroke="currentColor" strokeWidth={2} viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" d="M15.75 19.5L8.25 12l7.5-7.5" />
                </svg>
                Back to personal login
              </button>
            ) : (
              <button
                onClick={() => handleModeSwitch("org")}
                className="flex items-center gap-1.5 text-sm text-muted-foreground hover:text-foreground transition-colors group"
              >
                <svg className="w-3.5 h-3.5" fill="none" stroke="currentColor" strokeWidth={1.5} viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" d="M3.75 21h16.5M4.5 3h15M5.25 3v18m13.5-18v18M9 6.75h1.5m-1.5 3h1.5m-1.5 3h1.5m3-6H15m-1.5 3H15m-1.5 3H15M9 21v-3.375c0-.621.504-1.125 1.125-1.125h3.75c.621 0 1.125.504 1.125 1.125V21" />
                </svg>
                Login for organizations
                <svg className="w-3 h-3 group-hover:translate-x-0.5 transition-transform" fill="none" stroke="currentColor" strokeWidth={2} viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" d="M8.25 4.5l7.5 7.5-7.5 7.5" />
                </svg>
              </button>
            )}
          </motion.div>

        </div>
      </div>
    </div>
  );
}