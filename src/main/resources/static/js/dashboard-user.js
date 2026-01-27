const available = document.getElementById("availableEvents");
const joined = document.getElementById("joinedEvents");

(async function init(){
  const u = getLoggedUser();
  if (!u) return;

  try {
    const events = await apiGet("/api/evente");

    available.innerHTML = (events || []).map(e => `
      <div class="glass-card" style="cursor:pointer" onclick="location.href='/event-detaje.html?id=${e.id}'">
        <h3>${e.titulli}</h3>
        <p>${e.kategoriEmri} • ${e.data} ${e.ora}</p>
        <p>Host: ${e.hostEmri} ${e.hostMbiemri}</p>
      </div>
    `).join("");

    // Për "Your Events" do duhet endpoint i regjistrimeve të userit.
    // Ti e ke përmendur që e ke: /api/perdorues/{id}/regjistrime
    const regs = await apiGet(`/api/perdorues/${u.id}/regjistrime`);

    joined.innerHTML = (regs || []).map(r => `
      <div class="glass-card">
        <h3>Event #${r.eventId}</h3>
        <p>Status: ${r.statusi}</p>
        <a class="btn small-btn" href="/event-detaje.html?id=${r.eventId}">Open</a>
      </div>
    `).join("") || `<div class="glass-card"><h3>No joined events</h3></div>`;

  } catch(err){
    console.error(err);
    available.innerHTML = `<div class="glass-card"><h3>Error</h3><p>${err.message}</p></div>`;
  }
})();
