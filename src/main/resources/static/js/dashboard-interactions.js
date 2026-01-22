// Data for the 3 main cards (Creator's Toolkit)
const mainCardData = {
    "mvp": {
        title: "From Idea to MVP",
        videoUrl: "https://www.youtube.com/embed/YwEEV0wHnaA",
        suggestions: [
            "Define your core value proposition before coding.",
            "Focus on a single key feature for the first release.",
            "Use rapid prototyping tools like Figma or wireframes.",
            "Gather user feedback immediately after launch."
        ]
    },
    "collaboration": {
        title: "Mastering Team Collaboration",
        videoUrl: "https://www.youtube.com/embed/jhtbhSpV5YA",
        suggestions: [
            "Establish a clear Git workflow (e.g., Feature Branching).",
            "Use Pull Requests to review code before merging.",
            "Communicate daily updates via Slack or Stand-ups.",
            "Document your code and API endpoints for teammates."
        ]
    },
    "pitch": {
        title: "The Art of the Pitch",
        videoUrl: "https://www.youtube.com/embed/7u0cKqRPYhY",
        suggestions: [
            "Start with the problem statement, then your solution.",
            "Keep your demo concise and focused on the 'Wow' factor.",
            "Highlight the technologies used and why you chose them.",
            "Practice your delivery to stay within time limits."
        ]
    }
};

// Idea of the Day Data
const ideas = [
    "A decentralized voting system using Blockchain for local communities.",
    "An AI-powered recipe generator based on ingredients in your fridge.",
    "A mental health companion app that uses sentiment analysis.",
    "A smart irrigation system for urban balcony gardens.",
    "A peer-to-peer skill sharing platform for university students.",
    "An AR app to visualize furniture in your room before buying.",
    "A subscription management tool that cancels unused services automatically."
];

function getIdeaOfTheDay() {
    const day = new Date().getDay();
    return ideas[day % ideas.length];
}

// Data generation for the "See More" section (Infinite Grid)
const generateVideoData = (category) => {
    const videos = [];

    // Topics Map
    const topicsMap = {
        'technical': [
            "Java Spring Boot Microservices", "React Native Crash Course", "Python for Data Science",
            "Machine Learning Basics", "Docker & Kubernetes", "AWS Cloud Essentials",
            "Cybersecurity 101", "Blockchain Development", "IoT Home Automation"
        ],
        'non-technical': [
            "Project Management Professional", "Agile & Scrum Master", "UI/UX Design Principles",
            "Digital Marketing Strategy", "Startup Funding Series A", "Public Speaking Mastery",
            "Team Leadership Skills"
        ],
        'business': [
            "Business Model Canvas", "Financial Forecasting", "Market Research 101",
            "Growth Hacking Strategies", "Legal Basics for Startups", "Pitch Deck Masterclass"
        ],
        'creative': [
            "Graphic Design Trends", "Video Editing with Premiere", "3D Modeling in Blender",
            "Music Production for Games", "Creative Writing Workshop", "Color Theory in Design"
        ],
        'iot': [
            "Arduino Basics", "Raspberry Pi Projects", "ESP8266 WiFi Module",
            "Home Assistant Setup", "LoRaWAN Networks", "MQTT Protocol Guide"
        ]
    };

    const topics = topicsMap[category] || topicsMap['technical'];

    // Dynamic Thumbnails based on category (Generic)
    let baseImg = "https://img.youtube.com/vi/PkZNo7MFNFg/hqdefault.jpg";
    if (category === 'business') baseImg = "https://img.youtube.com/vi/sJPneE9s7kI/hqdefault.jpg";
    if (category === 'creative') baseImg = "https://img.youtube.com/vi/7T7r_oSp0SE/hqdefault.jpg";

    // Generate 100 items (reduced from 500 for perf, still plenty)
    for (let i = 1; i <= 100; i++) {
        const topic = topics[i % topics.length];
        videos.push({
            id: i,
            title: `${topic} - Volume ${Math.ceil(i / topics.length)}`,
            thumbnail: baseImg,
            url: "https://www.youtube.com/results?search_query=" + encodeURIComponent(topic)
        });
    }
    return videos;
};

// Function to open the Main Card Modal
function openMainModal(type) {
    const data = mainCardData[type];
    if (!data) return;

    const modal = document.getElementById('mainModal');
    const title = document.getElementById('mainModalTitle');
    const iframe = document.getElementById('mainModalVideo');
    const suggestionsList = document.getElementById('mainModalSuggestions');

    title.textContent = data.title;
    iframe.src = data.videoUrl;

    suggestionsList.innerHTML = '';
    data.suggestions.forEach(text => {
        const li = document.createElement('li');
        li.textContent = text;
        suggestionsList.appendChild(li);
    });

    modal.style.display = 'flex';
}

// Function to open the Selection Modal (See More)
function openSelectionModal() {
    document.getElementById('selectionModal').style.display = 'flex';
}

// Function to close any modal
function closeModal(id) {
    const modal = document.getElementById(id);
    modal.style.display = 'none';

    if (id === 'mainModal') {
        document.getElementById('mainModalVideo').src = '';
    }
}

// Function to select category and show grid
function selectCategory(category) {
    document.getElementById('selectionModal').style.display = 'none';
    const gridModal = document.getElementById('gridModal');
    gridModal.style.display = 'flex';

    const gridTitle = document.getElementById('gridModalTitle');
    // Capitalize first letter
    gridTitle.textContent = category.charAt(0).toUpperCase() + category.slice(1) + " Resources";

    const container = document.getElementById('videoGrid');
    container.innerHTML = '';

    const videos = generateVideoData(category);

    videos.forEach(video => {
        const card = document.createElement('div');
        card.className = 'video-card';
        card.innerHTML = `
            <div class="video-thumb" style="background-image: url('${video.thumbnail}')">
                <div class="play-icon">▶</div>
            </div>
            <div class="video-info">
                <h5>${video.title}</h5>
                <a href="${video.url}" target="_blank" class="watch-link">Watch on YouTube</a>
            </div>
        `;
        container.appendChild(card);
    });
}

// Close modals when clicking outside
window.onclick = function(event) {
    if (event.target.classList.contains('modal-overlay')) {
        event.target.style.display = "none";
        if (event.target.id === 'mainModal') {
            document.getElementById('mainModalVideo').src = '';
        }
    }
}

// Init Idea of the Day
document.addEventListener('DOMContentLoaded', () => {
    const ideaBox = document.getElementById('ideaText');
    if (ideaBox) {
        ideaBox.textContent = getIdeaOfTheDay();
    }
});
