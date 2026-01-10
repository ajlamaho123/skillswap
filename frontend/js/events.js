/* ===============================
   DEFAULT EVENTS (always visible)
================================ */

const defaultEvents = [
  {
    id: 1,
    title: "Learn Java in 2 hours",
    category: "Programming",
    host: "Alex",
    spots: 10
  },
  {
    id: 2,
    title: "Women-only self-defense",
    category: "Fitness",
    host: "Sara",
    spots: 8
  },
  {
    id: 3,
    title: "Photography walk in the city",
    category: "Photography",
    host: "Mark",
    spots: 12
  }
];

/* ===============================
   HOST CREATED EVENTS
================================ */

const hostEvents = JSON.parse(localStorage.getItem("events")) || [];

/* ===============================
   ALL EVENTS (merged)
================================ */

const allEvents = [...defaultEvents, ...hostEvents];

/* ===============================
   RENDER EVENTS – event.html
================================ */

const eventsContainer = document.getElementById("events");

if (eventsContainer) {
  eventsContainer.innerHTML = "";

  allEvents.forEach(event => {
    eventsContainer.innerHTML += `
      <div class="event-card">
        <h3>${event.title}</h3>
        <p>${event.category}</p>
        <p>Host: ${event.host}</p>
        <p>Spots: ${event.spots}</p>
        <button class="btn" onclick="viewEvent(${event.id})">
          View Details
        </button>
      </div>
    `;
  });
}

/* ===============================
   VIEW EVENT DETAILS
================================ */

function viewEvent(id) {
  localStorage.setItem("selectedEventId", id);
  window.location.href = "event-detaje.html";
}

/* ===============================
   USER DASHBOARD – joined events
================================ */

const dashboardContainer = document.getElementById("my-events");
const joinedEvents = JSON.parse(localStorage.getItem("joinedEvents")) || [];

if (dashboardContainer) {

  if (joinedEvents.length === 0) {
    dashboardContainer.innerHTML = `
      <div class="glass-card">
        <h3>No events yet</h3>
        <p>You haven’t joined any events.</p>
        <a href="event.html" class="btn">Browse Events</a>
      </div>
    `;
  } else {
    joinedEvents.forEach(event => {
      dashboardContainer.innerHTML += `
        <div class="event-card">
          <h3>${event.title}</h3>
          <p>${event.category}</p>
          <p>Host: ${event.host}</p>
        </div>
      `;
    });
  }
}

const container = document.getElementById("availableEvents");

if (container) {
  events.forEach(event => {
    const card = document.createElement("div");
    card.className = "glass-card";
    card.innerHTML = `
      <h3>${event.title}</h3>
      <p><strong>Category:</strong> ${event.category}</p>
      <p><strong>Host:</strong> ${event.host}</p>
      <p><strong>Spots:</strong> ${event.spots}</p>
      <button class="btn" onclick="viewEvent(${event.id})">View</button>
    `;
    container.appendChild(card);
  });
}

function viewEvent(id) {
  localStorage.setItem("selectedEventId", id);
  window.location.href = "event-detaje.html";
}
const hostContainer = document.getElementById("hostEvents");
const hostName = localStorage.getItem("hostName") || "Ajla";


if (hostContainer) {
  const myEvents = events.filter(e => e.host === hostName);

  document.getElementById("totalEvents").innerText = myEvents.length;

  myEvents.forEach(event => {
    const card = document.createElement("div");
    card.className = "glass-card";
    card.innerHTML = `
      <h3>${event.title}</h3>
      <p><strong>Category:</strong> ${event.category}</p>
      <p><strong>Spots:</strong> ${event.spots}</p>
      <button class="btn" onclick="editEvent(${event.id})">Edit</button>
    `;
    hostContainer.appendChild(card);
  });
}

function editEvent(id) {
  localStorage.setItem("editEventId", id);
  window.location.href = "edit-event.html";
}

// Nëse nuk ekziston localStorage.events, e ruaj
if (!localStorage.getItem("events")) {
  localStorage.setItem("events", JSON.stringify(events));
}

// Merr events nga localStorage për të gjithë skriptet e tjerë
const eventsFromStorage = JSON.parse(localStorage.getItem("events"));
