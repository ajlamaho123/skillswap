const index = localStorage.getItem("editEventIndex");
const events = JSON.parse(localStorage.getItem("events")) || [];

const event = events[index];

if (!event) {
  window.location.href = "dashboard-host.html";
}

title.value = event.title;
category.value = event.category;
host.value = event.host;
spots.value = event.spots;

document.getElementById("editEventForm").addEventListener("submit", function (e) {
  e.preventDefault();

  events[index] = {
    ...event,
    title: title.value,
    category: category.value,
    host: host.value,
    spots: spots.value
  };

  localStorage.setItem("events", JSON.stringify(events));
  localStorage.removeItem("editEventIndex");

  window.location.href = "dashboard-host.html";
});
