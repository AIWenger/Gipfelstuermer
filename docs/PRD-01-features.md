# PRD Teil 1: Übersicht, Stack, Architektur & Features

> Weiter zu [Teil 2: Datenmodell, Theming, Animationen, i18n & Projektstruktur](./PRD-02-technical.md)

---

## 1. Produktübersicht

**GipfelStürmer** ist eine lokale Android-App mit 30 Kletterspielen für Kinder.
Die App läuft vollständig offline ohne API, Login oder Benutzerkonto.
Alle Daten werden lokal mit Room gespeichert. Die App unterstützt Deutsch und Englisch (i18n).

---

## 2. Technologie-Stack (Stand Februar 2026)

| Komponente | Version |
|---|---|
| Kotlin | 2.3.10 |
| AGP | 9.0.0 |
| Gradle | 9.3.1 |
| Compose BOM | 2026.01.01 |
| Material 3 | 1.4.0 |
| Room | 2.8.4 |
| Navigation Compose | 2.9.7 |
| Coroutines | 1.10.2 |
| Core KTX | 1.17.0 |
| Lifecycle | 2.10.0 |
| compileSdk / targetSdk | 36 |
| minSdk | 26 |

---

## 3. Architektur

- **Single-Activity, Compose-first**
- **MVVM mit Unidirectional Data Flow (UDF)**
- **Schichten:** UI (Composables) -> ViewModel -> Repository -> Room Database
- **Keine Fragments, kein API-Layer**
- **i18n:** `stringResource(R.string.*)` + `res/values-de/strings.xml` / `res/values-en/strings.xml`
- **Pre-populated Database:** 30 Spiele werden beim ersten Start aus einer vordefinierten Liste in Room eingefügt

---

## 4. Screens & Features

### 4.1 Hauptscreen (Single Screen App)

Die gesamte App besteht aus **einem scrollbaren Hauptscreen** mit folgenden Sektionen von oben nach unten:

#### 4.1.1 Header-Card (Hero)
- **Violetter Gradient** (von blau-violett zu violett)
- Großer Titel: **"GipfelStürmer"** (weiß, bold)
- Untertitel: **"30 Spiele für kleine Kletterer"** (weiß, semi-transparent)
- **Berg-Icon** (Mountain) rechts oben in der Card
- **Suchleiste** unten in der Card:
  - Abgerundetes Textfeld mit Lupe-Icon
  - Placeholder: "Spiel, Kategorie oder Stichwort suchen"
  - Suche filtert Spiele nach Name, Beschreibung und Kategorie
  - Suche ist case-insensitive

#### 4.1.2 Timer-Widget
- **Dunkler Hintergrund** (dunkelgrau/anthrazit, abgerundete Ecken)
- **Stoppuhr-Icon** (orange/gelb) links
- **Zeitanzeige**: `0:00` Format (Minuten:Sekunden), große weiße Schrift
- **Play/Pause-Button**: Grüner Kreis mit Play-Icon, wechselt zu Pause bei laufendem Timer
- **Reset-Button**: Grauer Kreis mit Reset-Icon
- Timer zählt aufwärts (Stoppuhr-Funktion)
- Timer-Zustand bleibt beim Scrollen erhalten

#### 4.1.3 Kategorie-Filter (Tabs/Chips)
- Drei horizontal angeordnete Filter-Chips:
  1. **"Alle Spiele"** - kein Icon, zeigt alle 30 Spiele
  2. **"Halle"** - Gebäude-Icon, filtert nach Indoor-Spielen
  3. **"Draußen"** - Wind/Outdoor-Icon, filtert nach Outdoor-Spielen
- **Aktiver Tab**: Gefüllter Hintergrund (dunkel bei "Alle", grün bei "Draußen", dunkel bei "Halle")
- **Inaktiver Tab**: Nur Text + Icon, kein Hintergrund
- **Indikator-Linie** unter den Tabs
- Tab-Wechsel mit leichter **Slide-Animation**

#### 4.1.4 "Zufälliges Spiel wählen"-Button
- Volle Breite
- **Orange-zu-Pink Gradient** (von links nach rechts)
- Text: "Zufälliges Spiel wählen" + Würfel-Icon
- Wählt ein zufälliges Spiel aus der aktuellen gefilterten Liste
- Öffnet die Spieldetail-Ansicht

#### 4.1.5 Spiele-Anzahl
- Text: **"X Spiele gefunden"** (z.B. "30 Spiele gefunden")
- Aktualisiert sich dynamisch bei Filter- und Suchänderungen

#### 4.1.6 Spiele-Liste
- Vertikale Liste von **Spiel-Cards**
- Jede Card enthält:
  - **Schwierigkeits-Badge**: Farbiger Chip (z.B. "Mittel" in Orange)
  - **Ort-Icons**: Halle-Icon und/oder Draußen-Icon (zeigt wo das Spiel spielbar ist)
  - **Spielname**: Fett, große Schrift (z.B. "Der blinde Passagier")
  - **Kurzbeschreibung**: Grauer Text, abgeschnitten mit "..." (z.B. "Ein Kletterer klettert blind (oder mit...")
  - **Pfeil-Icon** rechts (Navigation zur Detailansicht)
- Cards haben abgerundete Ecken und leichten Schatten/Elevation

### 4.2 Spieldetail-Ansicht

Wird angezeigt wenn ein Spiel aus der Liste oder "Zufälliges Spiel" gewählt wird:
- **Zurück-Navigation** (Top-Bar oder System-Back)
- **Spielname** als Titel
- **Schwierigkeitsgrad** (Badge)
- **Ort** (Halle / Draußen / Beides)
- **Vollständige Beschreibung** des Spiels
- **Regeln / Anleitung** (falls vorhanden)
- **Favorit-Button** (Herz-Icon, Toggle) - Favoriten-Status wird in Room gespeichert

---

> Weiter zu [Teil 2: Datenmodell, Theming, Animationen, i18n & Projektstruktur](./PRD-02-technical.md)
