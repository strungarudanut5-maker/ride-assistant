# Ride Assistant

Applicație Android profesională pentru șoferii Bolt și Uber care detectează și analizează rentabilitatea curselor în timp real.

## Caracteristici

- 🔍 **Accessibility Service** - Detectare automată Bolt/Uber
- 🧠 **OCR AI** - ML Kit Text Recognition pentru citire date
- 🎯 **Overlay** - Afișare rezultate peste aplicațiile rideshare
- 💰 **Profit Calculator** - Calcul automat cu color-coding
- 🗺️ **Google Maps** - Vizualizare zone profitabile
- 📊 **Statistici** - Istoric complet și analize
- ⚙️ **Setări** - Personalizare completă
- 🚀 **Performanță** - Optimizat pentru baterie și viteză

## Tehnologie

- **Kotlin** - Limbaj principal
- **Jetpack Compose** - UI moderne
- **MVVM** - Arhitectură aplicației
- **Room Database** - Storage local
- **Hilt** - Dependency Injection
- **Coroutines** - Async operations
- **ML Kit** - OCR
- **Google Maps** - Hărți
- **Material Design 3** - Design language

## Cerințe

- Android 10+ (API 26+)
- Google Play Services
- Google Maps API Key

## Instalare

1. Clonează repository-ul
2. Deschide în Android Studio
3. Adaugă Google Maps API Key în AndroidManifest.xml
4. Build & Run

## Structură Proiect

```
app/src/main/java/com/rideassistant/
├── MainActivity.kt
├── App.kt
├── accessibility/          # Accessibility Service
├── ocr/                   # OCR Engine
├── overlay/               # Overlay Management
├── services/              # Background Services
├── repository/            # Data Layer
├── database/              # Room Database
├── models/                # Data Models
├── viewmodel/             # MVVM ViewModels
├── ui/                    # Compose Screens
├── maps/                  # Maps Integration
├── utils/                 # Utilities
└── di/                    # Hilt Modules
```

## Permisiuni Necesare

- `INTERNET` - Conectare la servicii
- `ACCESS_FINE_LOCATION` - GPS
- `FOREGROUND_SERVICE` - Rulare în fundal
- `SYSTEM_ALERT_WINDOW` - Overlay
- `BIND_ACCESSIBILITY_SERVICE` - Accessibility

## Licență

Copyright © 2024. Toate drepturile rezervate.
