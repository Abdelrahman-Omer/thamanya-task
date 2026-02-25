# Thamanya — Project Overview & Technical Documentation

> A modern Android audio/podcast content platform built with Jetpack Compose and Clean Architecture.

---

## Table of Contents

1. [Project Summary](#1-project-summary)
2. [Tech Stack](#2-tech-stack)
3. [Project Structure](#3-project-structure)
4. [Architecture](#4-architecture)
5. [Screens & User Experience](#5-screens--user-experience)
6. [Animations & Motion Design](#6-animations--motion-design)
7. [State Management & Data Flow](#7-state-management--data-flow)
8. [Media Playback System](#8-media-playback-system)
9. [Network & Data Layer](#9-network--data-layer)
10. [Design System](#10-design-system)
11. [Dependency Injection](#11-dependency-injection)
12. [Code Quality & Build System](#12-code-quality--build-system)
13. [Potential Improvements](#13-potential-improvements)

---

## 1. Project Summary

**Thamanya** is an Arabic-language mobile application for discovering and consuming audio content — including podcasts, audiobooks, and articles. The app features a rich content feed with category-based filtering, a real-time search, and a full-featured collapsible audio player.

The project is built as a **multi-module Android application** using **Jetpack Compose** for the UI layer and follows a strict **Clean Architecture + MVI** pattern, making it scalable, testable, and maintainable.

---

## 2. Tech Stack

| Category | Technology |
|---|---|
| **Language** | Kotlin |
| **UI Framework** | Jetpack Compose (Material 3) |
| **Architecture** | Clean Architecture + MVVM |
| **Navigation** | Jetpack Navigation Compose |
| **Networking** | Ktor Client (OkHttp engine) |
| **Serialization** | Kotlinx Serialization (JSON) |
| **Dependency Injection** | Koin |
| **Media Playback** | ExoPlayer (AndroidX Media3) |
| **Image Loading** | Coil |
| **Local Storage** | DataStore Preferences |
| **Async / Reactive** | Kotlin Coroutines + Flow |
| **Lifecycle** | Jetpack Lifecycle (compose-aware) |
| **Code Analysis** | Detekt, Ktlint, Spotless |
| **Documentation** | Dokka |
| **Build System** | Gradle (Kotlin DSL) + KSP |

---

## 3. Project Structure

The project follows a **multi-module Gradle setup**, separating concerns across `core` modules and `feature` modules. This enables independent builds, strict layer separation, and future scalability.

```
thamanya-task-main/
│
├── app/                          # Application shell
│   ├── MainActivity.kt           # Single-activity entry point
│   └── ThamanyaApplication.kt    # Koin initialization
│
├── core/                         # Shared infrastructure
│   ├── data/                     # HTTP client, DataStore, interceptors
│   ├── domain/                   # Base Result type, Error types, exceptions
│   ├── presentation/             # Shared Composables, navigation routes, UI utils
│   ├── designsystem/             # Color palette, typography, Material 3 theme
│   ├── di/                       # Koin module aggregation
│   └── utils/                    # Extensions, MediaPlayer wrapper, time utilities
│
├── features/
│   └── home/                     # Self-contained Home feature
│       ├── data/                 # DTOs, mappers, network source, repository impl
│       ├── domain/               # Repository interface, domain models, Chip model
│       └── presentation/         # ViewModel, State, Actions, Composables, Components
│
└── buildSrc/                     # Centralized Gradle build logic & dependency versions
```

### Module Dependency Graph

```
app
 └── features/home
      ├── core/data
      ├── core/domain
      ├── core/presentation
      ├── core/designsystem
      ├── core/di
      └── core/utils
```

Each feature module owns its full vertical slice (data → domain → presentation) and depends only on shared `core` modules, never on other feature modules. This enforces clean boundaries and makes modules independently testable.

---

## 4. Architecture

### Clean Architecture Layers

```
┌─────────────────────────────────┐
│        Presentation Layer        │
│   Composables · ViewModel       │
│   StateFlow · HomeActions       │
├─────────────────────────────────┤
│          Domain Layer            │
│   Repository Interfaces         │
│   Domain Models · Use Cases     │
├─────────────────────────────────┤
│           Data Layer             │
│   DTOs · Mappers · Network      │
│   Repository Implementations    │
└─────────────────────────────────┘
```

### MVI with Unidirectional Data Flow (UDF)

The presentation layer strictly follows **Unidirectional Data Flow**:

```
User Interaction
      │
      ▼
HomeActions (sealed interface)
      │
      ▼
HomeViewModel.onAction()
      │
      ▼
MutableStateFlow<HomeState> updated
      │
      ▼
StateFlow<HomeState> emitted
      │
      ▼
HomeScreen collects via collectAsStateWithLifecycle()
      │
      ▼
UI Recomposition
```

This makes the data flow predictable, debuggable, and fully testable — the ViewModel can be unit tested in isolation without any Android dependencies.

### Result Type — Functional Error Handling

Instead of throwing exceptions across boundaries, the project uses a custom functional `Result` type:

```kotlin
sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : Error>(val error: E) : Result<Nothing, E>
}
```

This ensures every call site is forced to handle both success and failure explicitly, eliminating silent failures.

---

## 5. Screens & User Experience

### Home Screen

The single screen in the current scope is the **Home Feed**, composed of several distinct UI regions:

#### 5.1 Header — `UserAndNotificationHeader`

- Displays a circular user profile indicator (green background)
- Shows a notification bell with an unread badge count
- Contains a **toggleable search field** — tapping the search icon smoothly reveals an inline search bar
- Fully RTL-aware for Arabic layout

#### 5.2 Category Filter Chips — `HomeCategoryChips`

A horizontal scrollable row of filter chips:

| Chip | Arabic | Meaning |
|---|---|---|
| All | الجميع | Show everything |
| For You | لك | Personalized feed |
| Podcasts | البودكاست | Podcast content |
| Audio | الصوتية | Audiobook content |
| Articles | المقالات | Article content |
| Books | الكتب | Book content |

- Single-selection with animated state transitions
- Selecting a chip filters the content feed below it
- Selecting "All" reloads the default home sections

#### 5.3 Content Feed — Sectioned LazyColumn

Content is organized into **typed sections**, each rendered with a layout appropriate to its content type:

| Section Type | Component | Layout |
|---|---|---|
| Large featured items | `BigSquareList` | Horizontal large cards |
| Featured audio | `BigSquareAudioList` | Horizontal cards with audio metadata |
| Compact grid items | `SquareList` | Horizontal compact cards |
| Double-row grid | `TwoLinesGridList` | Two-row horizontal grid |

Each section has a title and ordered content list fetched from the API.

#### 5.4 Loading State — Shimmer Placeholders

While data is loading, the screen displays animated shimmer placeholder items that mirror the layout of real content, providing a polished perceived-performance experience.

#### 5.5 Audio Player — Collapsible Bar

When a track is selected, a player bar animates into view from the bottom of the screen. It supports two states:

**Collapsed (Mini Player):**
- Thumbnail, track title, artist/podcast name
- Play/Pause button
- Progress indicator

**Expanded (Full Screen Player):**
- Large album art
- Track title and podcast name
- Progress slider with current/total time
- Play/Pause button
- 15-second seek forward and backward buttons
- Collapse button to return to mini player

#### 5.6 Search

- Triggered by the search icon in the header
- Debounced (400ms) to avoid firing requests on every keystroke
- Live results replace section content in the same LazyColumn
- Clearing search or deselecting restores the original home feed

#### 5.7 Toast Notifications

- Custom animated toast system (not Android's native Toast)
- Appears from the top of the screen with fade + slide animation
- Used for error messages and playback warnings
- Configurable colors (error red, success green)

---

## 6. Animations & Motion Design

The app uses Compose's animation APIs deliberately and consistently throughout.

### 6.1 Shimmer Loading Effect

**File:** `core/presentation/components/shimmerEffect.kt`

An infinite animated gradient that sweeps across placeholder elements while content loads.

- **Type:** Infinite repeatable with `LinearEasing`
- **Duration:** 1000ms per cycle
- **Color sweep:** `#FFB8B5B5 → #FF8F8B8B → #FFB8B5B5`
- **Applied via:** a reusable `Modifier.shimmerEffect()` extension

### 6.2 Audio Player Entry / Exit

**File:** `HomeScreen.kt`

The entire player is wrapped in `AnimatedVisibility` tied to whether a track is selected.

- **Entry:** `slideInVertically(from bottom) + fadeIn`
- **Exit:** `slideOutVertically(to bottom) + fadeOut`
- **Trigger:** `selectedTrack != null`

### 6.3 Player Expand / Collapse

**File:** `core/presentation/components/CollapsibleAudioPlayerBar.kt`

The transition between mini player and fullscreen player uses **spring-based offset animation**:

- **Type:** `animateFloatAsState` driving Y-offset
- **Spring:** `DampingRatioLowBouncy + StiffnessLow`
- **Feel:** Natural, elastic expand/collapse

### 6.4 Player Content Crossfade

Inside the player, `AnimatedContent` handles the crossfade between collapsed and expanded content:

- Prevents jarring layout jumps when expanding/collapsing
- Custom animation spec aligned with the outer spring

### 6.5 Chip Selection Animation

**File:** `core/presentation/components/AnimatedCustomChip.kt`

- **Type:** Animated background color + border color transitions
- **Trigger:** Selection state change
- **Behavior:** The selected chip smoothly highlights; deselected chips dim

### 6.6 Content Size Changes

Some list items use `Modifier.animateContentSize()` to smoothly handle layout size changes without abrupt recomposition jumps.

### 6.7 Toast Notification Animation

**File:** `core/presentation/components/custom_toast/ToastHost.kt`

- **Entry:** `fadeIn + slideInVertically` from top (`-it`)
- **Exit:** `fadeOut + slideOutVertically` to top
- **Duration:** 300ms tween
- **Position:** `TopCenter` of screen

---

## 7. State Management & Data Flow

### HomeState

All UI state for the home screen lives in a single immutable data class:

```kotlin
data class HomeState(
    val isLoading: Boolean = false,
    val isSearchActive: Boolean = false,
    val searchQuery: String = "",
    val isPlayerExpanded: Boolean = false,
    val isPlaying: Boolean = false,
    val progress: Float = 0f,
    val selectedTrack: AudioTrack? = null,
    val sections: List<Section> = emptyList(),
    val categories: List<Chip> = emptyList(),
    val errorMessage: UiText? = null
)
```

The ViewModel holds a `MutableStateFlow<HomeState>` and exposes it as a read-only `StateFlow<HomeState>`. All updates go through `state.update { it.copy(...) }` — immutable and thread-safe.

### HomeActions — Complete Action Surface

```kotlin
sealed interface HomeActions {
    data class OnCategorySelected(val category: Chip) : HomeActions
    data class OnPlayerExpandChange(val isExpanded: Boolean) : HomeActions
    data class OnTrackPlaying(val isPlaying: Boolean) : HomeActions
    data class OnProgressChange(val progress: Float) : HomeActions
    data class OnTrackSelected(val track: Content) : HomeActions
    data object OnSeekForward : HomeActions
    data object OnSeekBackward : HomeActions
    data object OnClosePlayer : HomeActions
    data object OnSearchToggle : HomeActions
    data class OnSearchQueryChanged(val query: String) : HomeActions
    data object OnClearError : HomeActions
}
```

Having all actions as a sealed interface means the ViewModel's `onAction()` is the single point of entry for all UI events — making the entire interaction surface auditable in one place.

### Search Debouncing

```
User types character
       │
       ▼
OnSearchQueryChanged emitted
       │
       ▼
StateFlow map + debounce(400ms)
       │
       ▼
Repository.searchHomePage(query) called
       │
       ▼
Results update HomeState.sections
```

The 400ms debounce prevents unnecessary API calls on every keystroke.

---

## 8. Media Playback System

### Architecture

The media layer is encapsulated behind a custom `MediaPlayer` class that wraps **ExoPlayer (AndroidX Media3)**, preventing ExoPlayer APIs from leaking into the ViewModel or UI.

### Player State Machine

```
IDLE ──────────► PLAYING
  ▲                │
  │                ▼
ENDED ◄────── PAUSED
```

### PlayerState Flow

```kotlin
data class PlayerState(
    val current: MediaPlayerState,  // IDLE | PLAYING | PAUSED | ENDED
    val totalDuration: Long,         // in milliseconds
    val currentPosition: Long        // in milliseconds
)
```

Emitted as a `Flow<PlayerState>` that the ViewModel collects to keep the UI progress bar in sync.

### Position Tracking

A `Handler`-based loop fires every **100ms** while playing, emitting position updates to the flow. This gives smooth, real-time progress bar movement without excessive CPU usage.

### Key Capabilities

| Feature | Implementation |
|---|---|
| Play / Pause | `resume()` / `pause()` |
| Seek Forward 15s | `seekForward(15_000)` |
| Seek Backward 15s | `seekBackward(15_000)` |
| Seek to position | `seekTo(position)` with bounds check |
| Playback speed | `setPlaybackSpeed(Float)` |
| Cleanup | `release()` called on ViewModel cleared |

---

## 9. Network & Data Layer

### HTTP Client — Ktor + OkHttp

Configured in `HttpClientFactory.kt`:

- **Engine:** OkHttp (production-grade, connection pooling)
- **Timeout:** 5 seconds socket + request timeout
- **Serialization:** Kotlinx JSON (ignores unknown keys — forward-compatible)
- **Authorization:** Bearer token injected from DataStore on every request
- **Logging:** Full request/response logging in debug builds

### Safe API Call Wrapper

All network calls go through a centralized `safeCall {}` extension that catches exceptions and maps them to typed errors:

| Exception / HTTP Code | Mapped Error |
|---|---|
| `SocketTimeoutException` | `REQUEST_TIMEOUT` |
| `UnresolvedAddressException` | `NO_INTERNET` |
| HTTP 401 | `UNAUTHORIZED` |
| HTTP 408 | `REQUEST_TIMEOUT` |
| HTTP 429 | `TOO_MANY_REQUESTS` |
| HTTP 5xx | `SERVER` |
| Other | `UNKNOWN` |

### API Endpoints

| Endpoint | Purpose |
|---|---|
| `HOME_SECTION_BASE_URL/home_sections` | Fetch home feed sections |
| `HOME_SECTION_SEARCH_BASE_URL/search?q={query}` | Search content |

### Data Flow — Network to UI

```
API JSON Response
       │
       ▼
Kotlinx Deserialization → HomeResponseDto
       │
       ▼
HomeResponseMapper.toHomeResponse()
       │
       ▼
HomeResponse (domain model)
       │
       ▼
HomeRepository emits Result<HomeResponse, DataError>
       │
       ▼
HomeViewModel updates HomeState.sections
       │
       ▼
HomeScreen recomposes
```

### Local Storage — DataStore Preferences

- Stores user auth token (`ACCESS_TOKEN` key)
- Async, Flow-based access (no blocking reads)
- Used by `HttpClientFactory` to inject the Bearer token

---

## 10. Design System

### Color Palette

| Token | Hex | Usage |
|---|---|---|
| `PrimaryOrange` | `#FFDD563A` | Primary actions, highlights |
| `PrimaryBlack` | `#FF14151F` | App background |
| `GrayBackgroundColor` | `#FF272938` | Card / surface backgrounds |
| `TextColor` | `#FFFFFFFF` | Body text |
| `PrimaryLightGreen` | `#FF52D774` | Success states |
| `PrimaryRed` | `#FFD33535` | Error states |
| `PrimaryDarkGreen` | `#FF005255` | Secondary success |

### Theme

- **Dark-first:** The app uses a dark color scheme as the default
- **Material 3:** Built on top of Material 3 theming with custom color overrides
- **No dynamic color:** Colors are fixed for brand consistency
- **RTL support:** `LayoutDirection.Rtl` applied at the root for correct Arabic layout

### Typography

- Material 3 default type scale
- Ready for custom Arabic typeface injection

---

## 11. Dependency Injection

The project uses **Koin** — a lightweight, pure Kotlin DI framework.

### Module Structure

```
DiModules.kt (aggregator)
 ├── coreDataModule        → HttpClient, Ktor engine, Ktor logging
 ├── localStorageModule    → DataStore instance
 ├── mediaModule           → MediaPlayer singleton
 └── homeModule            → HomeRepository, RemoteDataSource, HomeViewModel
```

### Initialization

Koin is started in `ThamanyaApplication.onCreate()` with all modules registered. ViewModels are resolved lazily via Koin's `koinViewModel()` in Composables — no manual instantiation needed.

---

## 12. Code Quality & Build System

### Build Tools

| Tool | Purpose |
|---|---|
| **Detekt** | Static analysis for Kotlin code smells |
| **Ktlint** | Code style enforcement |
| **Spotless** | Automated formatting |
| **Dokka** | KDoc documentation generation |
| **Baseline Profile** | Startup performance optimization |
| **ProGuard** | Code shrinking & obfuscation for release |

### Build Types

| Type | Notes |
|---|---|
| `debug` | Full logging, no obfuscation |
| `releaseExternalQa` | QA builds with some debug tooling |
| `release` | ProGuard enabled, optimized |

### Centralized Dependency Management

All library versions are declared once in `buildSrc`, consumed across all modules — no version duplication, no conflicts.

---

## 13. Potential Improvements

The following are well-considered improvements that can meaningfully elevate the project across performance, scalability, user experience, and code quality.

---

### 13.1 Offline-First with Local Caching

**Current:** All data is fetched fresh on every app open.

**Improvement:** Introduce a **Room database** as the local source of truth.

- Cache home sections after first fetch
- Display cached data immediately on app start, refresh in the background
- Show content even with no internet connection
- Pattern: Repository checks local DB first, then syncs from network

**Impact:** Dramatically improves perceived performance and resilience.

---

### 13.2 Pagination for Content Sections

**Current:** The API returns pagination metadata (`nextPage`, `totalPages`) but it is not used — all content loads in a single request.

**Improvement:** Implement **Paging 3** with infinite scroll.

- Load content incrementally as the user scrolls
- Significantly reduces initial load time and memory usage
- Pagination state handled by `PagingData<Content>` flow

**Impact:** Essential for a production-quality content feed with large datasets.

---

### 13.3 Background Audio Playback Service

**Current:** Playback is tied to the Activity lifecycle — audio stops when the app is backgrounded.

**Improvement:** Wrap ExoPlayer in a **foreground Service** with a **MediaSession**.

- Audio continues playing when the screen is off or the user switches apps
- Notification controls (play/pause/skip) appear in the notification shade
- Compatible with Bluetooth headsets and car audio
- Managed via AndroidX Media3's `MediaSessionService`

**Impact:** Core requirement for any serious audio app — a must-have for production.

---

### 13.4 Testing Coverage

**Current:** No test files are present in the project.

**Improvement:** Add a structured testing strategy:

- **Unit tests:** ViewModel logic, use cases, mappers, `Result` extensions — all pure Kotlin, no Android dependencies needed
- **Integration tests:** Repository + network layer with a mock Ktor server (`MockEngine`)
- **UI tests:** Key flows with Compose Testing (`composeTestRule`) — player expand/collapse, search, chip selection
- **Tools:** JUnit 5, Turbine (Flow testing), MockK, Compose UI Test

**Impact:** Enables confident refactoring, prevents regressions, and signals engineering maturity to stakeholders.

---

### 13.5 Multiple Feature Modules

**Current:** Only one feature module (`home`) exists.

**Improvement:** As the app grows, add isolated feature modules:

- `feature:player` — Dedicated player screen with queue management
- `feature:search` — Standalone search experience
- `feature:profile` — User account and settings
- `feature:categories` — Browse by category

**Impact:** Parallel team development, faster build times per module, clear ownership boundaries.

---

### 13.6 Playback Queue & Playlist Support

**Current:** Only a single track can be played at a time with no queue.

**Improvement:** Introduce a playback queue:

- "Up next" list of episodes in a series
- Auto-advance to next episode on completion
- Shuffle and repeat modes
- Queue managed in ExoPlayer's `MediaItem` list

**Impact:** Core user experience feature for a podcast/audiobook platform.

---

### 13.7 Playback Speed Control in UI

**Current:** The `MediaPlayer` already supports `setPlaybackSpeed()` internally, but there is no UI for it.

**Improvement:** Add a speed selector in the expanded player (0.5×, 0.75×, 1×, 1.25×, 1.5×, 2×).

**Impact:** High-demand feature for podcast listeners — low implementation cost given the infrastructure already exists.

---

### 13.8 Sleep Timer

**Current:** Not implemented.

**Improvement:** A common podcast app feature — automatically pauses playback after a set duration (e.g., 15, 30, 60 minutes, or end of episode).

**Impact:** Valued by users who listen before sleep, differentiates the app from basic players.

---

### 13.9 Accessibility (A11y)

**Current:** No explicit accessibility annotations are present.

**Improvement:**
- Add `contentDescription` to all interactive icons and images
- Ensure touch targets are at least 48×48dp
- Add semantic roles (`Role.Button`, `Role.Image`) where needed
- Test with TalkBack screen reader

**Impact:** Required for Play Store accessibility compliance and broader audience reach.

---

### 13.10 Analytics & Crash Reporting

**Current:** No analytics or crash reporting.

**Improvement:**
- **Firebase Crashlytics** for crash reporting — catch production issues before users report them
- **Firebase Analytics** or a custom events layer for tracking: content plays, search usage, category selection, player interactions

**Impact:** Data-driven product decisions and proactive production monitoring.

---

### 13.11 Dynamic Theming / Light Mode

**Current:** Dark mode only, hardcoded colors.

**Improvement:** Support light mode and optionally Android 12+ dynamic color theming.

- Respect system-level dark/light mode preference
- Material 3 dynamic color based on user's wallpaper (Android 12+)

**Impact:** Better user experience across diverse device configurations.

---

### 13.12 Search UX Enhancements

**Current:** Search replaces the entire content feed with results.

**Improvement:**
- Recent searches history (persisted via DataStore)
- Suggested queries while typing
- Empty state illustration when no results found
- Voice search support

**Impact:** Significantly improves discoverability of content.

---

### Summary Table

| Improvement | Effort | Impact | Priority |
|---|---|---|---|
| Background audio service | Medium | Critical | P0 |
| Offline-first caching | High | High | P1 |
| Pagination | Medium | High | P1 |
| Unit & UI testing | High | High | P1 |
| Playback speed UI | Low | Medium | P2 |
| Playback queue | Medium | High | P2 |
| Sleep timer | Low | Medium | P2 |
| Accessibility | Medium | Medium | P2 |
| Analytics & Crash reporting | Low | High | P2 |
| Multiple feature modules | Medium | Medium | P3 |
| Light mode / dynamic theme | Low | Low | P3 |
| Search UX enhancements | Medium | Medium | P3 |

---

*Document prepared to accompany the Thamanya technical review. All architectural decisions reflect the current implementation; improvement items represent forward-looking recommendations based on production audio platform best practices.*
