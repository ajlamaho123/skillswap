const hostEvents = document.getElementById("hostEvents");
const totalEventsEl = document.getElementById("totalEvents");
const totalParticipantsEl = document.getElementById("totalParticipants");
const avgRatingEl = document.getElementById("avgRating");
const ratingCountEl = document.getElementById("ratingCount");


// ===== helpers =====
function getLoggedUser() {
  const u = localStorage.getItem("loggedUser");
  return u ? JSON.parse(u) : null;
}

async function apiGet(url) {
  const res = await fetch(url);
  const data = await res.json().catch(() => ({}));
  if (!res.ok) throw new Error(data.gabim || data.message || `API GET failed (${res.status})`);
  return data;
}

// ===== main =====
(async function init() {
  const u = getLoggedUser();
  if (!u) return;

  try {
    // ⚠️ këtu mbaje sipas backend-it tënd:
    // nëse eventet i ke /api/evente -> ndrysho këtë rresht
    const all = await apiGet("/api/evente").catch(() => apiGet("/api/event"));
    const mine = (all || []).filter(e => String(e.hostId) === String(u.id));

    totalEventsEl.textContent = mine.length;

    // Merr vlerësimet e host-it (një herë)
    const ratingSummary = await apiGet(`/api/host/${u.id}/vleresime`);

    // Për secilin event merr pjesëmarrësit
    const enriched = await Promise.all(
      mine.map(async (ev) => {
        const pjes = await apiGet(`/api/event/${ev.id}/pjesemarres?hostId=${u.id}`)
          .catch(() => apiGet(`/api/evente/${ev.id}/pjesemarres?hostId=${u.id}`));

        const pjesemarres = pjes || [];

        // filtro vlerësimet e host-it vetëm për këtë event
        const vleresimeEventi = (ratingSummary?.vleresime || []).filter(v => String(v.eventId) === String(ev.id));

        return {
          ...ev,
          pjesemarres,
          nrPjesemarres: pjesemarres.length,
          vleresimeEventi
        };
      })
);
      // ✅ TOTAL PARTICIPANTS (sum i te gjithe pjesemarresve ne eventet e host-it)
const totalParticipants = enriched.reduce((sum, e) => sum + (e.nrPjesemarres || 0), 0);
if (totalParticipantsEl) totalParticipantsEl.textContent = totalParticipants;

// ✅ MY RATINGS (mesatarja + numri)
try {
  // endpoint: GET /api/host/{id}/vleresime
  const r = await apiGet(`/api/host/${u.id}/vleresime`);
  if (avgRatingEl) avgRatingEl.textContent = (r?.mesatareRating ?? "—");
  if (ratingCountEl) ratingCountEl.textContent = r?.numriVleresimesh ? `(${r.numriVleresimesh})` : "";
} catch (e) {
  if (avgRatingEl) avgRatingEl.textContent = "—";
  if (ratingCountEl) ratingCountEl.textContent = "";
}

    

    hostEvents.innerHTML =
      enriched.map(ev => {
        const pjesList = ev.pjesemarres.length
          ? `<ul style="margin-top:8px;">
              ${ev.pjesemarres.map(p => `
                <li>
                  <strong>${p.emri} ${p.mbiemri}</strong> — <span>${p.email}</span>
                </li>
              `).join("")}
            </ul>`
          : `<p style="opacity:.8;">Askush s’eshte regjistruar ende.</p>`;

        const vlerList = ev.vleresimeEventi.length
          ? `<ul style="margin-top:8px;">
              ${ev.vleresimeEventi.map(v => `
                <li>
                  ⭐ ${v.yje} — ${v.koment || "(pa koment)"} <br/>
                  <small>${v.ngaEmri} ${v.ngaMbiemri} • ${v.dataKrijimit}</small>
                </li>
              `).join("")}
            </ul>`
          : `<p style="opacity:.8;">S’ka vlerësime ende.</p>`;

        return `
          <div class="glass-card">
            <h3>${ev.titulli}</h3>
            <p>${ev.kategoriEmri} • ${ev.data} ${ev.ora}</p>
            <p>📍 ${ev.vendndodhja}</p>

            <p>👥 Registered: <strong>${ev.nrPjesemarres}</strong></p>

            <details style="margin-top:10px;">
              <summary style="cursor:pointer;">Shiko pjesëmarrësit</summary>
              ${pjesList}
            </details>

            <details style="margin-top:10px;">
              <summary style="cursor:pointer;">Shiko vlerësimet</summary>
              ${vlerList}
            </details>

            <a class="btn small-btn" href="/event-detaje-host.html?id=${ev.id}" style="margin-top:10px;display:inline-block;">
              View event
            </a>
          </div>
        `;
      }).join("") || `<div class="glass-card"><h3>No events created yet</h3></div>`;

  } catch (err) {
    console.error(err);
    hostEvents.innerHTML = `<div class="glass-card"><h3>Error</h3><p>${err.message}</p></div>`;
  }
})();
