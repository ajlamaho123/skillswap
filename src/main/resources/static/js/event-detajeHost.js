const container = document.getElementById("eventContainer");

function getEventId() {
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

async function apiPut(url, body) {
  const r = await fetch(url, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(body)
  });
  const d = await r.json();
  if (!r.ok) throw new Error(d.gabim);
  return d;
}

async function apiDelete(url) {
  const r = await fetch(url, { method: "DELETE" });
  const d = await r.json();
  if (!r.ok) throw new Error(d.gabim);
  return d;
}

function render(event) {
  container.innerHTML = `
    <h2>${event.titulli}</h2>
    <p><b>Category:</b> ${event.kategoriEmri}</p>
    <p><b>Date:</b> ${event.data} ${event.ora}</p>
    <p><b>Location:</b> ${event.vendndodhja}</p>
    <p>${event.pershkrimi}</p>

    <button class="btn" id="editBtn">Edit</button>
    <button class="btn" id="deleteBtn">Fshi</button>
  `;

  document.getElementById("editBtn").onclick = async () => {
     location.href = `/create-event.html?editId=${event.id}`;

    await apiPut(`/api/evente/${event.id}`, {
      hostId: getUser().id,
      kategoriId: event.kategoriId,
      titulli,
      pershkrimi: event.pershkrimi,
      data: event.data,
      ora: event.ora,
      vendndodhja: event.vendndodhja,
      kufiriPjesemarresve: event.kufiriPjesemarresve,
      fotoUrl: event.fotoUrl || ""
    });

    alert("Eventi u perditesua");
    location.reload();
  };

  document.getElementById("deleteBtn").onclick = async () => {
    if (!confirm("A je i sigurt?")) return;
    await apiDelete(`/api/evente/${event.id}?hostId=${getUser().id}`);
    alert("Eventi u fshi");
    location.href = "/dashboard-host.html";
  };
}

(async () => {
  const user = getUser();
  if (!user || localStorage.getItem("role") !== "host") {
    alert("Access denied");
    return;
  }

  const event = await apiGet(`/api/evente/${getEventId()}`);
  render(event);
})();
