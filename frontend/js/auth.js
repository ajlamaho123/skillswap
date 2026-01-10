function register() {
  const name = document.getElementById("registerName").value;
  const role = document.querySelector('input[name="role"]:checked').value;

  localStorage.setItem("loggedIn", "true");
  localStorage.setItem("role", role);

  if (role === "host") {
    localStorage.setItem("hostName", name);
    window.location.href = "dashboard-host.html";
  } else {
    window.location.href = "dashboard-user.html";
  }
}

function login() {
  // marr role nga localStorage
  const role = localStorage.getItem("role") || "user";

  localStorage.setItem("loggedIn", "true");

  if (role === "host") {
    window.location.href = "dashboard-host.html";
  } else {
    window.location.href = "dashboard-user.html";
  }
}