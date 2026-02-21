# GipfelStürmer

Android Kotlin App - 30 Kletterspiele für Kinder.

## PRD

- [PRD Übersicht](./docs/PRD.md)
  - [Teil 1: Übersicht, Stack, Architektur & Features](./docs/PRD-01-features.md)
  - [Teil 2: Datenmodell, Theming, Animationen, i18n & Projektstruktur](./docs/PRD-02-technical.md)

## Screenshots

- [Screenshot 1 - Alle Spiele](./docs/Screenshot_20260211_144148_Chrome.jpg)
- [Screenshot 2 - Halle](./docs/Screenshot_20260211_144310_Chrome.jpg)
- [Screenshot 3 - Draußen](./docs/Screenshot_20260211_144323_Chrome.jpg)

## Tech-Stack

- Kotlin 2.3.10 / AGP 9.0.0 / Gradle 9.3.1
- Compose BOM 2026.01.01 / Material 3 1.4.0
- Room 2.8.4 / Navigation Compose 2.9.7
- compileSdk 36 (Android 16) / minSdk 26

## Architektur

- Single-Activity, Compose-first, MVVM + UDF
- Lokaler Speicher mit Room (pre-populated, 30 Spiele)
- i18n: Deutsch (Standard) + Englisch
- Kein API, kein Login, kein Dark Mode
