async function register() {
  const fullName = document.getElementById("registerName").value.trim();
  const email = document.getElementById("registerEmail").value.trim();
  const password = document.getElementById("registerPassword").value;

  const role = document.querySelector('input[name="role"]:checked')?.value || "user";
  const roli = role === "host" ? "ADMIN" : "PERDORUES";

  if (!fullName || !email || !password) {
    alert("Ploteso te gjitha fushat.");
    return;
  }

  const parts = fullName.split(" ").filter(Boolean);
  const emri = parts[0];
  const mbiemri = parts.slice(1).join(" ") || "-";

  const res = await fetch("/api/auth/regjistro", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ emri, mbiemri, email, fjalekalim: password, roli })
  });

  const data = await res.json().catch(() => ({}));
  if (!res.ok) {
    alert(data.gabim || data.message || "Regjistrimi deshtoi.");
    return;
  }

  alert("✅ U regjistrove me sukses! Tani bej login.");
  window.location.href = "/login.html";
}

async function login() {
  const email = document.getElementById("loginEmail").value.trim();
  const password = document.getElementById("loginPassword").value;

  if (!email || !password) {
    alert("Ploteso email dhe password.");
    return;
  }

  try {
    const res = await fetch("/api/auth/hyr", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, fjalekalim: password })
    });

    const data = await res.json().catch(() => ({}));

    if (!res.ok) {
      alert(data.gabim || data.message || "Login deshtoi.");
      return;
    }

    // ✅ ruaj userin e sakte
    localStorage.setItem("userId", String(data.id));
    localStorage.setItem("loggedUser", JSON.stringify(data));
    localStorage.setItem("loggedIn", "true");
    localStorage.setItem("role", data.roli === "ADMIN" ? "host" : "user");

    if (data.roli === "ADMIN") window.location.href = "/dashboard-host.html";
    else window.location.href = "/dashboard-user.html";
  } catch (err) {
    console.error(err);
    alert("Gabim lidhjeje me serverin. A eshte ndezur backend?");
  }
}
