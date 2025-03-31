const toggleBtn = document.getElementById("toggle-btn");
const navBar = document.getElementById("nav-bar");
const navLinks = document.querySelectorAll("#nav-bar a");

toggleBtn.addEventListener("click", (e) => {
  navBar.classList.toggle("active");
  e.stopPropagation();
});

document.addEventListener("click", (e) => {
  if (
    navBar.classList.contains("active") &&
    e.target !== navBar &&
    e.target !== toggleBtn
  ) {
    navBar.classList.remove("active");
  }
});

navLinks.forEach((link) => {
  link.addEventListener("click", () => {
    navBar.classList.remove("active");
  });
});

document.addEventListener("DOMContentLoaded", function () {
  const navLinks = document.querySelectorAll("nav ul li a");

  navLinks.forEach((link) => {
    link.addEventListener("click", function (e) {
      e.preventDefault();
      const targetId = this.getAttribute("href").substring(1);
      const targetSection = document.getElementById(targetId);

      if (targetSection) {
        window.scrollTo({
          top: targetSection.offsetTop - 70, // Adjusted for fixed navbar
          behavior: "smooth"
        });

        // Remove active class from all links
        navLinks.forEach((nav) => nav.classList.remove("active"));
        this.classList.add("active");
      }
    });
  });

  // Typing effect with blinking cursor
  const typingElement = document.querySelector(".hero-content h1 span");
  const words = ["Blockchain Developer", "Web Developer", "Market researcher"];
  let wordIndex = 0;
  let charIndex = 0;
  let isDeleting = false;

  function typeEffect() {
    const currentWord = words[wordIndex];
    const currentText = isDeleting
      ? currentWord.substring(0, charIndex--)
      : currentWord.substring(0, charIndex++);

    typingElement.innerHTML = currentText + '<span class="cursor">|</span>';

    if (!isDeleting && charIndex === currentWord.length) {
      setTimeout(() => (isDeleting = true), 1000);
    } else if (isDeleting && charIndex === 0) {
      isDeleting = false;
      wordIndex = (wordIndex + 1) % words.length;
    }

    setTimeout(typeEffect, isDeleting ? 100 : 200);
  }
  typeEffect();

  // Hover and click effects for buttons
  const buttons = document.querySelectorAll(".project-btn, .show-more-btn");

  buttons.forEach((btn) => {
    btn.addEventListener("mouseenter", () => {
      btn.style.transform = "scale(1.1)";
      btn.style.transition = "transform 0.3s ease";
    });

    btn.addEventListener("mouseleave", () => {
      btn.style.transform = "scale(1)";
    });

    btn.addEventListener("click", () => {
      btn.style.backgroundColor = "#ffd700";
      setTimeout(() => {
        btn.style.backgroundColor = "#00d4ff";
      }, 300);
    });
  });

  // Contact Form Validation with error highlighting
  const contactForm = document.querySelector("form");
  const nameInput = document.querySelector("input[name='name']");
  const emailInput = document.querySelector("input[name='email']");
  const messageInput = document.querySelector("textarea[name='message']");

  contactForm.addEventListener("submit", function (e) {
    e.preventDefault();

    let isValid = true;

    if (nameInput.value.trim() === "") {
      showError(nameInput, "Name is required.");
      isValid = false;
    } else {
      clearError(nameInput);
    }

    if (emailInput.value.trim() === "" || !validateEmail(emailInput.value)) {
      showError(emailInput, "Enter a valid email.");
      isValid = false;
    } else {
      clearError(emailInput);
    }

    if (messageInput.value.trim() === "") {
      showError(messageInput, "Message cannot be empty.");
      isValid = false;
    } else {
      clearError(messageInput);
    }

    if (isValid) {
      alert("Message sent successfully!");
      contactForm.reset();
    }
  });

  function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
  }

  function showError(input, message) {
    input.style.border = "2px solid red";
    input.nextElementSibling.textContent = message;
    input.nextElementSibling.style.color = "red";
  }

  function clearError(input) {
    input.style.border = "2px solid #00d4ff";
    input.nextElementSibling.textContent = "";
  }

  // Dynamic Project Reveal Animation on Scroll
  const projectCards = document.querySelectorAll(".project");

  function revealProjects() {
    projectCards.forEach((project) => {
      const projectPosition = project.getBoundingClientRect().top;
      const screenPosition = window.innerHeight / 1.2;

      if (projectPosition < screenPosition) {
        project.classList.add("visible");
      }
    });
  }

  window.addEventListener("scroll", revealProjects);
  revealProjects(); // Initial check

  // Dark mode toggle with smooth transition
  const darkModeToggle = document.getElementById("dark-mode-toggle");

  if (darkModeToggle) {
    darkModeToggle.addEventListener("click", function () {
      document.body.classList.toggle("dark-mode");
      document.body.style.transition = "background 0.5s ease, color 0.5s ease";

      if (document.body.classList.contains("dark-mode")) {
        localStorage.setItem("darkMode", "enabled");
      } else {
        localStorage.removeItem("darkMode");
      }
    });

    if (localStorage.getItem("darkMode") === "enabled") {
      document.body.classList.add("dark-mode");
    }
  }
});
document.getElementById("toggle-btn").addEventListener("click", function () {
  document.querySelector("nav ul").classList.toggle("active");
});
