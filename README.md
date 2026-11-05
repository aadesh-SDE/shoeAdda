# ShoeAdda

An Android shoe shopping app. Users can browse shoes by category, brand and size, add to cart and wishlist, check out, track orders and share a shoe on WhatsApp. The app has no backend: products come from the free DummyJSON API plus a bundled local file, and login and payment are mocked.

Tagline (placeholder): *Walk your way*

## Status
- Phase 1, design: done. 44 screens in light and dark mode, in `design/`.
- Phase 2, development: starting. The Android project has not been created yet.

## Tech stack
Kotlin, Jetpack Compose with strict Material 3, MVVM with unidirectional data flow, Hilt, Navigation 3, Room, DataStore, Retrofit, Coil. Minimum Android version: 8.0 (API 26). Application ID: `com.shoeadda.app`.

## Documents
| File | What it covers |
|---|---|
| [FEATURES.md](FEATURES.md) | Features, decisions, user journey |
| [SCREENS.md](SCREENS.md) | The 44 screens |
| [TECH_STACK.md](TECH_STACK.md) | Libraries and architecture, with reasons |
| [FOLDER_STRUCTURE.md](FOLDER_STRUCTURE.md) | Feature-first layout (data, domain, presentation in every feature) |
| [CONTRIBUTING.md](CONTRIBUTING.md) | Branches, commits and pull requests |
| `design/` | Design files: `canvas/` (screens), `brand/` (logo, colors, fonts), `pdf/` (PDF export) |

## Building
Requirements: Android Studio (stable) with JDK 17 or newer and the Android SDK.
Build instructions will be added when the Android project is created.
