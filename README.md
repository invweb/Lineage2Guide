# Lineage 2 Mobile Guide

A mobile companion guide app for Lineage 2 (Interlude chronicle) with offline caching, real-time sync, and comprehensive game data.

## 📱 Features

- **📦 31,000+ items** — weapons, armor, consumables with stats and drop info
- **⚔️ 15,000+ skills** — damage, healing, buffs, debuffs with ranges and cooldowns
- **🧙 6,400+ NPCs** — monsters, vendors, quest givers, bosses
- **📜 329 quests** — full quest lines with rewards and requirements
- **🏰 89 classes** — class trees and progression paths
- **📡 Pull-to-refresh** — sync with live data
- **📴 Offline support** — Room cache keeps data available without connection
- **🔔 Connectivity checker** — offline banner with slide animation
- **🔄 Sync indicator** — visual feedback during data sync
- **🔍 Search & filter** — find items by name, rarity, type; filter skills by class

## 🛠 Tech Stack

| Layer | Technology |
|-------|-----------|
| **Language** | Kotlin |
| **UI** | Jetpack Compose + Material 3 |
| **Architecture** | MVVM |
| **DI** | Koin 4.0.0 |
| **Local DB** | Room 2.7.2 |
| **API** | Retrofit 2.12.0 |
| **Server** | Ktor 3.2.3 (Netty) |
| **Build** | AGP 9.4.1, Gradle 9.6.0 |
| **SDK** | compileSdk 37, minSdk 24 |

## 📊 Data

Game data sourced from [lineage2-api](https://github.com/cuteshaun/lineage2-api) (Interlude chronicle):

| Entity | Count | Compressed |
|--------|-------|------------|
| Items | 9,206 | 249 KB |
| Skills | 15,308 | 270 KB |
| NPCs | 6,472 | 372 KB |
| Quests | 329 | 22 KB |
| Classes | 89 | 63 KB |
| **Total** | **31,404** | **975 KB** (from 24 MB source) |

## 🚀 Getting Started

### Prerequisites
- Android Studio (latest)
- JDK 17+
- Android SDK (API 37)

### Run the App
1. Clone the repository:
```bash
git clone https://github.com/invweb/Lineage2Guide.git
cd Lineage2Guide
```

2. Open in Android Studio
3. Sync Gradle files
4. Run on emulator or device

### Run the Mock Server (Development)
The app uses a Ktor server for game data. For development:

```bash
cd server
./gradlew run
```

Server starts on `http://localhost:8080` (emulator: `http://10.0.2.2:8080`).

## 📂 Project Structure

```
├── app/                    # Android application module
│   ├── di/                 # Koin dependency injection
│   ├── data/               # Room DAOs, Retrofit API, repositories
│   └── ui/                 # Compose screens & navigation
│       ├── items/          # Items list & detail
│       ├── skills/         # Skills list & detail
│       ├── npcs/           # NPCs list & detail
│       ├── quests/         # Quests list & detail
│       ├── classes/        # Classes list & detail
│       └── common/         # Shared components & states
└── server/                 # Ktor backend module
    ├── data/               # RealDataLoader, ServerMockData
    ├── dto/                # API data transfer objects
    ├── routing/            # API endpoints
    └── resources/          # Compressed .gz game data files
```

## 📡 API Endpoints

| Endpoint | Description |
|----------|-------------|
| `GET /api/v1/items` | List all items (supports `?search=&rarity=&type=`) |
| `GET /api/v1/items/{id}` | Get item by ID |
| `GET /api/v1/skills` | List all skills (supports `?classRestriction=&type=`) |
| `GET /api/v1/skills/{id}` | Get skill by ID |
| `GET /api/v1/npcs` | List all NPCs (supports `?search=&type=`) |
| `GET /api/v1/npcs/{id}` | Get NPC by ID |
| `GET /api/v1/quests` | List all quests (supports `?search=&type=`) |
| `GET /api/v1/quests/{id}` | Get quest by ID |
| `GET /api/v1/classes` | List all classes |
| `GET /api/v1/classes/{id}` | Get class by ID |
| `GET /api/v1/sync` | Check for updates |

## 📝 License

MIT License
