# 🐾 pet-clinic.lk

**A question about your pet, answered by a real clinic.**

pet-clinic Sri Lanka is a web platform that connects pet owners with **registered, admin-verified veterinary clinics**. Pet owners post questions about symptoms, vaccines, diet or behaviour, and only approved clinics can reply, so every piece of advice comes from an accountable practice.

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)


![Landing page](docs/screenshots/landing_page.png)

---

## 📑 Table of Contents

- [Overview](#-overview)
- [How It Works](#-how-it-works)
- [User Roles & Features](#-user-roles--features)
- [Screenshots](#-screenshots)
- [Tech Stack](#-tech-stack)
- [Getting Started](#-getting-started)
- [Project Structure](#-project-structure)
- [Roadmap](#-roadmap)
- [Author](#-author)

---

## 🌟 Overview

Many pet owners in Sri Lanka don't know whether a symptom is serious or who to trust for advice. pet-clinic puts pet owners and verified clinics on **one board**:

- Pet owners ask questions with details about their pet.
- Registered clinics reply with professional advice.
- Administrators verify every clinic before it can log in, keeping the platform trustworthy.

## 🔄 How It Works

| Step | What happens |
|------|--------------|
| **1. Pet owners ask** | Post a question about symptoms, vaccination schedules, diet or behaviour, with details about the pet. |
| **2. Registered clinics answer** | Only clinics an administrator has verified and approved can log in and reply. |
| **3. Your pet gets the right care** | Read the reply, follow up with more questions, and download a summary to bring to your next visit. |

### Why the approval step?

- Pet owners know every answer comes from a verified clinic.
- Admins can update or remove a clinic's access at any time.
- Advice on the board stays accountable and traceable.

---

## 👥 User Roles & Features

### 🐶 Pet Owner
- Create an account (name, contact number, email, optional address and city)
- Log in and manage a personal profile (email is fixed, other details editable)
- **Ask the clinics a question** with:
  - Question title and topic
  - Pet name (optional), type of pet and age (optional)
  - A detailed description of what's happening
- Personal dashboard showing questions asked, questions waiting for a reply and questions answered
- Browse the **Community Q&A** and filter by topic
- Browse all registered, approved clinics
- Download a **summary (.txt)** of a question and its replies to take to the vet
- Emergency notice on the question form reminding owners to call their nearest clinic directly

### 🏥 Veterinary Clinic
- Submit a **registration request** (clinic name, registration no., email, contact, address, city, services/opening hours)
- Cannot log in until an administrator approves the request
- Clinic dashboard with *Questions waiting for a reply* and *Replies you've posted*
- Browse all pet owner questions in the Community Q&A and filter by topic
- Open a question to see pet details, topic, owner city and posted time
- **Reply as a clinic** to advise owners, flagging urgent cases
- Edit the clinic profile

### 🛡️ Administrator
- Admin dashboard with live stats: clinic requests waiting, approved clinics, pet owners, questions asked
- Review **clinic registration requests**
- Manage clinics: **approve, revoke access, edit or delete**
- Manage pet owners: **edit or delete** accounts
- View the public clinic directory

### 🌐 Visitors (not logged in)
- View the landing page and how the platform works
- Browse the directory of approved clinics
- Sign up as a pet owner or request clinic registration

---

## 📸 Screenshots

### Public pages

| Landing page | Login |
|:---:|:---:|
| ![Landing page](docs/screenshots/landing_page.png) | ![Login](docs/screenshots/login.png) |

| Find a clinic (visitor) | Logout confirmation |
|:---:|:---:|
| ![Find a clinic](docs/screenshots/find_a_clinic.png) | ![Logout](docs/screenshots/logout.png) |

### 🐶 Pet owner

| Create account | Dashboard |
|:---:|:---:|
| ![Create account](docs/screenshots/pet_owner_acc_creat.png) | ![Pet owner dashboard](docs/screenshots/pet_owner_acc_dashboard.png) |

| Ask a question | Edit profile |
|:---:|:---:|
| ![Ask a question](docs/screenshots/pet_owner_acc_ask_questions.png) | ![Edit profile](docs/screenshots/pet_owner_acc_edit_profile.png) |

| Community Q&A | Browse clinics |
|:---:|:---:|
| ![Community Q&A](docs/screenshots/pet_owner_acc_ask_Q_A.png) | ![Find all clinics](docs/screenshots/find_all_clinics.png) |

### 🏥 Clinic

| Request registration | Clinic dashboard |
|:---:|:---:|
| ![Request clinic registration](docs/screenshots/request_clinic_registration.png) | ![Clinic dashboard](docs/screenshots/clinic_dashboard.png) |

| Community Q&A | Question detail & reply |
|:---:|:---:|
| ![Clinic Q&A](docs/screenshots/Q_and_A_section.png) | ![Submitted question](docs/screenshots/submitted_questions.png) |

| Browse clinics |
|:---:|
| ![Clinic side find clinics](docs/screenshots/find_clinic_on_pet_clinc_side.png) |

### 🛡️ Administrator

| Admin dashboard | Manage clinics |
|:---:|:---:|
| ![Admin dashboard](docs/screenshots/admin_dashboard.png) | ![All clinics](docs/screenshots/all_clinics.png) |

| Edit clinic | Manage pet owners |
|:---:|:---:|
| ![Edit clinic](docs/screenshots/edit_clinic.png) | ![Pet owners](docs/screenshots/all_pet_owers_operations.png) |

| Clinic directory (admin view) |
|:---:|
| ![Admin find clinics](docs/screenshots/find_clinics_on_admin_side.png) |

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java, Spring Boot |
| Database | MySQL |
| Frontend | HTML, CSS, Bootstrap |
| Build tool | Maven / Gradle *(update to match your project)* |

---

## 🚀 Getting Started

### Prerequisites

- Java 17 or later *(adjust to your version)*
- MySQL 8+
- Maven *(or Gradle)*
- Git

### 1. Clone the repository

```bash
git clone https://github.com/<your-username>/pet-clinic.git
cd pet-clinic
```

### 2. Create the database

```sql
CREATE DATABASE pet-clinic;
```

### 3. Configure the application

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pet-clinic
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 4. Run the application

```bash
./mvnw spring-boot:run
```

Then open **http://localhost:8080** in your browser.

### Default accounts

| Role | Email | Password |
|------|-------|----------|
| Admin | `admin@vetconnect.lk` | *(set in your seed data / config)* |

> Pet owners can sign up from the site. Clinics must submit a registration request and be **approved by the admin** before they can log in.

---

## 📂 Project Structure

```text
pet-clinic/
├── src/
│   ├── main/
│   │   ├── java/             # Controllers, services, repositories, entities
│   │   └── resources/
│   │       ├── templates/    # Pages
│   │       ├── static/       # CSS, JS, images
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

*(this basic package layout.)*

---

## 🗺️ Roadmap

- [ ] Email notifications when a clinic is approved or a question is answered
- [ ] Mark a reply as "accepted answer"
- [ ] Image uploads for pet symptoms
- [ ] Search clinics by city
- [ ] Sinhala and Tamil language support

---

## 👤 Author

- **Name :Isuru Udayanga**<br>
- **ID : 27890**<br>
- GitHub: [Git Link](https://github.com/Isurunsbm1/pet-clinic.git)

---

<p align="center">Made with 💚 for pets and the people who love them in Sri Lanka.</p>