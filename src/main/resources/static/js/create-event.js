const form = document.getElementById("createEventForm");
const msg = document.getElementById("msg");
const params = new URLSearchParams(window.location.search);
const editId = params.get("editId");
const isEditMode = !!editId; // 🔵 EDIT MODE

function getLoggedUser() {
  return JSON.parse(localStorage.getItem("loggedUser"));
}

/* 🔵 EDIT MODE: mbush formen me eventin ekzistues */
async function loadEventForEdit(id) {
  try {
    const res = await fetch(`/api/evente/${id}`);
    const event = await res.json();

    if (!res.ok) {
      alert(event.gabim || "Eventi nuk u gjet");
      return;
    }

    document.getElementById("kategoriId").value = event.kategoriId;
    document.getElementById("titulli").value = event.titulli;
    document.getElementById("pershkrimi").value = event.pershkrimi;
    document.getElementById("data").value = event.data;
    document.getElementById("ora").value = event.ora;
    document.getElementById("vendndodhja").value = event.vendndodhja;
    document.getElementById("kufiriPjesemarresve").value = event.kufiriPjesemarresve;
    if (document.getElementById("fotoUrl")) {
      document.getElementById("fotoUrl").value = event.fotoUrl || "";
    }

    // ndrysho tekstin e butonit
    const submitBtn = form.querySelector("button[type='submit']");
    if (submitBtn) submitBtn.textContent = "Update Event";

  } catch (err) {
    console.error(err);
    alert("Gabim ne ngarkimin e eventit per edit");
  }
}

/* 🔵 EDIT MODE: thirr ngarkimin kur hapet faqja */
if (isEditMode) {
  loadEventForEdit(editId);
}

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const u = getLoggedUser();
  if (!u) {
    alert("Duhet login!");
    location.href = "/login.html";
    return;
  }

  const payload = {
    hostId: u.id,
    kategoriId: Number(document.getElementById("kategoriId").value),
    titulli: document.getElementById("titulli").value.trim(),
    pershkrimi: document.getElementById("pershkrimi").value.trim(),
    data: document.getElementById("data").value,
    ora: document.getElementById("ora").value,
    vendndodhja: document.getElementById("vendndodhja").value.trim(),
    kufiriPjesemarresve: Number(document.getElementById("kufiriPjesemarresve").value),
    fotoUrl: document.getElementById("fotoUrl")?.value.trim() || ""
  };

  // kontroll minimal (rregullova typo: playload -> payload)
  if (
    !payload.titulli ||
    !payload.pershkrimi ||
    !payload.vendndodhja ||
    !payload.data ||
    !payload.ora ||
    !payload.kategoriId
  ) {
    alert("Ploteso te gjitha fushat!");
    return;
  }

  try {
    const url = isEditMode ? `/api/evente/${editId}` : `/api/evente`; // 🔵
    const method = isEditMode ? "PUT" : "POST";                      // 🔵

    const res = await fetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });

    const data = await res.json();

    if (!res.ok) {
      msg.innerHTML = `<span style="color:red;">❌ ${data.gabim || data.message || "Operacioni deshtoi"}</span>`;
      return;
    }

    msg.innerHTML = isEditMode
      ? `<span style="color:green;">✅ Eventi u perditesua me sukses!</span>`
      : `<span style="color:green;">✅ Eventi u krijua me sukses!</span>`;

    form.reset();

    setTimeout(() => {
      window.location.href = "/dashboard-host.html";
    }, 700);

  } catch (err) {
    console.error(err);
    msg.innerHTML = `<span style="color:red;">❌ Gabim lidhjeje me serverin</span>`;
  }
});
