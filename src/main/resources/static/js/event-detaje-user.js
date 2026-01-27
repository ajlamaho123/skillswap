const container = document.getElementById("eventContainer");

function getId() {
  return new URLSearchParams(window.location.search).get("id");
}

function getUser() {
  return JSON.parse(localStorage.getItem("loggedUser"));
}

async function apiGet(url) {
  const r = await fetch(url);
  const d = await r.json();
  if (!r.ok) throw new Error(d.gabim);
  return d;
}

async function apiPost(url, body) {
  const r = await fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(body)
  });
  const d = await r.json();
  if (!r.ok) throw new Error(d.gabim);
  return d;
}

async function isJoined(eventId, userId) {
  const r = await apiGet(`/api/perdorues/${userId}/regjistrime`);
  return r.some(e => String(e.eventId) === String(eventId));
}

function render(event, joined) {
  container.innerHTML = `
    <h2>${event.titulli}</h2>
    <p><b>Category:</b> ${event.kategoriEmri}</p>
    <p><b>Host:</b> ${event.hostEmri} ${event.hostMbiemri}</p>
    <p><b>Date:</b> ${event.data} ${event.ora}</p>
    <p><b>Location:</b> ${event.vendndodhja}</p>
    <p>${event.pershkrimi}</p>

    <div id="action">
      ${joined
        ? "<p style='color:green'>✔ Je i regjistruar</p>"
        : "<button class='btn' id='joinBtn'>Join Event</button>"
      }
    </div>
  `;

  if (!joined) {
    document.getElementById("joinBtn").onclick = async () => {
      await apiPost(`/api/evente/${event.id}/regjistrime`, {
        perdoruesId: getUser().id
      });
      alert("U regjistrove me sukses");
      location.reload();
    };
  }
}

(async () => {
  const user = getUser();
  if (!user || localStorage.getItem("role") !== "user") {
    alert("Access denied");
    return;
  }

  const event = await apiGet(`/api/evente/${getId()}`);
  const joined = await isJoined(event.id, user.id);
  render(event, joined);
})();
