# Ride Assistant - Android Application

Applicație Android profesională pentru șoferii Bolt și Uber care detectează automat curse și calculează rentabilitatea în timp real.

## 🚀 Caracteristici Principale

### 1. Accessibility Service
- ✅ Detectare automată Bolt/Uber
- ✅ Monitorizare modificări UI
- ✅ Extragere date prin Accessibility
- ✅ Fallback la OCR dacă nu se poate citi prin Accessibility

### 2. OCR AI (ML Kit)
- ✅ Text Recognition cu ML Kit
- ✅ Detectare preț, distanță, timp
- ✅ Support pentru Bolt și Uber
- ✅ Procesare rapidă doar când apare ofertă nouă

### 3. Overlay Inteligent
- ✅ Afișare peste Bolt/Uber
- ✅ Informații: preț, km, RON/km, profit, scor
- ✅ Color-coding (verde/galben/portocaliu/roșu)
- ✅ Overlay draggable pe ecran
- ✅ Dimensiuni configurabile

### 4. Calculator Profit
- ✅ Calcul automat cost combustibil
- ✅ Calcul comisii Bolt/Uber
- ✅ Profit brut și net
- ✅ RON/km și RON/oră
- ✅ Scor profit (0-100)
- ✅ Color-coding: 🟢🟡🟠🔴

### 5. Google Maps
- ✅ Afișare curse pe hartă
- ✅ Zone profitabile/slabe
- ✅ Heatmap cursor
- ✅ Istoric curse salvate

### 6. Istoric Curse
- ✅ Salvare automată: ora, dată, preț, km, profit
- ✅ Filtrare după platform, status, perioadă
- ✅ Ștergere curse
- ✅ Locații salvate

### 7. Statistici Detaliate
- ✅ Profit pe zi/săptămână/lună
- ✅ RON/km mediu
- ✅ Număr curse
- ✅ Ore online/active
- ✅ Consum combustibil
- ✅ Venit brut/net
- ✅ Distribuție Bolt/Uber

### 8. Setări Personalizate
- ✅ Consum mașină (l/100km)
- ✅ Preț combustibil (RON/l)
- ✅ Comision Bolt (%)
- ✅ Comision Uber (%)
- ✅ Profit minim (RON)
- ✅ RON/km minim
- ✅ Dimensiune overlay
- ✅ Poziție overlay
- ✅ Pornire automată
- ✅ Dark mode

### 9. OCR Inteligent
- ✅ Detectare automată platformă
- ✅ Reguli diferite pentru Bolt/Uber
- ✅ Toleranță la schimbări UI
- ✅ Confidence score pentru date

### 10. Performanță
- ✅ Consum minimal de baterie
- ✅ OCR doar când necesar
- ✅ Debounce pentru Accessibility
- ✅ Coroutines pentru async operations
- ✅ Foreground Service optimizat

## 🏗️ Arhitectură

### Design Patterns
- **MVVM** - Model-View-ViewModel
- **Repository Pattern** - Data abstraction
- **Singleton** - Dependency Injection cu Hilt
- **Observer Pattern** - StateFlow
- **Factory Pattern** - ViewModel factories

### Tehnologii Folosite
- **Kotlin** - Limbaj principal
- **Jetpack Compose** - UI moderne
- **Room Database** - Stocaj local
- **Hilt** - Dependency Injection
- **Coroutines** - Async operations
- **Flow** - Reactive streams
- **ML Kit** - OCR
- **Google Play Services** - Maps & Location
- **Material Design 3** - UI Design
- **Firebase** - Analytics & Crashlytics

## 📱 Structură Proiect

```
app/src/main/java/com/rideassistant/
├── MainActivity.kt                          # Activity principal
├── App.kt                                   # Application class cu Hilt
│
├── accessibility/
│   ├── RideAccessibilityService.kt         # Accessibility Service
│   └── AccessibilityHelper.kt              # Helper extragere date
│
├── ocr/
│   ├── OCREngine.kt                        # Engine OCR cu ML Kit
│   ├── TextRecognitionHelper.kt            # Helper recunoaștere text
│   └── ScreenCaptureManager.kt             # Capture ecran
│
├── overlay/
│   └── RideOverlayManager.kt               # Manager overlay & OverlayView
│
├── services/
│   ├── RideAssistantService.kt             # Foreground Service
│   └── NotificationManagerHelper.kt        # Notificări
│
├── repository/
│   └── RideRepository.kt                   # Repository pattern
│
├── database/
│   ├── AppDatabase.kt                      # Room Database
│   ├── RideDao.kt                          # DAO pentru Ride
│   └── SettingsDao.kt                      # DAO pentru Settings
│
├── models/
│   ├── Ride.kt                             # Model Ride
│   ├── Settings.kt                         # Model Settings
│   └── RideModels.kt                       # RideData, ProfitResult
│
├── viewmodel/
│   ├── MainViewModel.kt                    # ViewModel Home
│   ├── HistoryViewModel.kt                 # ViewModel Istoric
│   ├── StatsViewModel.kt                   # ViewModel Statistici
│   └── SettingsViewModel.kt                # ViewModel Setări
│
├── ui/
│   ├── screens/
│   │   ├── HomeScreen.kt                   # Ecran principal
│   │   ├── HistoryScreen.kt                # Ecran istoric
│   │   ├── StatsScreen.kt                  # Ecran statistici
│   │   ├── MapsScreen.kt                   # Ecran hărți
│   │   └── SettingsScreen.kt               # Ecran setări
│   └── theme/
│       └── Theme.kt                        # Material Design 3 theme
│
├── maps/
│   └── MapsManager.kt                      # Google Maps integration
│
├── utils/
│   ├── Constants.kt                        # Constante și Logger
│   ├── Extensions.kt                       # Extensii Kotlin
│   ├── ProfitCalculator.kt                 # Calculator profit
│   ├── PreferencesManager.kt               # Preferințe locale
│   └── PermissionUtils.kt                  # Utility permisiuni
│
└── di/
    ├── DatabaseModule.kt                   # Hilt module Database
    └── AppModule.kt                        # Hilt module App
```

