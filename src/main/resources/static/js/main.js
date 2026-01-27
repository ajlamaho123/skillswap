const container = document.getElementById("eventsContainer");

async function loadEvents() {
  try {
    const res = await fetch("/api/evente");
    const events = await res.json();

    if (!Array.isArray(events) || events.length === 0) {
      container.innerHTML = `
        <div class="glass-card">
          <h3>No events available yet</h3>
          <p>New SkillSwap events are regularly added by hosts.</p>
        </div>
      `;
      return;
    }

    container.innerHTML = "";

    events.slice(0, 4).forEach(event => {
      container.innerHTML += `
        <div class="glass-card" style="cursor:pointer"
             onclick="location.href='event-detaje.html?id=${event.id}'">
          <h3>${event.titulli}</h3>
          <p>
            <strong>Skill:</strong> ${event.kategoriEmri}<br>
            <strong>Date:</strong> ${event.data} ${event.ora}
          </p>
          <p>
            👥 Limit: ${event.kufiriPjesemarresve}<br>
            🤝 Host: ${event.hostEmri} ${event.hostMbiemri}
          </p>
        </div>
      `;
    });

  } catch (err) {
    console.error(err);
    container.innerHTML = `
      <div class="glass-card">
        <h3>Error loading events</h3>
        <p>Backend must be running on http://localhost:8080</p>
      </div>
    `;
  }
}

loadEvents();
