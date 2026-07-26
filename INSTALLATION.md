# Ghid Instalare Ride Assistant

## Cerințe Preliminare

✅ Android Studio 2023.1+
✅ JDK 17+
✅ Android SDK 34
✅ Gradle 8.0+
✅ Git
✅ Google Maps API Key

## Pasii Instalării

### 1. Clone Repository

```bash
cd ~/Projects
git clone https://github.com/strungarudanut5-maker/ride-assistant.git
cd ride-assistant
```

### 2. Deschidere în Android Studio

- Android Studio → Open → Selectează folderul `/ride-assistant`
- Asteapta să finalizeze Gradle sync (2-5 minute)
- Verifica că nu sunt erori

### 3. Configurare Google Maps

**Pasul 1: Generează API Key**
- Google Cloud Console → https://console.cloud.google.com
- Creeaza proiect nou: "Ride Assistant"
- Activeaza: Google Maps SDK for Android
- Creeaza credential: API Key
- Copie cheia

**Pasul 2: Adauga cheia în proiect**

Deschide: `app/src/main/AndroidManifest.xml`

Gaseste:
```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="YOUR_GOOGLE_MAPS_API_KEY" />
```

Inlocuieste:
```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="AIzaSyDxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" />
```

### 4. Build Proiectul

```bash
# Build debug
./gradlew assembleDebug

# Build release
./gradlew assembleRelease

# Sau din Android Studio: Build → Make Project
```

### 5. Conectare Dispozitiv

**Cu fir USB:**
- Conectează telefon la PC
- Activeaza "USB Debugging" pe telefon
- Android Studio detecteaza dispozitiv

**Cu emulator:**
- Deschide: Tools → Device Manager
- Creeaza / Start emulator

### 6. Instalare APK

```bash
# Prin Android Studio: Run → Run 'app'
# Sau prin terminal:
./gradlew installDebug
adb shell am start -n com.rideassistant/.MainActivity
```

### 7. Configurare Permisiuni

**Accessibility Service:**
1. Settings → Accessibility → Services
2. Selecteaza "Ride Assistant"
3. Enable toggle

**System Overlay:**
1. Settings → Apps → Special app access → Display over other apps
2. Selecteaza "Ride Assistant"
3. Enable "Allow display over other apps"

**Location:**
1. Settings → Apps → Permissions → Location
2. Selecteaza "Ride Assistant"
3. Permite "Allow all the time" (recomandat)

**Notifications:**
1. Settings → Apps → Permissions → Notifications
2. Selecteaza "Ride Assistant"
3. Enable notifications

## Configurare Inițiala

### 1. Setări Mașină

App → Settings:
- **Consum combustibil**: Introdu consumul actual (ex: 8 l/100km)
- **Preț combustibil**: Introdu prețul actual (ex: 6.50 RON/l)

### 2. Setări Platforme

- **Comision Bolt**: De obicei 20-25%
- **Comision Uber**: De obicei 25-30%

### 3. Praguri Profit

- **Profit minim**: Minimul care îți convine (ex: 10 RON)
- **RON/km minim**: Pragul minim acceptabil (ex: 5 RON/km)

### 4. Personalizare Overlay

- **Dimensiune**: Ajusteaza după preferință
- **Poziție**: Se poate muta cu degetul pe ecran
- **Opacitate**: Ajusteaza transparența

## Testare

### 1. Test Accessibility

- Deschide app-ul Bolt
- Verifica dacă Accessibility Service primeste evenimenete
- Logs: "Window state changed on BOLT"

### 2. Test OCR

- Fă o captură de ecran cu o ofertă Bolt
- OCR ar trebui să extragă: preț, km, timp

### 3. Test Overlay

- Deschide Bolt/Uber
- Când apare ofertă, overlay-ul ar trebui să se arate
- Poți face swipe pe overlay

### 4. Test Salvare

- Accepta o ofertă (sau simulează)
- Verifica că apare în "Istoric"
- Verifica statistici actualizate

## Troubleshooting

### APK nu instalează

```bash
# Dezinstaleaza versiune veche
adb uninstall com.rideassistant

# Instalează din nou
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Accessibility Service nu se activează

1. Verifica AndroidManifest.xml
2. Verifica accessibility_service_config.xml
3. Dezinstaleaza și reinstaleaza app-ul
4. Verifica logs: `adb logcat | grep Accessibility`

### Overlay nu se vede

1. Verifica permisiunea "Display over other apps"
2. Verifica că Window Manager Type este corect
3. Logs: `adb logcat | grep RideOverlayManager`

### OCR nu funcționează

1. Verifica că ML Kit este inițializat
2. Verifica că imagine e suficient clară
3. Logs: `adb logcat | grep OCREngine`

### Database errors

1. Dezinstaleaza app
2. Șterge data: `adb shell pm clear com.rideassistant`
3. Reinstaleaza

## Command Line Utilities

```bash
# View logs
adb logcat
adb logcat | grep "RideAssistant"
adb logcat | grep "OCR"
adb logcat | grep "Accessibility"

# Instaleaza APK
adb install -r app/build/outputs/apk/debug/app-debug.apk

# Dezinstaleaza app
adb uninstall com.rideassistant

# Lansează app
adb shell am start -n com.rideassistant/.MainActivity

# Verifica permisiuni
adb shell pm list permissions
adb shell pm dump com.rideassistant
```

## Release Build

### 1. Creeaza Keystore

```bash
keytool -genkey -v -keystore ride_assistant.keystore \
  -keyalg RSA -keysize 2048 -validity 10000 -alias ride_key
```

### 2. Configurare gradle.properties

```properties
STORE_FILE=ride_assistant.keystore
STORE_PASSWORD=your_password
KEY_ALIAS=ride_key
KEY_PASSWORD=your_password
```

### 3. Build Release APK

```bash
./gradlew assembleRelease
```

APK se găsește: `app/build/outputs/apk/release/app-release.apk`

## Resurse Utile

- [Android Developer Docs](https://developer.android.com)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Room Database](https://developer.android.com/training/data-storage/room)
- [Hilt Dependency Injection](https://developer.android.com/training/dependency-injection/hilt-android)
- [ML Kit](https://developers.google.com/ml-kit)
- [Google Maps SDK](https://developers.google.com/maps/documentation/android-sdk)

## Support

Dacă ai probleme:
1. Verifica logs: `adb logcat`
2. Reia troubleshooting steps
3. Deschide issue pe GitHub

---

**Successfully installed! 🎉**
