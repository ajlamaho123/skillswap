function requireLogin(requiredRole = null) {
  const u = JSON.parse(localStorage.getItem("loggedUser") || "null");
  if (!u) {
    location.href = "/login.html";
    return null;
  }
  if (requiredRole && u.roli !== requiredRole) {
    location.href = "/index.html";
    return null;
  }
  return u;
}

async function apiGet(url) {
  const res = await fetch(url);
  const data = await res.json().catch(() => ({}));
  if (!res.ok) throw new Error(data.gabim || data.message || "API GET failed");
  return data;
}

async function apiPost(url, body) {
  const res = await fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(body || {})
  });
  const data = await res.json().catch(() => ({}));
  if (!res.ok) throw new Error(data.gabim || data.message || "API POST failed");
  return data;
}

function getLoggedUser() {
  const u = localStorage.getItem("loggedUser");
  return u ? JSON.parse(u) : null;
}
