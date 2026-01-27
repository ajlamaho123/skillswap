// ================= AUTH GUARD =================
const currentUser = JSON.parse(localStorage.getItem("loggedUser"));
if (!currentUser) window.location.href = "/login.html";


if (currentUser.role !== "host") {
  window.location.href = "dashboard-user.html";
}

// ================= LOAD HOST EVENTS =================
const hostEventsContainer = document.getElementById("hostEvents");

function loadHostEvents() {
  const events = JSON.parse(localStorage.getItem("events")) || [];

  hostEventsContainer.innerHTML = "";

  const hostEvents = events.filter(
    event => event.host === currentUser.email
  );

  if (hostEvents.length === 0) {
    hostEventsContainer.innerHTML = "<p>No events created yet.</p>";
    return;
  }

  hostEvents.forEach(event => {
    const div = document.createElement("div");
    div.classList.add("event-card");

    div.innerHTML = `
      <h3>${event.title}</h3>
      <p>${event.description}</p>
      <p><strong>Date:</strong> ${event.date}</p>
      <p><strong>Participants:</strong> ${event.participants.length}</p>
    `;

    hostEventsContainer.appendChild(div);
  });
}

loadHostEvents();

// ================= LOGOUT =================
const logoutBtn = document.getElementById("logoutBtn");
if (logoutBtn) {
  logoutBtn.addEventListener("click", function () {
    localStorage.removeItem("currentUser");
    window.location.href = "login.html";
  });
}
