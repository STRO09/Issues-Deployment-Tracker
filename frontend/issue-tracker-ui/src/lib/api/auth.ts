const BASE_URL = process.env.NEXT_PUBLIC_BACKEND_URL;

export async function RegisterUser(
  fullName: string,
  email: string,
  password: string
) {
  const res = await fetch(`${BASE_URL}/api/auth/register`, {
    method: "POST",
    headers: { "Content-type": "application/json" },
    body: JSON.stringify({
      fullName,
      email,
      password,
    }),
  });
  const data = await res.json();
  if (!res.ok) throw new Error(data.message || "Registration failed");
  return data;
}

export async function LoginUser(email: string, password: string) {
  const res = await fetch(`${BASE_URL}/api/auth/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ email, password }),
    credentials: "include",
  });
  const data = await res.json();
  if (!res.ok) {
    alert(data.message || "Login failed.");
  }
  return data;
}

export async function validateUser() {
  const res = await fetch(`${BASE_URL}/api/auth/validate`, {
    method: "GET",
    credentials: "include", // send cookie automatically
  });

  const data = await res.json();
  if (!res.ok) {
    throw new Error(data.message || "Validation failed");
  }
  return data;
}

export async function logoutUser() {
  const res = await fetch(`${BASE_URL}/api/auth/logout`, {
    method: "POST",
    credentials: "include", // send cookies
  });

  const data = await res.json();
  if (res.ok) {
    // Optional: clear any client state
    localStorage.clear();
    sessionStorage.clear();
  } else {
    throw new Error(data.message || "Logout Error");
  }
  return data;
}
