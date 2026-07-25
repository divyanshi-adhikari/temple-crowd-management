# 🛕 SVH26008 - Temple & Pilgrimage Crowd Management Mobile App (Android / Kotlin)

> **Problem Statement ID**: SVH26008  
> **Title**: Temple & Pilgrimage Crowd Management (Somnath, Dwarka, Ambaji, Pavagadh)  
> **Organization**: Gujarat Council on Science & Technology (GUJCOST), Dept. of Science & Technology, Govt. of Gujarat  
> **Category**: Software | **Theme**: Heritage & Culture  

---

## 👨‍💻 Work Division & Architecture Overview

The app is structured into strict feature module boundaries to enable parallel work between **Developer 1** and **Developer 2** without code collisions.

```
app/src/main/java/com/temple/crowdmanagement/
├── MainActivity.kt
├── ui/theme/                       # Shared Spiritual Color Tokens & Typography
├── core/                           # Common Navigation & Data Models
└── features/                       # ⚠️ STRICT NO-CROSSING ZONES
    ├── auth/                       # 👈 DEV 1 WORKSPACE (Login, Signup, Multilingual)
    ├── dashboard/                  # 👈 DEV 1 WORKSPACE (Live status, Weather, Timings)
    ├── guide/                      # 👈 DEV 1 WORKSPACE (Pilgrim guide, Aarti, FAQs)
    ├── profile/                    # 👈 DEV 1 WORKSPACE (Profile, Pass History, Settings)
    ├── booking/                    # 👈 DEV 2 WORKSPACE (Smart Darshan Pass & Slots)
    ├── map/                        # 👈 DEV 2 WORKSPACE (Live Canvas Map & Heatmap)
    ├── queue/                      # 👈 DEV 2 WORKSPACE (Smart Virtual Queue Engine)
    └── emergency/                  # 👈 DEV 2 WORKSPACE (Panic SOS & Offline Mesh Relay)
```

---

## 🚀 Implemented Developer 2 Features (Action & Interactive Systems)

### 1. 🎫 Smart Darshan Booking (`features/booking`)
- **Multi-Temple Support**: Switch between **Somnath**, **Dwarka**, **Ambaji**, and **Pavagadh**.
- **Interactive Time Slot Selection**: Displays live slot capacities, percent full indicator bars, and special **Aarti** slot flags.
- **Pass Generation with QR Code**: Generates digital QR passes (`TC-XXXXXX`) with lead devotee details and count.
- **My Passes Archive**: Interactive list of active and historic darshan passes.

### 2. 🗺️ Live Temple Map & Crowd Heatmap (`features/map`)
- **Custom Jetpack Compose Canvas**: Custom architectural floorplan drawing with real-time radial crowd heat zones.
- **Color-Coded Crowd Density**: Red (Critical Overcrowding), Orange (Heavy/Moderate), Green (Smooth Flow).
- **POI Interactive Layers**: Filter chips to toggle Entry/Exit Gates, Parking Spaces with capacity counts, Medical Stations, Washrooms, and Water points.

### 3. ⏱️ Smart Virtual Queue Engine (`features/queue`)
- **One-Tap Virtual Entry**: Devotees join virtual queues remotely without standing in physical lines.
- **Real-time Live Ticker**: Token ID, current queue position countdown, and estimated waiting duration.
- **Notify Me Engine**: Push alert engine triggering when devotees are within 5 minutes of sanctum entry.
- **Queue Analytics**: Live crowd flow rate metrics (e.g. 145 devotees cleared per 10 mins).

### 4. 🚨 Emergency SOS & Offline Mesh Relay (`features/emergency`)
- **Panic SOS Trigger**: Rapid 1-tap SOS trigger broadcasting GPS coordinates.
- **Direct Category Alerts**: Medical Assistance, Stampede Hazard, Fire Risk, and Lost Person.
- **Offline Mesh Protocol Simulation**: Simulates peer-to-peer Bluetooth Low Energy (BLE) packet forwarding to nearest security marshals when cellular network is disconnected.

---

## 🛠️ How to Open & Test in Android Studio

1. **Open Android Studio**:
   - Click `File` -> `Open...` and select the folder:
     `Temple Crowd management app`

2. **Sync Gradle**:
   - Android Studio will detect Gradle wrapper and sync dependencies automatically.
   - Wait for `Gradle Sync Finished Successfully`.

3. **Run on Emulator / Connected Android Device**:
   - Select an Android Virtual Device (AVD - Android 8.0 / API 26 or higher recommended).
   - Press **Run 'app'** (`Shift + F10` or the green Play icon).

4. **Testing Developer 2 Features**:
   - **Map Tab**: Switch between Somnath/Dwarka/Ambaji/Pavagadh, tap heat zone circles to view live capacity, and filter POI chips.
   - **Queue Tab**: Tap `GET VIRTUAL QUEUE TOKEN`, test `SIMULATE STEP` to watch queue position decrease in real-time, and toggle `Notify Me Engine`.
   - **Booking Tab**: Select slot, set devotee count, and click `CONFIRM & GENERATE QR PASS`.
   - **Emergency Tab**: Test panic SOS, toggle `Network Offline (Mesh Protocol Active)`, and trigger category alerts.
