// Merr eventId nga localStorage
const eventId = localStorage.getItem("selectedEventId");
const container = document.getElementById("event-details");

// Gjej event
const event = events.find(e => e.id == eventId);

if (!event) {
  container.innerHTML = "<p>Event not found.</p>";
} else {
  container.innerHTML = `
    <h2>${event.title}</h2>
    <p><strong>Category:</strong> ${event.category}</p>
    <p><strong>Host:</strong> ${event.host}</p>
    <p><strong>Available spots:</strong> <span id="spots">${event.spots}</span></p>
    <button class="btn" id="joinBtn">Join Event</button>
    <div id="feedbackSection" style="margin-top:20px;"></div>
  `;

  const joinBtn = document.getElementById("joinBtn");
  let joinedEvents = JSON.parse(localStorage.getItem("joinedEvents")) || [];

  if (joinedEvents.includes(event.id)) {
    joinBtn.innerText = "Already Joined";
    joinBtn.disabled = true;
    showFeedback();
  } else {
    joinBtn.addEventListener("click", () => {
      joinedEvents.push(event.id);
      localStorage.setItem("joinedEvents", JSON.stringify(joinedEvents));
      joinBtn.innerText = "Already Joined";
      joinBtn.disabled = true;
      showFeedback();
    });
  }
}

// Funksioni për feedback
function showFeedback() {
  const feedbackSection = document.getElementById("feedbackSection");
  feedbackSection.innerHTML = `
    <h3>Leave your feedback:</h3>
    <textarea id="feedbackText" placeholder="Write a comment..." rows="3" style="width:100%; padding:8px; border-radius:8px;"></textarea>
    <button class="btn" style="margin-top:10px;" onclick="submitFeedback()">Submit</button>
    <div id="feedbackList" style="margin-top:15px;"></div>
  `;
  displayFeedback();
}

function submitFeedback() {
  const feedbackText = document.getElementById("feedbackText").value.trim();
  if (!feedbackText) return;

  let feedbacks = JSON.parse(localStorage.getItem(`feedback-${event.id}`)) || [];
  feedbacks.push(feedbackText);
  localStorage.setItem(`feedback-${event.id}`, JSON.stringify(feedbacks));

  document.getElementById("feedbackText").value = "";
  displayFeedback();
}

function displayFeedback() {
  const feedbackList = document.getElementById("feedbackList");
  let feedbacks = JSON.parse(localStorage.getItem(`feedback-${event.id}`)) || [];

  if(feedbacks.length === 0){
    feedbackList.innerHTML = "<p>No feedback yet.</p>";
    return;
  }

  feedbackList.innerHTML = feedbacks.map(f => `<p>💬 ${f}</p>`).join("");
}
