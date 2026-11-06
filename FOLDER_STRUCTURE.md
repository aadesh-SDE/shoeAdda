# ShoeAdda – Folder Structure (proposed)

Based on `TECH_STACK.md`: Kotlin, Jetpack Compose with strict Material 3, MVVM with unidirectional data flow, Hilt, Navigation 3, Room 2.x, DataStore, Retrofit, Coil 3.

**Architecture: feature-first, and every feature has its own `data`, `domain` and `presentation` layers** (decided by the user, 2026-11-05). A small `core` holds only what several features share.

The package name `com.shoeadda.app` is a placeholder. Change it before the project is created.

## 1. Repository (top level)

```
ShoeAdda/
├── FEATURES.md              features, decisions, user journey
├── SCREENS.md               44-screen inventory
├── TECH_STACK.md            tech stack and architecture
├── FOLDER_STRUCTURE.md      this file
├── README.md                (to add) what the project is, how to build
├── .gitignore               (to add) Android and Gradle ignores
├── design/                  Phase 1 design files (not part of the build)
│   ├── canvas/              88 screen files + canvas.json
│   ├── brand/               logo, color and typography pages
│   └── pdf/                 PDF export of every screen (light, dark)
├── app/                     the Android app module (section 2)
├── gradle/
│   └── libs.versions.toml   version catalog, every library version in one place
├── build.gradle.kts         root build file
├── settings.gradle.kts
└── gradle.properties
```

## 2. App module

```
app/
├── build.gradle.kts
├── proguard-rules.pro
└── src/
    ├── main/
    │   ├── AndroidManifest.xml
    │   ├── assets/
    │   │   └── products.json            30 to 50 local shoes (merged with DummyJSON)
    │   ├── java/com/shoeadda/app/       (section 3)
    │   └── res/
    │       ├── font/                    Barlow Condensed, Archivo (+ Noto Sans scripts)
    │       ├── drawable/                vector icons, logo
    │       ├── mipmap-*/                launcher icon (adaptive)
    │       ├── values/                  strings.xml (English), themes.xml
    │       ├── values-hi/ -mr/ -gu/ -ta/ -te/    Hindi, Marathi, Gujarati, Tamil, Telugu strings
    │       └── xml/locales_config.xml   per-app language list
    ├── test/                            unit tests, mirrors the feature folders
    ├── androidTest/                     Room and Compose UI tests
    └── baselineProfiles/                (later) startup performance
```

## 3. Source packages

```
com.shoeadda.app/
├── ShoeAddaApp.kt                 Application class (@HiltAndroidApp)
├── MainActivity.kt                single activity, hosts the Compose app
│
├── navigation/                    Routes.kt, AppNavDisplay.kt (Navigation 3), BottomTabs.kt
│
├── core/                          shared by several features, no screens here
│   ├── designsystem/              Cocoa and Mist theme (Color, Type, Shape, Theme)
│   │   └── components/            shared UI: ProductCard, PriceText, SizeChip, EmptyState, ...
│   ├── database/                  AppDatabase (Room) that registers the entities of all features
│   ├── network/                   OkHttp and Retrofit setup
│   ├── datastore/                 DataStore setup (language, theme, onboarding, login flag)
│   ├── di/                        Hilt modules for the above
│   └── util/                      ₹ formatter, WhatsApp share, locale helper, Result and error types
│
└── feature/
    ├── start/        splash, language, intro
    ├── auth/         login, OTP
    ├── catalog/      products: DummyJSON + products.json (data and domain only, no screens)
    ├── home/
    ├── categories/
    ├── search/
    ├── productlist/  list, filter sheet, sort sheet
    ├── productdetail/  detail, image viewer, size guide, reviews, share
    ├── wishlist/
    ├── cart/         cart, coupon sheet
    ├── checkout/     address, payment, processing, order confirmed
    ├── orders/       orders, order detail, cancel, exchange and return
    └── profile/      profile, edit profile, saved addresses, notifications, help
```

### Inside every feature

```
feature/cart/
├── data/
│   ├── local/             CartDao, CartEntity (Room)
│   ├── remote/            API service and DTOs (only if the feature calls a network API)
│   ├── mapper/            Entity or DTO  <->  domain model
│   └── repository/        CartRepositoryImpl
├── domain/
│   ├── model/             CartItem, PriceSummary (plain Kotlin, no Android or Room classes)
│   ├── repository/        CartRepository (interface)
│   └── usecase/           AddToCartUseCase, ApplyCouponUseCase, CalculatePriceUseCase, ...
├── presentation/
│   ├── CartScreen.kt      composable, UI only
│   ├── CartViewModel.kt   exposes StateFlow<CartUiState>, receives actions
│   ├── CartUiState.kt     UI state, user actions and one-off events
│   └── components/        composables used only by this feature
└── di/
    └── CartModule.kt      Hilt: binds CartRepositoryImpl to CartRepository
```

