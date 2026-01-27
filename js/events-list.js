const container = document.getElementById("eventsContainer");

function renderCard(e) {
  return `
    <div class="glass-card" style="cursor:pointer" onclick="location.href='/event-detaje.html?id=${e.id}'">
      <h3>${e.titulli}</h3>
      <p><strong>Category:</strong> ${e.kategoriEmri}</p>
      <p><strong>Host:</strong> ${e.hostEmri} ${e.hostMbiemri}</p>
      <p>📍 ${e.vendndodhja}</p>
      <p>📅 ${e.data} • 🕒 ${e.ora}</p>
      <p>👥 Limit: ${e.kufiriPjesemarresve}</p>
    </div>
  `;
}

(async function init() {
  try {
    const events = await apiGet("/api/evente"); // backend yt
    if (!events || events.length === 0) {
      container.innerHTML = `<div class="glass-card"><h3>No events yet</h3></div>`;
      return;
    }
    container.innerHTML = events.map(renderCard).join("");
  } catch (err) {
    console.error(err);
    container.innerHTML = `<div class="glass-card"><h3>Error loading events</h3><p>${err.message}</p></div>`;
  }
})();
