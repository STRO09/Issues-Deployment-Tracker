import { NextRequest, NextResponse } from "next/server";

export async function GET(req: NextRequest, { params }: { params: { path: string[] } }) {
  return proxyRequest(req, params);
}

export async function POST(req: NextRequest, { params }: { params: { path: string[] } }) {
  return proxyRequest(req, params);
}

async function proxyRequest(req: NextRequest, params: { path: string[] }) {
  const backendUrl = `https://issues-deployment-tracker-backend.onrender.com/${params.path.join('/')}`;

  const body = req.method !== "GET" && req.method !== "HEAD" ? await req.text() : undefined;

  const response = await fetch(backendUrl, {
    method: req.method,
    headers: {
      ...Object.fromEntries(req.headers.entries()),
      host: new URL(backendUrl).host,
    },
    body,
  });

  const resBody = await response.text();
  return new NextResponse(resBody, {
    status: response.status,
    headers: { "Content-Type": response.headers.get("content-type") || "text/plain" },
  });
}
