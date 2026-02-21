# PRD Teil 2: Datenmodell, Theming, Animationen, i18n & Projektstruktur

> Zurück zu [Teil 1: Übersicht, Stack, Architektur & Features](./PRD-01-features.md)

---

## 5. Datenmodell (Room)

### 5.1 Entity: `Game`

| Feld | Typ | Beschreibung |
|---|---|---|
| `id` | Int (PK) | Auto-generierte ID |
| `nameKey` | String | i18n-String-Key für den Namen |
| `descriptionKey` | String | i18n-String-Key für die Beschreibung |
| `rulesKey` | String? | i18n-String-Key für Regeln (optional) |
| `difficulty` | String | "Leicht", "Mittel", "Schwer" (Enum) |
| `locationIndoor` | Boolean | Spielbar in der Halle |
| `locationOutdoor` | Boolean | Spielbar draußen |
| `isFavorite` | Boolean | Vom Benutzer als Favorit markiert |

### 5.2 DAO: `GameDao`

- `getAllGames(): Flow<List<Game>>`
- `getIndoorGames(): Flow<List<Game>>`
- `getOutdoorGames(): Flow<List<Game>>`
- `searchGames(query: String): Flow<List<Game>>`
- `toggleFavorite(gameId: Int, isFavorite: Boolean)`
- `getGameById(id: Int): Flow<Game>`

### 5.3 Pre-populated Data

Die 30 Spiele werden beim ersten App-Start über eine `RoomDatabase.Callback` in `onCreate` eingefügt. Die Spielinhalte (Namen, Beschreibungen) werden als String-Ressourcen gespeichert (i18n-fähig).

---

## 6. Theming & Design

### 6.1 Farbpalette

| Element | Farbe |
|---|---|
| Header-Gradient | `#5C4DFF` -> `#7B68EE` (Violett) |
| Timer-Hintergrund | `#2D2D3A` (Dunkelgrau) |
| Play-Button | `#4CAF50` (Grün) |
| Reset-Button | `#757575` (Grau) |
| Stoppuhr-Icon | `#FFB300` (Orange/Amber) |
| Random-Button-Gradient | `#FF8C00` -> `#E91E63` (Orange -> Pink) |
| Aktiver Tab "Draußen" | `#4CAF50` (Grün) |
| Aktiver Tab "Halle"/"Alle" | `#3D3D50` (Dunkel) |
| Schwierigkeit "Leicht" | `#4CAF50` (Grün) |
| Schwierigkeit "Mittel" | `#FF9800` (Orange) |
| Schwierigkeit "Schwer" | `#F44336` (Rot) |
| Hintergrund | `#FFFFFF` (Weiß) |
| Kartenfarbe | `#FFFFFF` mit Elevation |
| Text primär | `#1A1A2E` (Fast-Schwarz) |
| Text sekundär | `#757575` (Grau) |

### 6.2 Typografie

- Material 3 Default-Typografie
- Titel: Bold, groß
- Untertitel: Regular, mittel
- Body: Regular, normal

### 6.3 Formen

- Cards: `RoundedCornerShape(16.dp)`
- Chips/Badges: `RoundedCornerShape(24.dp)`
- Suchleiste: `RoundedCornerShape(28.dp)`
- Buttons: `RoundedCornerShape(28.dp)`

---

## 7. Animationen

Leichte, subtile Animationen:

| Animation | Typ | Dauer |
|---|---|---|
| Tab-Wechsel | `animateColorAsState` + `animateContentSize` | 300ms |
| Spiele-Liste Filter | `AnimatedVisibility` (fadeIn/fadeOut) | 250ms |
| Spielanzahl-Update | `AnimatedContent` (Zähler) | 200ms |
| Card-Press | `scale` auf 0.98 beim Drücken | 100ms |
| Detail-Navigation | `slideInHorizontally` / `slideOutHorizontally` | 300ms |
| Favorit-Toggle | `animateScale` (Herz pulsiert) | 200ms |
| Timer-Start | Stoppuhr-Icon dreht kurz | 300ms |

---

## 8. i18n (Internationalisierung)

### Unterstützte Sprachen
- **Deutsch** (Standard/Fallback)
- **Englisch**

### Umsetzung
- Alle UI-Texte in `res/values/strings.xml` (DE) und `res/values-en/strings.xml` (EN)
- Alle 30 Spielnamen und -beschreibungen als String-Ressourcen
- `stringResource(R.string.*)` in allen Composables
- Spielinhalte werden über ihren `nameKey` / `descriptionKey` zur Laufzeit in die aktuelle Sprache aufgelöst

---

## 9. Projektstruktur

```
app/src/main/java/com/gipfelstuermer/
├── GipfelStuermerApp.kt          # Application-Klasse
├── MainActivity.kt               # Single Activity
├── data/
│   ├── local/
│   │   ├── AppDatabase.kt        # Room Database + Callback
│   │   ├── GameDao.kt            # DAO
│   │   └── GameEntity.kt         # Entity
│   └── repository/
│       └── GameRepository.kt     # Repository
├── ui/
│   ├── theme/
│   │   ├── Color.kt              # Farben
│   │   ├── Theme.kt              # Material 3 Theme
│   │   └── Type.kt               # Typografie
│   ├── components/
│   │   ├── HeroCard.kt           # Header mit Suche
│   │   ├── TimerWidget.kt        # Stoppuhr
│   │   ├── CategoryTabs.kt       # Filter-Tabs
│   │   ├── RandomGameButton.kt   # Zufalls-Button
│   │   ├── GameCard.kt           # Einzelne Spiel-Card
│   │   └── GameCountLabel.kt     # "X Spiele gefunden"
│   ├── screens/
│   │   ├── HomeScreen.kt         # Hauptscreen
│   │   └── GameDetailScreen.kt   # Detailansicht
│   └── viewmodel/
│       └── GameViewModel.kt      # ViewModel
└── navigation/
    └── NavGraph.kt               # Navigation
```

```
app/src/main/res/
├── values/
│   ├── strings.xml               # Deutsche Texte (Standard)
│   └── colors.xml
├── values-en/
│   └── strings.xml               # Englische Texte
```

---

## 10. Nicht im Scope

- Kein Login / Authentifizierung
- Kein Backend / API
- Kein Dark Mode (nur Light Theme wie im Screenshot)
- Keine Push-Notifications
- Kein Onboarding
- Kein In-App-Purchase
- Keine Analytics

---

> Zurück zu [Teil 1: Übersicht, Stack, Architektur & Features](./PRD-01-features.md)
