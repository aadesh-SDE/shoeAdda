# ShoeAdda – Tech Stack and Architecture (Phase 2, proposed)

Written from a senior Android developer's view. Status: all open decisions are answered (section 4).

## 1. What the research found

**Confirmed from official sources**
- Google's architecture guide ([Recommendations for Android architecture](https://developer.android.com/topic/architecture/recommendations)): a UI layer and a data layer, repositories as the single source of truth, unidirectional data flow, a ViewModel that exposes one `uiState` as a `StateFlow`, a domain layer only when logic is reused across ViewModels, and Jetpack Compose as the UI toolkit. Google's Now in Android sample app follows it.
- [Core app quality guidelines](https://developer.android.com/docs/quality-guidelines/archive/core/core-app-quality-2026-03-20) (March 2026): standard back navigation, gesture navigation, target the latest SDK, watch Android Vitals after release.
- [Android 16 behavior changes](https://developer.android.com/about/versions/16/behavior-changes-16): for apps targeting API 36, edge-to-edge cannot be turned off, predictive back animations are on by default, and orientation and resizability limits are ignored on large screens.
- [AndroidX release pages](https://developer.android.com/jetpack/androidx/versions/stable-channel): Navigation 3 is stable (1.1.x), Navigation 2 is 2.9.x. Room 3 is only a release candidate, so we use Room 2.x for now.
- Hilt is Google's recommended dependency injection for Android. Koin is the usual choice when code is shared with other platforms (community articles, not official).

**Not found (treat as unknown)**
- Uber: the open-source [RIBs](https://github.com/uber/RIBs) framework exists, but there is no official Uber post on its Android stack. Third-party sites say it is mostly XML Views with Compose added step by step. Unverified.
- Blinkit: its blog only covers React Native (older posts). Nothing found on its current Android stack.
- Zepto: nothing found.
- Nike, Myntra, Flipkart: no official Compose case studies found.

**What we take from this**
Large apps such as Uber use heavy custom architectures (RIBs, server-driven UI) because of team size and the need to ship UI changes without app updates. ShoeAdda is a small app with no backend, so Google's standard architecture is the right fit. RIBs and server-driven UI would be over-engineering.

## 2. Recommended stack

| Area | Choice | Why |
|---|---|---|
| Language | Kotlin only | Google's default |
| UI | Jetpack Compose + Material 3 (strict) | Matches the M3 decision |
| Architecture | MVVM with unidirectional data flow: UI, ViewModel (`StateFlow<UiState>`), repository, data sources. Every feature has its own data, domain and presentation layers (user decision, 2026-11-05; see `FOLDER_STRUCTURE.md`). Note: Google calls the domain layer optional, but we use it in every feature on purpose | Google's guide |
| Navigation | Navigation 3 | Stable and current |
| Dependency injection | Hilt | Google's recommendation, fits an Android-only app |
| Local data | Room 2.x for user, cart, wishlist, orders, reviews and the product cache | Already decided |
| Settings | Preferences DataStore for language, theme, onboarding and login flag | Replaces SharedPreferences |
| Network | Retrofit + OkHttp + kotlinx.serialization, for DummyJSON only | Mature and simple |
| Images | Coil 3 | Built for Compose |
| Async | Kotlin Coroutines and Flow | Standard |
| Languages | Per-app language (`AppCompatDelegate.setApplicationLocales`) with `values-hi`, `values-mr`, `values-gu`, `values-ta`, `values-te` | Official per-app language support |
| Fonts | Barlow Condensed and Archivo in `res/font`, Noto Sans scripts for Indian languages | Matches the design |
| Mock data | `assets/products.json` (30 to 50 shoes) merged with DummyJSON into Room | Already decided |
| Testing | JUnit, Turbine, Compose UI tests, Room in-memory tests | Standard |
| Build | Gradle Kotlin DSL, version catalog (`libs.versions.toml`), KSP | Standard |
| Quality | Baseline Profiles, R8, Android Vitals, edge-to-edge, predictive back, large-screen support | Core quality guidelines |

## 3. Project structure

One `app` module, organised by feature. Every feature has its own `data`, `domain` and `presentation` packages. A small `core` holds shared pieces (theme, Room database, network and DataStore setup). Split into Gradle modules later only if build time or team size requires it. The full layout is in `FOLDER_STRUCTURE.md`.

```
app/
  core/        designsystem, database, network, datastore, di, util
  feature/
    start/  auth/  catalog/  home/  categories/  search/  productlist/
    productdetail/  wishlist/  cart/  checkout/  orders/  profile/
      each with: data/  domain/  presentation/
```

## 4. Decisions (confirmed by the user, 2026-11-04)
1. Minimum Android version: Android 8.0 (API 26).
2. Dependency injection: Hilt.
3. Navigation: Navigation 3.
4. Firebase: none in version 1 (add Crashlytics before a public release).
5. Tablets and foldables: phone first. Layouts must not break when resized, but there are no tablet designs in version 1.

Before the Android project is created, re-check exact library versions on the AndroidX release pages.
