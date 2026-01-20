// ================= USERS =================
function getUsers() {
  return JSON.parse(localStorage.getItem("users")) || [];
}

function saveUsers(users) {
  localStorage.setItem("users", JSON.stringify(users));
}

function getCurrentUser() {
  return JSON.parse(localStorage.getItem("currentUser"));
}

// ================= EVENTS =================
function getEvents() {
  return JSON.parse(localStorage.getItem("events")) || [];
}

function saveEvents(events) {
  localStorage.setItem("events", JSON.stringify(events));
}

function createEvent(event) {
  const events = getEvents();
  events.push(event);
  saveEvents(events);
}

function joinEvent(eventId, userEmail) {
  const events = getEvents();

  const event = events.find(e => e.id === eventId);
  if (!event) return;

  if (!event.participants.includes(userEmail)) {
    event.participants.push(userEmail);
    saveEvents(events);
  }
}
