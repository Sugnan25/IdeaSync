// Main featured project (Hero)
const featuredProject = {
    title: "EcoSmart City Grid",
    description: "A city-wide network of IoT sensors optimizing energy consumption and traffic flow using real-time AI analytics.",
    tags: "AI • IoT • Urban Planning"
};

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

    // Use featured data if type is 'featured'
    const projectData = (type === 'featured') ? featuredProject : data;

    // For featured, we might mock some extra details since they aren't in the object above
    if(type === 'featured') {
        projectData.techStack = ["Python", "AWS", "LoRaWAN"];
    }

    const modal = document.getElementById('projectModal');
    const title = document.getElementById('projectModalTitle');
    const desc = document.getElementById('projectModalDesc');
    const techContainer = document.getElementById('projectModalTech');
    const joinBtn = document.getElementById('modalJoinBtn');

    title.textContent = projectData.title;
    desc.textContent = projectData.description;

    // Clear and populate tech stack
    techContainer.innerHTML = '';
    projectData.techStack.forEach(tech => {
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

// Filter Logic for Main Page
function filterMain(category, el) {
    // Visual toggle
    document.querySelectorAll('.filter-chip').forEach(c => c.classList.remove('active'));
    el.classList.add('active');

    const grid = document.querySelector('.suggestions-grid');
    const cards = grid.querySelectorAll('.suggestion-card');

    if (category === 'all') {
        cards.forEach(card => card.style.display = 'flex');
    } else {
        cards.forEach(card => {
            // Check if card has the class corresponding to category
            // e.g. card-image ai, card-image fintech
            const imgDiv = card.querySelector('.card-image');
            if (imgDiv.classList.contains(category)) {
                card.style.display = 'flex';
            } else {
                card.style.display = 'none';
            }
        });
    }
}

window.onclick = function(event) {
    if (event.target.classList.contains('modal-overlay')) {
        event.target.style.display = "none";
    }
}
