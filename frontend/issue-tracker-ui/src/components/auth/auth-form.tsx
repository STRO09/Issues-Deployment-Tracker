"use client";
import { useState, useEffect } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { LoginForm } from "./LoginFormPanel";
import { RegisterForm } from "./RegisterFormPanel";

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

export function AuthForm() {
  const [isLogin, setIsLogin] = useState(true); // login/register toggle
  const [animating, setAnimating] = useState(false); // blocks form rendering until switch ends
  const isMobile = useIsMobile();

  const handleSwitch = () => {
    setAnimating(true); // start hiding form contents
    setTimeout(() => {
      setIsLogin(!isLogin); // switch form type mid-animation
      setAnimating(false); // re-enable form rendering
    }, 50); // duration should match your CSS/motion transition
  };

  return (
    <div className="flex h-screen w-full items-center justify-center bg-gray-100">
      <div className="relative flex w-[800px] h-[650px] overflow-hidden rounded-2xl shadow-lg bg-white">
        {/* Image / Side Panel */}
        <motion.div
          animate={{ x: isLogin ? 0 : "100%" }}
          transition={{ duration: 0.5 }}
          className="hidden md:flex    /* 🔴 hide completely on mobile */ 
          absolute top-0 left-0 h-full w-1/2
          bg-gradient-to-br from-indigo-500 to-purple-500
          items-center justify-center text-white text-2xl font-bold"
        >
          {isLogin ? "Welcome Back!" : "Join Us Today!"}
        </motion.div>

        {/* Form Panel */}
        <motion.div
          animate={{ x: isMobile ? 0 : isLogin ? "100%" : 0 }}
          transition={{ duration: 0.5 }}
          className="relative w-full right-0  /* 🔴 full width on mobile */
          md:absolute md:top-0 md:left-0 md:h-full md:w-1/2
          flex items-center justify-center p-10"
        >
          <AnimatePresence mode="wait">
            {!animating && (
              <motion.div
                key={isLogin ? "login" : "register"}
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                exit={{ opacity: 0 }}
                transition={{ duration: 0.3 }}
                className="w-full max-w-sm"
              >
                {isLogin ? (
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
    </div>
  );
}
