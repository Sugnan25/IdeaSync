// Data for the 3 main project cards
const mainProjectData = {
    "ai": {
        title: "AI Health Assistant",
        description: "A machine learning powered application that provides personalized health tips and symptom analysis based on user input.",
        techStack: ["Python", "TensorFlow", "React", "FastAPI"],
        teamSize: "3/5 Members",
        difficulty: "Advanced"
    },
    "fintech": {
        title: "DeFi Crypto Exchange",
        description: "Decentralized finance platform allowing users to swap tokens and provide liquidity with low fees.",
        techStack: ["Solidity", "Web3.js", "Next.js", "Ethereum"],
        teamSize: "2/4 Members",
        difficulty: "Intermediate"
    },
    "sustainability": {
        title: "Urban Vertical Farming",
        description: "IoT based system to monitor and control hydroponic farms in urban environments for sustainable food production.",
        techStack: ["C++", "Arduino", "MQTT", "Vue.js"],
        teamSize: "4/6 Members",
        difficulty: "Intermediate"
    }
};

let currentModalType = null;

// Function to open the Main Project Modal
function openProjectModal(type) {
    currentModalType = type;
    const data = mainProjectData[type];
    if (!data) return;

    const modal = document.getElementById('projectModal');
    const title = document.getElementById('projectModalTitle');
    const desc = document.getElementById('projectModalDesc');
    const techContainer = document.getElementById('projectModalTech');
    const joinBtn = document.getElementById('modalJoinBtn');

    title.textContent = data.title;
    desc.textContent = data.description;

    // Clear and populate tech stack
    techContainer.innerHTML = '';
    data.techStack.forEach(tech => {
        const badge = document.createElement('span');
        badge.className = 'tech-badge';
        badge.textContent = tech;
        techContainer.appendChild(badge);
    });

    // Reset button state
    joinBtn.textContent = "Request to Join";
    joinBtn.classList.remove('requested');
    joinBtn.disabled = false;

    modal.style.display = 'flex';
}

// Handle Join Action in Modal
function requestJoin() {
    const joinBtn = document.getElementById('modalJoinBtn');
    joinBtn.textContent = "Request Sent ✓";
    joinBtn.classList.add('requested');
    joinBtn.disabled = true;

    // In a real app, this would send an API request
    console.log(`Join requested for ${currentModalType}`);
}

// Function to open the Selection Modal (See More)
function openSelectionModal() {
    document.getElementById('selectionModal').style.display = 'flex';
}

// Function to close any modal
function closeModal(id) {
    const modal = document.getElementById(id);
    modal.style.display = 'none';
}

// Function to select category and show grid
function selectCategory(category) {
    document.getElementById('selectionModal').style.display = 'none';
    const gridModal = document.getElementById('gridModal');
    gridModal.style.display = 'flex';

    const gridTitle = document.getElementById('gridModalTitle');
    gridTitle.textContent = category === 'software' ? "Software Projects" : "Hardware/IoT Projects";

    const container = document.getElementById('projectGrid');
    container.innerHTML = ''; // Clear previous

    const projects = generateProjectData(category);

    projects.forEach(project => {
        const card = document.createElement('div');
        card.className = 'project-card';
        // Random gradient for thumb
        const hue = Math.floor(Math.random() * 360);
        card.innerHTML = `
            <div class="project-thumb" style="background: hsl(${hue}, 70%, 80%)"></div>
            <div class="project-info">
                <h5>${project.title}</h5>
                <p style="font-size: 0.8rem; color: #666; margin-bottom: 0.5rem;">${project.tags}</p>
                <span class="join-link" onclick="this.textContent='Requested ✓'; this.style.color='#10b981'">Request to Join</span>
            </div>
        `;
        container.appendChild(card);
    });
}

// Generate 500+ mock projects
const generateProjectData = (category) => {
    const projects = [];
    const isSoftware = category === 'software';

    const softwarePrefixes = ["Smart", "Crypto", "AI", "Cloud", "Social", "Edu", "Health", "Fin"];
    const softwareSuffixes = ["App", "Platform", "Bot", "Hub", "Network", "System", "Tracker", "Wallet"];

    const hardwarePrefixes = ["Auto", "Solar", "Drone", "Robot", "Home", "Smart", "Eco", "Wearable"];
    const hardwareSuffixes = ["Controller", "Monitor", "Device", "Sensor", "System", "Rover", "Grid", "Glass"];

    const prefixes = isSoftware ? softwarePrefixes : hardwarePrefixes;
    const suffixes = isSoftware ? softwareSuffixes : hardwareSuffixes;

    for (let i = 1; i <= 510; i++) {
        const pre = prefixes[Math.floor(Math.random() * prefixes.length)];
        const suf = suffixes[Math.floor(Math.random() * suffixes.length)];
        const title = `${pre}${suf} ${Math.floor(Math.random() * 1000)}`;

        projects.push({
            id: i,
            title: title,
            tags: isSoftware ? "React • Node • MongoDB" : "Arduino • C++ • IoT"
        });
    }
    return projects;
};

// Close modals when clicking outside
window.onclick = function(event) {
    if (event.target.classList.contains('modal-overlay')) {
        event.target.style.display = "none";
    }
}