## 🔧 Configurație

### build.gradle.kts (app)
- minSdk: 26 (Android 8.0)
- targetSdk: 34 (Android 14)
- Kotlin: 1.9.20
- Compose: 1.6.0
- Material3: 1.1.2

### Dependencies Importante
- androidx.room:room-runtime:2.6.1
- androidx.compose:compose-ui:1.6.0
- com.google.dagger:hilt-android:2.48
- com.google.mlkit:text-recognition:16.0.0
- com.google.android.gms:play-services-maps:18.2.0
- org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3

## 📋 Permisiuni Necesare

```xml
<!-- Obligatorii -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.BIND_ACCESSIBILITY_SERVICE" />
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />

<!-- Pentru locații -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

<!-- Pentru notificări -->
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

## 🚀 Instalare și Rulare

### 1. Clonare Repository
```bash
git clone https://github.com/strungarudanut5-maker/ride-assistant.git
cd ride-assistant
```

### 2. Deschidere în Android Studio
- File → Open → Selectează folderul proiectului
- Gradle sync automat

### 3. Configurare Google Maps API
- Mergi în AndroidManifest.xml
- Înlocuiește `YOUR_GOOGLE_MAPS_API_KEY` cu cheia ta

### 4. Build & Run
```bash
# Via Android Studio: Run → Run 'app'
# Sau via terminal:
./gradlew installDebug
```

### 5. Configurare Permisiuni
- Activeaza Accessibility Service: Settings → Accessibility → Ride Assistant
- Permite System Overlay: Settings → Apps & notifications → App permissions → Display over other apps
- Permite Locație: Settings → Apps & notifications → App permissions → Location

## 🎨 Interfață Utilizator

### Material Design 3
- Light/Dark theme automat
- Culori dinamice
- Animații fluide
- Responsive design

### Ecrane
1. **Home** - Curse recente cu detalii
2. **Istoric** - Toate cursele cu filtrare
3. **Statistici** - Analize detaliate
4. **Hărți** - Vizualizare pe hartă
5. **Setări** - Personalizare aplicație

## 📊 Calculul Profitului

```kotlin
Fuel Cost = (Distance / 100) * FuelConsumption * FuelPrice
Commission = Price * CommissionPercent / 100
Gross Profit = Price - Fuel Cost - Commission
RON/km = Gross Profit / Distance
RON/hour = Gross Profit / (Time / 60)

Profit Score = (RON/km / MaxRonPerKm) * 100

Color Coding:
- 🟢 Verde (Excellent): > 25 RON/km
- 🟡 Galben (Good): 15-25 RON/km
- 🟠 Portocaliu (Acceptable): 10-15 RON/km
- 🔴 Roșu (Poor): < 10 RON/km
```

## 🔒 Securitate

- ✅ ProGuard minification pentru release
- ✅ Data encryption cu Room
- ✅ Permisiuni runtime pe Android 6+
- ✅ Firebase Crashlytics pentru bug tracking
- ✅ No API keys in code (folosește BuildConfig)

## 🐛 Debugging

- Timber logging cu color-coded output
- Logger custom cu tag support
- Debug builds cu logare detaliată
- Release builds optimizate

## 📈 Extinderi Future

- [ ] Export statistici (CSV, PDF)
- [ ] Sincronizare cloud (Firebase Realtime Database)
- [ ] Predicție AI pentru curse bune
- [ ] Integration Stripe pentru suporț platitori
- [ ] Widget home screen
- [ ] Sharing statistici pe social media
- [ ] Multi-language support
- [ ] Sunete notificări personalizate

## 📝 Licență

Copyright © 2024. Toate drepturile rezervate.

## 👤 Autor

Creat de: **strungarudanut5-maker**

## 💬 Suport

Pentru probleme, deschide un issue pe GitHub.

---

## 🎯 Cum Funcționează

### 1. Detectare Curs
```
Utilizator deschide Bolt/Uber
        ↓
Accessibility Service sesizează schimbare
        ↓
Extrage text prin Accessibility Service
        ↓
Dacă nu reușește → activeaza OCR
        ↓
Extrage: preț, distanță, timp, locații
        ↓
Trimite date la calculator profit
```

### 2. Calculator Profit
```
Date curs (preț, km, timp)
        ↓
Citeste setări (consum, comisii, prag)
        ↓
Calculeaza: cost combustibil, comisie
        ↓
Calculeaza: profit brut/net, RON/km, RON/h
        ↓
Genereza scor (0-100)
        ↓
Determinează culoare: 🟢🟡🟠🔴
```

### 3. Afișare Overlay
```
Calcul gata
        ↓
Creează OverlayView cu date
        ↓
Afișeaza deasupra Bolt/Uber
        ↓
Utilizator poate: muta, citi, copia
        ↓
Clic acceptare → salvare curs în database
```

### 4. Salvare Curs
```
Utilizator accepta oferta
        ↓
Room database insereaza curs
        ↓
Flow actualizeaza ViewModel
        ↓
UI refreshs din State Flow
        ↓
Curs apare in Istoric & Statistici
```

---

**Made with ❤️ for Bolt & Uber drivers**