Rules for the inside of a feature:
- Create only the folders a feature needs. A feature with no network call has no `remote/`. A feature with no storage and no API (for example `home`) may have only `domain/` and `presentation/`.
- `domain/` is pure Kotlin. It imports no Android, Room, Retrofit or Compose classes.
- Models are mapped at the boundary: Entity or DTO → domain model (in `data/mapper`) → UI state (in the ViewModel).

## 4. What each feature owns

| Feature | data | domain | presentation |
|---|---|---|---|
| `start` | language and onboarding flag (DataStore) | `SetLanguage`, `CompleteOnboarding` | Splash, Language, Intro |
| `auth` | user table (Room), mock OTP | `SendOtp`, `VerifyOtp` (accepts 123456), `Logout`, `ObserveSession` | Login, OTP |
| `catalog` | DummyJSON API, `products.json`, product table, USD to INR | `Shoe` model, `ProductRepository`, `RefreshCatalog`, `SearchAndFilterShoes`, `GetShoe` | none |
| `home` | none (uses `catalog`) | `GetHomeSections` | Home |
| `categories` | none (uses `catalog`) | `GetCategories` | Categories |
| `search` | recent searches table | `SearchShoes`, `SaveRecentSearch` | Search, No results |
| `productlist` | none (uses `catalog`) | `FilterSortShoes` (size, brand, color, price, width, activity, sort) | List, Filter sheet, Sort sheet |
| `productdetail` | reviews table, stock-alert table | `GetShoeDetail`, `GetReviews`, `AddReview`, `NotifyWhenInStock`, `BuildShareText` | Detail, Image viewer, Size guide, Reviews, Write review |
| `wishlist` | wishlist table | `ToggleWishlist`, `ObserveWishlist` | Wishlist |
| `cart` | cart table | `AddToCart`, `UpdateQuantity`, `ApplyCoupon`, `CalculatePrice` | Cart, Coupon sheet |
| `checkout` | address table, mock payment | `SaveAddress`, `PlaceOrder`, `MockPayment` (2 second delay, always succeeds) | Address, Add address, Payment, Processing, Order confirmed |
| `orders` | order table, status timer | `ObserveOrders`, `AdvanceOrderStatus`, `CancelOrder`, `RequestExchangeReturn` | Orders, Order detail, Cancel, Exchange and return |
| `profile` | profile table, notification table | `UpdateProfile`, `ObserveNotifications`, `ManageAddresses` | Profile, Edit profile, Saved addresses, Notifications, Help |

Full screen list per feature is in `SCREENS.md`.

## 5. Dependency rules

```
presentation  ->  domain  <-  data
```

- **presentation** depends on `domain` only. It never touches Room or Retrofit.
- **data** depends on `domain` (it implements the repository interfaces) and on `core`.
- **domain** depends on nothing else.
- **Between features:** a feature may use another feature's `domain` (interfaces, models and use cases), never its `data` or `presentation`. Example: `cart` and `wishlist` read shoes through `catalog.domain`. This keeps features independent.
- **core** never depends on a feature. `core/database` lists the Room entities and DAOs of all features, and each feature's `di/` module provides its own DAO from it.
- **navigation/** is the only place that knows every screen. Features expose their screens and the app wires them together.
- **Strings** are never hard-coded in composables. Use `stringResource` so the six languages work.

## 6. Tests

```
test/.../feature/cart/domain/       use case tests (pure Kotlin, fast)
test/.../feature/cart/presentation/ ViewModel tests (Turbine, fake repository)
androidTest/.../feature/cart/data/  Room DAO tests (in-memory database)
androidTest/.../feature/cart/presentation/   Compose UI tests
```

## 7. Build progress

| Feature | Status |
|---|---|
| core (theme, fonts, languages, navigation shell) | done |
| `start` (system splash, language, intro) | done, built but not tested; it is the template for the others |
| `catalog` (59 shoes: 10 from DummyJSON, 49 bundled with photos) | done, built but not tested; has no screens yet |
| `auth`, `home`, `categories`, `search`, `productlist` | not started |
| `productdetail`, `wishlist`, `cart`, `checkout`, `orders`, `profile` | not started |

The `start` feature settled these details for the others to copy:
- A feature that needs the operating system (here the language switch) defines an interface in `domain/repository` and implements it in `data` (for example `LocaleController` and `AndroidLocaleController`).
- Use cases are plain classes with `@Inject constructor` and `operator fun invoke`.
- Each screen has a `ViewModel` that exposes `StateFlow<UiState>` and one-off events through a `Channel`.
- `ViewModel`s are created with `hiltViewModel()` from `androidx.hilt.lifecycle.viewmodel.compose` and are tied to the Navigation 3 screen.
- Small settings are stored in DataStore, behind a data source class inside the feature's `data/local`.

## 8. When to split into modules

Stay with the single `app` module for version 1. Because each feature already has its own layers and depends only on other features' `domain`, any `feature/*` folder can later become a Gradle module (`:feature:cart`) with few changes. Do the split only if build times get slow or more developers join.
