# 📱 DARSHAN — Temple Crowd Management System

> **DARSHAN** — *Devotee Assistance & Real-time Security Handling And Navigation*

A comprehensive temple crowd management system designed to enhance the spiritual experience of pilgrims while ensuring safety and security through intelligent crowd monitoring and real-time communication.

---

## 📋 Table of Contents

- [About DARSHAN](#-about-darshan)
- [Features](#-features)
  - [Pilgrim Features](#-pilgrim-features)
  - [Security Features](#️-security-features)
- [Tech Stack](#️-tech-stack)
  - [Frontend — Android](#frontend--android)
  - [Backend — API](#backend--api)
  - [Infrastructure](#infrastructure)
- [Architecture](#️-architecture)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
- [Future Scope](#-future-scope)
- [Contributors](#-contributors)
- [License](#-license)

---

## 🙏 About DARSHAN

**DARSHAN** *(Devotee Assistance & Real-time Security Handling And Navigation)* is a comprehensive temple crowd management system designed to enhance the spiritual experience of pilgrims while ensuring safety and security through intelligent crowd monitoring and real-time communication.

The system consists of:

- 📱 **Android Application** — Pilgrim & Security roles
- 🖥️ **Controller Website** — Command Center
- 🔗 **Backend API** — FastAPI
- 🔔 **Real-time Notification System** — Firebase FCM

---

## ✨ Features

### 👤 Pilgrim Features

| Feature | Description |
|---|---|
| **Multi-Temple Support** | View real-time data for Somnath, Dwarka, and Ambaji temples |
| **Live Crowd Status** | Real-time crowd levels with AI predictions |
| **Darshan Booking** | Book VIP/Regular darshan slots |
| **Smart Queue** | Join queue, view wait time, and receive notifications |
| **Live Temple Map** | Interactive map with crowd heat zones |
| **AI Predictions** | Best time to visit based on historical data |
| **Weather Updates** | Real-time weather information |
| **Pilgrim Guide** | Temple information, Aarti timings, FAQs, and contacts |
| **Emergency SOS** | One-tap emergency alert with location |
| **Multi-Language** | English, Hindi, and Gujarati support |

### 🛡️ Security Features

| Feature | Description |
|---|---|
| **Security Dashboard** | Role-specific dashboard for security personnel |
| **Real-time Alerts** | Receive alerts from the Controller |
| **Alert Acknowledgment** | Acknowledge and respond to alerts |
| **Smart Duty Status** | On Duty / On Break / Off Duty |
| **Incident Reporting** | Report Security, Medical, Crowd, and Emergency incidents |
| **Emergency Trigger** | One-tap emergency alert to the Controller |
| **Contact Controller** | Send messages or call the Controller |
| **Duty Management** | View shift timings and assigned zones |

---

## 🛠️ Tech Stack

### Frontend (Android)

| Category | Technology | Version |
|---|---|---|
| **Language** | Kotlin | 1.9.0 |
| **UI Framework** | Jetpack Compose | 2024.02.00 |
| **Material Design** | Material 3 | 1.2.0 |
| **Navigation** | Navigation Compose | 2.7.5 |
| **State Management** | ViewModel + Flow | 2.7.0 |
| **Data Storage** | DataStore Preferences | 1.0.0 |
| **Networking** | Retrofit | 2.9.0 |
| **Image Loading** | Coil | 2.5.0 |
| **Notifications** | Firebase FCM | 33.1.0 |
| **Dependency Injection** | Manual (Hilt ready) | — |

### Backend (API)

| Category | Technology | Version |
|---|---|---|
| **Language** | Python | 3.10+ |
| **Framework** | FastAPI | 0.104.1 |
| **Server** | Uvicorn | 0.24.0 |
| **Data Validation** | Pydantic | 2.5.0 |
| **Push Notifications** | Firebase Admin SDK | 6.4.0 |
| **Database** | MongoDB / PostgreSQL | — |
| **Caching** | Redis | — |

### Infrastructure

| Service | Technology |
|---|---|
| **Authentication** | JWT / Firebase Auth |
| **Push Notifications** | Firebase Cloud Messaging (FCM) |
| **Cloud Platform** | Firebase / AWS |
| **CI/CD** | GitHub Actions |

---
## 🏗️ Project Architecture

```text
temple-crowd-management/
│
├── .idea/                                  # Android Studio project settings
│
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── temple/
│   │       │           └── crowdmanagement/
│   │       │               ├── MainActivity.kt
│   │       │               │
│   │       │               ├── core/
│   │       │               │   ├── model/
│   │       │               │   │   └── TempleSite.kt
│   │       │               │   └── navigation/
│   │       │               │       ├── NavGraph.kt
│   │       │               │       └── Screen.kt
│   │       │               │
│   │       │               ├── features/
│   │       │               │   ├── auth/
│   │       │               │   │   ├── AuthViewModel.kt
│   │       │               │   │   ├── LoginScreen.kt
│   │       │               │   │   ├── SignupScreen.kt
│   │       │               │   │   └── LanguageScreen.kt
│   │       │               │   │
│   │       │               │   ├── dashboard/
│   │       │               │   │   ├── presentation/
│   │       │               │   │   │   ├── DashboardScreen.kt
│   │       │               │   │   │   └── HomeViewModel.kt
│   │       │               │   │   ├── components/
│   │       │               │   │   │   ├── ActionGrid.kt
│   │       │               │   │   │   ├── AppLoadingScreen.kt
│   │       │               │   │   │   ├── CrowdPredictionCard.kt
│   │       │               │   │   │   ├── CrowdStatusCard.kt
│   │       │               │   │   │   ├── DashboardTopBar.kt
│   │       │               │   │   │   ├── HeaderSection.kt
│   │       │               │   │   │   ├── TempleTimingsCard.kt
│   │       │               │   │   │   ├── TodayEventsCard.kt
│   │       │               │   │   │   └── WeatherCard.kt
│   │       │               │   │   ├── model/
│   │       │               │   │   │   └── DashboardUiState.kt
│   │       │               │   │   └── repository/
│   │       │               │   │       └── DashboardRepository.kt
│   │       │               │   │
│   │       │               │   ├── security/
│   │       │               │   │   ├── presentation/
│   │       │               │   │   │   ├── SecurityAuthViewModel.kt
│   │       │               │   │   │   ├── SecurityDashboardScreen.kt
│   │       │               │   │   │   ├── SecurityDashboardViewModel.kt
│   │       │               │   │   │   ├── SecurityNotificationScreen.kt
│   │       │               │   │   │   └── SecurityNotificationViewModel.kt
│   │       │               │   │   ├── components/
│   │       │               │   │   │   ├── ContactControllerDialog.kt
│   │       │               │   │   │   ├── DutyStatusCard.kt
│   │       │               │   │   │   ├── DutyStatusDialog.kt
│   │       │               │   │   │   ├── IncidentReportingDialog.kt
│   │       │               │   │   │   └── QuickActionsSection.kt
│   │       │               │   │   └── model/
│   │       │               │   │       ├── DutyStatus.kt
│   │       │               │   │       └── IncidentData.kt
│   │       │               │   │
│   │       │               │   ├── booking/
│   │       │               │   │   └── presentation/
│   │       │               │   │       └── BookingScreen.kt
│   │       │               │   │
│   │       │               │   ├── queue/
│   │       │               │   │   └── presentation/
│   │       │               │   │       └── SmartQueueScreen.kt
│   │       │               │   │
│   │       │               │   ├── map/
│   │       │               │   │   └── presentation/
│   │       │               │   │       └── LiveTempleMapScreen.kt
│   │       │               │   │
│   │       │               │   ├── emergency/
│   │       │               │   │   └── presentation/
│   │       │               │   │       └── EmergencySOSScreen.kt
│   │       │               │   │
│   │       │               │   ├── guide/
│   │       │               │   │   ├── presentation/
│   │       │               │   │   │   ├── GuideScreen.kt
│   │       │               │   │   │   └── GuideViewModel.kt
│   │       │               │   │   ├── components/
│   │       │               │   │   │   ├── AartiTimingsCard.kt
│   │       │               │   │   │   ├── ContactCard.kt
│   │       │               │   │   │   ├── FAQSection.kt
│   │       │               │   │   │   ├── QuickInfoCard.kt
│   │       │               │   │   │   └── TempleInfoCard.kt
│   │       │               │   │   ├── model/
│   │       │               │   │   │   └── GuideData.kt
│   │       │               │   │   └── repository/
│   │       │               │   │       └── GuideRepository.kt
│   │       │               │   │
│   │       │               │   └── profile/
│   │       │               │       ├── presentation/
│   │       │               │       │   ├── ProfileScreen.kt
│   │       │               │       │   └── ProfileViewModel.kt
│   │       │               │       ├── components/
│   │       │               │       │   ├── HelpSection.kt
│   │       │               │       │   ├── ProfileHeader.kt
│   │       │               │       │   ├── QuickSettingsCard.kt
│   │       │               │       │   ├── RecentBookingCard.kt
│   │       │               │       │   └── VisitPreferencesCard.kt
│   │       │               │       ├── model/
│   │       │               │       │   └── ProfileData.kt
│   │       │               │       └── repository/
│   │       │               │           └── ProfileRepository.kt
│   │       │               │
│   │       │               └── ui/
│   │       │                   └── theme/
│   │       │                       ├── Theme.kt
│   │       │                       ├── Color.kt
│   │       │                       └── Typography.kt
│   │       │
│   │       └── res/
│   │           ├── drawable/
│   │           │   └── ic_notification.xml
│   │           ├── mipmap/
│   │           └── values/
│   │               ├── colors.xml
│   │               └── strings.xml
│   │
│   ├── build.gradle.kts
│   └── proguard-rules.pro
│
├── backend/
│   ├── main.py
│
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
│
├── .gitignore
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── live_phone_screen.png
├── settings.gradle.kts
└── README.md
```
## 🚀 Getting Started

### Prerequisites

| Tool           | Version             |
| -------------- | -------------------- |
| Android Studio | Hedgehog 2023.1.1+   |
| JDK            | 17                   |
| Kotlin         | 1.9.0                |

---

### 1. Clone & Open

```bash
git clone https://github.com/yourusername/temple-crowd-management.git
```

1. Open project in Android Studio
2. Wait for Gradle sync to complete

---

### 2. Run the App

1. Select emulator or connect real device
2. Click **Run ▶️** button
3. App opens on device

---

### 3. Login Credentials

| Role     | Email                | Password    |
| -------- | --------------------- | ----------- |
| Pilgrim  | test@example.com      | password123 |
| Security | security@temple.com   | password123 |

---

### 4. Quick Test Flow

```
Login → Select Role → Enter Credentials → Click Sign In → Dashboard Opens
```

---

### 5. Troubleshooting

| Issue              | Fix                                                  |
| ------------------- | ----------------------------------------------------- |
| Gradle sync fails   | File → Invalidate Caches → Invalidate and Restart     |
| App won't install   | Build → Clean Project → Rebuild Project               |
| Emulator issues     | Tools → AVD Manager → Wipe Data                       |


---

## 🔮 Future Scope

| Feature                               | Description                                |
| -------------------------------------- | ------------------------------------------- |
| 🧠 **Advanced AI**                     | Predictive crowd analytics with ML models   |
| 📊 **Real-time Analytics Dashboard**   | Live temple statistics and trends           |
| 🗣️ **Voice Assistance**                | Voice-guided navigation for pilgrims        |
| 📍 **GPS Tracking**                    | Live tracking of security personnel         |
| 🎥 **CCTV Integration**                | Live camera feed integration                |
| 📱 **Wearable Support**                | Smartwatch app for security                 |
| 🤖 **Chatbot**                         | AI-powered pilgrim assistance                |
| 📈 **Historical Data**                 | Temple visit trends and patterns            |
| 🌐 **Multi-Language Expansion**        | Support for more regional languages         |
| 🔗 **Third-party Integration**         | Payment gateways, transport APIs            |

---

## 📄 License

This project is currently under development.

License information will be added soon.
