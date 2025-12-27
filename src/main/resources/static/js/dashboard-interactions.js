// Data for the 3 main cards
const mainCardData = {
    "irrigation": {
        title: "Smart Irrigation System",
        videoUrl: "https://www.youtube.com/embed/5pMkRy7s_vY", // Example IoT video
        suggestions: [
            "Use Arduino Uno or ESP8266 for connectivity.",
            "Calibrate your soil moisture sensor before deployment.",
            "Consider adding a solar panel for a self-sustaining system.",
            "Implement a mobile app dashboard using Blynk or Firebase."
        ]
    },
    "attendance": {
        title: "AI Attendance System",
        videoUrl: "https://www.youtube.com/embed/sz25xxF_AVE", // Example OpenCV video
        suggestions: [
            "Use OpenCV and Python for face detection.",
            "Train your model with at least 50 images per person.",
            "Ensure good lighting conditions for better accuracy.",
            "Store attendance logs in a SQLite or MySQL database."
        ]
    },
    "solar": {
        title: "Solar Energy Monitor",
        videoUrl: "https://www.youtube.com/embed/3eRjRz1aMvM", // Example Solar Project video
        suggestions: [
            "Measure voltage and current using ACS712 sensors.",
            "Calculate power efficiency in real-time.",
            "Display data on an LCD 16x2 or OLED screen.",
            "Log historical data to an SD card for analysis."
        ]
    }
};

// Data generation for the "See More" section (500+ items)
const generateVideoData = (category) => {
    const videos = [];
    const isTech = category === 'technical';

    const techTopics = [
        "Java Spring Boot Microservices", "React Native Crash Course", "Python for Data Science",
        "Machine Learning Basics", "Docker & Kubernetes", "AWS Cloud Essentials",
        "Cybersecurity 101", "Blockchain Development", "IoT Home Automation",
        "Angular vs Vue", "Rust Programming", "GoLang Concurrency",
        "System Design Interview", "Database Indexing", "GraphQL API"
    ];

    const nonTechTopics = [
        "Project Management Professional", "Agile & Scrum Master", "UI/UX Design Principles",
        "Digital Marketing Strategy", "Startup Funding Series A", "Public Speaking Mastery",
        "Team Leadership Skills", "Product Roadmap Planning", "SEO Optimization",
        "Content Marketing 101", "Financial Modeling", "Negotiation Tactics",
        "Design Thinking Workshop", "Remote Team Management", "Brand Identity Design"
    ];

    const topics = isTech ? techTopics : nonTechTopics;
    const baseImg = isTech ? "https://img.youtube.com/vi/PkZNo7MFNFg/hqdefault.jpg" : "https://img.youtube.com/vi/zR5QTA_G8ho/hqdefault.jpg"; // Generic placeholders

    // Generate 500+ items
    for (let i = 1; i <= 510; i++) {
        const topic = topics[i % topics.length];
        videos.push({
            id: i,
            title: `${topic} - Part ${Math.ceil(i / topics.length)}`,
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

    // Stop video if closing main modal
    if (id === 'mainModal') {
        document.getElementById('mainModalVideo').src = '';
    }
}

// Function to select category and show grid
function selectCategory(category) {
    // Hide selection modal, show grid modal
    document.getElementById('selectionModal').style.display = 'none';
    const gridModal = document.getElementById('gridModal');
    gridModal.style.display = 'flex';

    const gridTitle = document.getElementById('gridModalTitle');
    gridTitle.textContent = category === 'technical' ? "Technical Projects" : "Non-Technical Projects";

    const container = document.getElementById('videoGrid');
    container.innerHTML = ''; // Clear previous

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
        // Stop video if main modal
        if (event.target.id === 'mainModal') {
            document.getElementById('mainModalVideo').src = '';
        }
    }
}
