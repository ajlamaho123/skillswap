const container = document.getElementById("host-events");
const events = JSON.parse(localStorage.getItem("events")) || [];

if (events.length === 0) {
  container.innerHTML = `
    <div class="glass-card">
      <h3>No events created</h3>
      <p>Create your first event and start teaching.</p>
      <a href="create-event.html" class="btn">Create Event</a>
    </div>
  `;
} else {
  events.forEach((event, index) => {
    container.innerHTML += `
      <div class="event-card">
        <h3>${event.title}</h3>
        <p>${event.category}</p>
        <p>Spots: ${event.spots}</p>

        <button class="btn" onclick="editEvent(${index})">Edit</button>
        <button class="btn" onclick="deleteEvent(${index})">Delete</button>
      </div>
    `;
  });
}

function deleteEvent(index) {
  events.splice(index, 1);
  localStorage.setItem("events", JSON.stringify(events));
  location.reload();
}

function editEvent(index) {
  localStorage.setItem("editEventIndex", index);
  window.location.href = "edit-event.html";
}