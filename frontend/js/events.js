const currentUser = JSON.parse(localStorage.getItem("currentUser"));
if (!currentUser) {
  window.location.href = "login.html";
}

// CREATE EVENT (HOST)
const createEventForm = document.getElementById("createEventForm");
if (createEventForm) {
  createEventForm.addEventListener("submit", function (e) {
    e.preventDefault();

    const event = {
      id: Date.now().toString(),
      title: document.getElementById("title").value,
      description: document.getElementById("description").value,
      date: document.getElementById("date").value,
      host: currentUser.email,
      participants: []
    };

    createEvent(event);
    window.location.href = "dashboard-host.html";
  });
}

// LIST EVENTS
const eventsContainer = document.getElementById("eventsContainer");
if (eventsContainer) {
  const events = getEvents();

  events.forEach(event => {
    const div = document.createElement("div");
    div.classList.add("event-card");

    div.innerHTML = `
      <h3>${event.title}</h3>
      <p>${event.description}</p>
      <p>Date: ${event.date}</p>
      <button data-id="${event.id}">Join</button>
    `;

    div.querySelector("button").addEventListener("click", () => {
      joinEvent(event.id, currentUser.email);
      alert("You joined the event!");
    });

    eventsContainer.appendChild(div);
  });
}
