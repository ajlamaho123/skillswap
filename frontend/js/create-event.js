const form = document.getElementById("createEventForm");

form.addEventListener("submit", function (e) {
  e.preventDefault();

  const events = JSON.parse(localStorage.getItem("events")) || [];

  const newEvent = {
    id: Date.now(),
    title: title.value,
    category: category.value,
    host: host.value,
    spots: spots.value
  };

  events.push(newEvent);
  localStorage.setItem("events", JSON.stringify(events));

  window.location.href = "dashboard-host.html";
});
