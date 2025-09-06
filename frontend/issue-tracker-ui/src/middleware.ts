// middleware.ts
import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";

export function middleware(req: NextRequest) {
  const token = req.cookies.get("token")?.value;

  // Protect everything under / (except /auth routes)
  if (!token && !req.nextUrl.pathname.startsWith("/auth")) {
    return NextResponse.redirect(new URL("/auth", req.url));
  }

  return NextResponse.next();
}

// Match all routes except static files and API routes
export const config = {
  matcher: ["/((?!_next/static|_next/image|favicon.ico|auth).*)"],
};
