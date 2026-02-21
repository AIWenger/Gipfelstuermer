# PRD Teil 3: Erweiterung – 100 Spiele, Altersgruppen & Spieltypen

## 1. Ziel

Die App wird von 30 auf 100 Spiele erweitert. Neue Klassifizierungsdimensionen
(Altersgruppe + Spieltyp) ermöglichen gezieltes Filtern für Trainer und Eltern.

---

## 2. Neue Features

### 2.1 Altersgruppen
| Enum-Wert | Label DE | Label EN | Zielgruppe |
|---|---|---|---|
| `KLEIN` | Klein | Young | 4–6 Jahre |
| `MITTEL` | Mittel | Middle | 7–10 Jahre |
| `GROSS` | Groß | Older | 11+ Jahre |

### 2.2 Spieltypen
| Enum-Wert | Label DE | Label EN | Beschreibung |
|---|---|---|---|
| `KLETTERN` | Klettern | Climbing | Kletteraktivitäten an der Wand |
| `AUFWAERMEN` | Aufwärmen | Warm-Up | Bewegungs- und Aufwärmspiele |
| `KRAFT` | Kraft | Strength | Kraftübungen & Kondition |

### 2.3 Spielanzahl
- **Gesamt:** 100 Spiele (30 bestehend + 70 neu)
- Verteilung: Klein 20 / Mittel 23 / Groß 27 (inkl. bestehende)

---

## 3. Datenmodell-Änderungen

### 3.1 Neue Enums (neue Dateien)
```
data/local/AgeGroup.kt   → enum class AgeGroup { KLEIN, MITTEL, GROSS }
data/local/GameType.kt   → enum class GameType { KLETTERN, AUFWAERMEN, KRAFT }
```

### 3.2 GameEntity – neue Felder
```kotlin
val ageGroup: AgeGroup = AgeGroup.MITTEL,
val gameType: GameType = GameType.KLETTERN,
```

### 3.3 EnumConverters (ersetzt DifficultyConverter)
```
data/local/EnumConverters.kt
```
TypeConverters für Difficulty, AgeGroup und GameType (String ↔ Enum).

### 3.4 AppDatabase
- `version = 1` → `version = 2`
- `@TypeConverters(EnumConverters::class)`
- `fallbackToDestructiveMigration()` (kein Migrations-SQL nötig)
- `populateDatabase()`: 100 Einträge mit allen 5 Feldern

---

## 4. Filter-Logik (ViewModel)

### 4.1 Neue Enums
```kotlin
enum class AgeFilter  { ALL_AGES, KLEIN, MITTEL, GROSS }
enum class TypeFilter { ALL_TYPES, KLETTERN, AUFWAERMEN, KRAFT }
```

### 4.2 StateFlows
```kotlin
private val _ageFilter  = MutableStateFlow(AgeFilter.ALL_AGES)
private val _typeFilter = MutableStateFlow(TypeFilter.ALL_TYPES)
```

### 4.3 combine() mit 5 Flows
```kotlin
filteredGames = combine(
    repository.getAllGames(),
    _selectedFilter,   // LocationFilter
    _ageFilter,
    _typeFilter,
    _searchQuery
) { allGames, loc, age, type, query -> … }
```
Filterreihenfolge: Location → Age → Type → Suche.

---

## 5. UI-Änderungen

### 5.1 CategoryTabs – 3 Filter-Zeilen
```
Zeile 1: [Alle Spiele]  [Halle]  [Draußen]          ← bestehend
Zeile 2: [Alle] [Klein] [Mittel] [Groß]              ← neu, Farbe je AgeGroup
Zeile 3: [Alle] [Klettern] [Aufwärmen] [Kraft]       ← neu, Farbe je GameType
──────────────────────────────────────────────────── HorizontalDivider
```

Neue Parameter in `CategoryTabs`:
- `ageFilter: AgeFilter`, `onAgeFilterSelected: (AgeFilter) -> Unit`
- `typeFilter: TypeFilter`, `onTypeFilterSelected: (TypeFilter) -> Unit`

### 5.2 GameCard – GameType-Badge
Zusätzlicher Badge neben dem Difficulty-Badge:
- Farbe: `GameTypeKlettern` / `GameTypeAufwaermen` / `GameTypeKraft`
- Text: `type_klettern` / `type_aufwaermen` / `type_kraft`

### 5.3 GameDetailScreen – neue Badge-Row
Unter dem Difficulty-Badge: Altersgruppe-Badge + Spieltyp-Badge.

---

## 6. Farb-Token (Color.kt)

| Token | Hex | Verwendung |
|---|---|---|
| `AgeGroupKlein` | `#29B6F6` | Hellblau, Filter-Chip + Badge |
| `AgeGroupMittel` | `#66BB6A` | Grün, Filter-Chip + Badge |
| `AgeGroupGross` | `#8D6E63` | Braun, Filter-Chip + Badge |
| `GameTypeKlettern` | `#5C4DFF` | Lila (Brand), Filter-Chip + Badge |
| `GameTypeAufwaermen` | `#FF9800` | Orange, Filter-Chip + Badge |
| `GameTypeKraft` | `#E53935` | Rot, Filter-Chip + Badge |

---

## 7. i18n – neue String-Keys

| Key | DE | EN |
|---|---|---|
| `app_subtitle` | 100 Spiele für kleine Kletterer | 100 Games for Little Climbers |
| `age_all` | Alle | All |
| `age_klein` | Klein | Young |
| `age_mittel` | Mittel | Middle |
| `age_gross` | Groß | Older |
| `age_klein_label` | 4–6 Jahre | Ages 4–6 |
| `age_mittel_label` | 7–10 Jahre | Ages 7–10 |
| `age_gross_label` | 11+ Jahre | Ages 11+ |
| `type_all` | Alle | All |
| `type_klettern` | Klettern | Climbing |
| `type_aufwaermen` | Aufwärmen | Warm-Up |
| `type_kraft` | Kraft | Strength |
| `age_group` | Altersgruppe | Age Group |
| `game_type` | Spieltyp | Game Type |

Spiel-Strings: `game_31_name` … `game_100_rules` (je 3 Keys × 70 Spiele = 210).

---

## 8. Implementierungsreihenfolge

1. PRD-Dateien → 2. Enums → 3. EnumConverters → 4. GameEntity
5. Color.kt → 6. strings.xml DE → 7. strings.xml EN
8. AppDatabase → 9. ViewModel → 10. CategoryTabs
11. GameCard → 12. HomeScreen → 13. GameDetailScreen → 14. Build & Test

---

## 9. Testkriterien

- Homescreen zeigt „100 Spiele gefunden"
- Filter Klein zeigt genau 20 Spiele
- Filter Klein + Kraft zeigt genau 5 Spiele
- Filter Klettern zeigt genau 46 Spiele
- Suche „Frosch" findet Froschsprung (31) und Frosch-Sprungkraft (50)
- GameCard zeigt GameType-Badge
- Detail-Screen: Altersgruppe + Spieltyp sichtbar
- Systemsprache EN: alle Labels auf Englisch
