// js/evente.js

// function eventImageByCategory(kategoriEmri) {
//   const k = (kategoriEmri || "").toLowerCase();
//   if (k.includes("program")) return "/img/programming.jpg";
//   if (k.includes("fotograf")) return "/img/photography.jpg";
//   if (k.includes("gjuh")) return "/img/languages.jpg";
//   if (k.includes("fitness") || k.includes("sport")) return "/img/fitness.jpg";
//   return "/img/hero.jpg"; // fallback
// }

function pickImageForEvent(ev){
  const k = (ev.kategoriEmri || "").toLowerCase();

  if (k.includes("program")) return "https://images.unsplash.com/photo-1636586108931-a8b9b8796ba6?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";

  if (k.includes("foto"))    return "https://images.unsplash.com/photo-1543399151-5dc42f103557?w=1000&auto=format&fit=crop&q=80&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1yZWxhdGVkfDEzfHx8ZW58MHx8fHx8";
  if (k.includes("fit"))     return "https://images.unsplash.com/photo-1517836357463-d25dfeac3438?q=80&w=870&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
  if (k.includes("gju"))     return "https://images.unsplash.com/photo-1632481185419-bc03b5082756?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";

  return "https://images.unsplash.com/photo-1490645935967-10de6ba17061?q=80&w=853&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
}

function getLoggedUser() {
  const u = localStorage.getItem("loggedUser");
  return u ? JSON.parse(u) : null;
}

async function apiGet(url) {
  const res = await fetch(url);
  if (!res.ok) throw new Error(`API error ${res.status}`);
  return res.json();
}

/* =========================
   1) INDEX PAGE (featured)
   ========================= */
async function loadFeaturedEvents() {
  const container = document.getElementById("eventsContainer");
  if (!container) return;

  try {
    const events = await apiGet("/api/evente");

    if (!events || events.length === 0) {
      container.innerHTML = `
        <div class="glass-card">
          <h3>No events available yet</h3>
          <p>New SkillSwap events are regularly added by hosts.</p>
        </div>
      `;
      return;
    }

    container.innerHTML = "";
    events.slice(0, 4).forEach(ev => {
      const img = ev.fotoUrl || "/img/event-default.jpg";

container.innerHTML += `
  <article class="event-card-v2" onclick="location.href='/event-detaje.html?id=${ev.id}'">
    <div class="event-card-v2__media img">
      <img src="${img}" alt="${ev.titulli}">
    </div>

    <div class="event-card-v2__body glass-card">
      <h3 class="event-card-v2__title">${ev.titulli}</h3>
      <p class="event-card-v2__meta"><strong>${ev.kategoriEmri}</strong> • ${ev.data} ${ev.ora}</p>
      <p class="event-card-v2__muted">📍 ${ev.vendndodhja}</p>
    </div>
  </article>
`;
    });

  } catch (e) {
    console.error(e);
    container.innerHTML = `<div class="glass-card"><h3>Error loading events</h3></div>`;
  }
}

/* =========================
   2) EVENT LIST PAGE (event.html)
   ========================= */
async function loadAllEventsPage() {
  const container = document.getElementById("events-container");
  if (!container) return;

  try {
    const events = await apiGet("/api/evente");
    if (!events || events.length === 0) {
      container.innerHTML = `
        <div class="glass-card">
          <h3>No events available yet</h3>
          <p>New SkillSwap events are regularly added by hosts.</p>
        </div>
      `;
      return;
    }

    container.innerHTML = "";
    events.forEach(ev => {
      const img = ev.fotoUrl || "/img/event-default.jpg";

container.innerHTML += `
  <article class="event-card-v2" onclick="location.href='/event-detaje.html?id=${ev.id}'">
    <div class="event-card-v2__media img">
      <img src="${img}" alt="${ev.titulli}">
    </div>

    <div class="event-card-v2__body glass-card">
      <h3 class="event-card-v2__title">${ev.titulli}</h3>
      <p class="event-card-v2__meta"><strong>${ev.kategoriEmri}</strong> • ${ev.data} ${ev.ora}</p>
      <p class="event-card-v2__muted">📍 ${ev.vendndodhja}</p>
    </div>
  </article>
`;
    });

  } catch (e) {
    console.error(e);
    container.innerHTML = `<div class="glass-card"><h3>Error loading events</h3></div>`;
  }
}

/* =========================
   3) HOST DASHBOARD (dashboard-host.html)
   ========================= */
async function loadHostDashboard() {
  const hostEvents = document.getElementById("hostEvents");
  const totalEvents = document.getElementById("totalEvents");
  if (!hostEvents || !totalEvents) return;

  const u = getLoggedUser();
  if (!u) return; // auth-check e bën redirect vetë te HTML

  try {
    const events = await apiGet("/api/evente");
    // Opsionale: filtro vetëm eventet e host-it sipas hostId
    const myEvents = events.filter(ev => ev.hostId === u.id);

    totalEvents.textContent = myEvents.length;
    hostEvents.innerHTML = "";

    myEvents.forEach(ev => {
      hostEvents.innerHTML += `
        <div class="glass-card">
          <h3>${ev.titulli}</h3>
          <p>${ev.data} • ${ev.ora}</p>
          <p>${ev.vendndodhja}</p>
          <a class="btn small-btn" href="/event-detaje.html?id=${ev.id}">View</a>
        </div>
      `;
    });
  } catch (e) {
    console.error(e);
    hostEvents.innerHTML = `<div class="glass-card"><h3>Error loading host events</h3></div>`;
  }
}

/* =========================
   4) USER DASHBOARD (dashboard-user.html)
   ========================= */
async function loadUserDashboard() {
  const availableEvents = document.getElementById("availableEvents");
  if (!availableEvents) return;

  try {
    const events = await apiGet("/api/evente");
    availableEvents.innerHTML = "";

    events.forEach(ev => {
  const img = ev.fotoUrl || "/img/event-default.jpg";

  availableEvents.innerHTML += `
    <article class="event-card-v2" onclick="location.href='/event-detaje.html?id=${ev.id}'">
      <div class="event-card-v2__media">
        <img src="${img}" alt="${ev.titulli}">
      </div>

      <div class="event-card-v2__body glass-card">
        <h3 class="event-card-v2__title">${ev.titulli}</h3>
        <p class="event-card-v2__meta"><strong>${ev.kategoriEmri}</strong> • ${ev.data} ${ev.ora}</p>
        <p class="event-card-v2__muted">📍 ${ev.vendndodhja || ""}</p>
      </div>
    </article>
  `;
});

  } catch (e) {
    console.error(e);
    availableEvents.innerHTML = `<div class="glass-card"><h3>Error loading events</h3></div>`;
  }
}

/* ========= RUN ========= */
if (document.getElementById("eventsContainer")) loadFeaturedEvents();
if (document.getElementById("events-container")) loadAllEventsPage();
if (document.getElementById("hostEvents")) loadHostDashboard();
if (document.getElementById("availableEvents")) loadUserDashboard();

