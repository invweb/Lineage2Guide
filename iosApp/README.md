# iOS App Setup

This guide explains how to set up and run the Lineage2Guide iOS app with Kotlin Multiplatform Mobile (KMM).

## Prerequisites

- Xcode 15+ 
- Kotlin 2.2.10+
- Android Studio (for Gradle wrapper)

## Setup Instructions

### 1. Build the Shared Framework

Before opening Xcode, you need to build the KMM shared framework:

```bash
./gradlew :shared:linkReleaseFrameworkIosArm64
./gradlew :shared:linkReleaseFrameworkIosSimulatorArm64
./gradlew :shared:linkReleaseFrameworkIosX64
```

Or use the unified task:
```bash
./gradlew :shared:assembleIosFramework
```

### 2. Open Xcode Project

```bash
open iosApp/iosApp.xcodeproj
```

### 3. Configure Build Phases

In Xcode, select the `iosApp` target and add the following build phase:

**Run Script:**
Add a new "Run Script" build phase to copy the shared framework:

```bash
# Copy shared framework
FRAMEWORK_PATH="${PROJECT_DIR}/../shared/build/ios-framework/Release"
if [ -d "$FRAMEWORK_PATH" ]; then
    cp -R "$FRAMEWORK_PATH/shared.framework" "${BUILT_PRODUCTS_DIR}/"
    cp -R "$FRAMEWORK_PATH/shared.framework.dSYM" "${BUILT_PRODUCTS_DIR}/"
fi
```

### 4. Link the Framework

In the `iosApp` target's **General** tab:
1. Scroll to **Frameworks, Libraries, and Embedded Content**
2. Add `shared.framework`
3. Set Embed to **Do Not Embed** (it's copied via build phase)

### 5. Configure Header Search Paths

In the `iosApp` target's **Build Settings**:
- Add `$(PROJECT_DIR)/../shared/build/ios-framework/Release` to **Header Search Paths**

## Running on Simulator

For the iOS Simulator (arm64):

```bash
# Build for simulator
./gradlew :shared:linkReleaseFrameworkIosSimulatorArm64

# Then run in Xcode
# Select "iPhone 15 (iOS 17)" or similar simulator
# Press Cmd+R
```

## Running on Device

For physical iOS devices (arm64):

```bash
# Build for device
./gradlew :shared:linkReleaseFrameworkIosArm64

# Sign the app with your development team in Xcode
# Connect device via USB
# Press Cmd+R
```

## Troubleshooting

### "shared.framework not found"
Make sure you've built the framework first:
```bash
./gradlew :shared:linkReleaseFrameworkIosSimulatorArm64
```

### Architecture mismatch
Ensure you're building the correct architecture:
- Simulator (arm64): `linkReleaseFrameworkIosSimulatorArm64`
- Device (arm64): `linkReleaseFrameworkIosArm64`
- Simulator (x64): `linkReleaseFrameworkIosX64` (Intel Macs only)

### Xcode linking errors
Clean the build folder (Shift+Cmd+K) and rebuild:
```bash
xcodebuild clean -project iosApp/iosApp.xcodeproj -target iosApp
```

## Project Structure

```
iosApp/
├── iosApp.xcodeproj/     # Xcode project
├── iosApp/               # Swift source files
│   ├── iosApp.swift      # AppDelegate
│   └── ComposeViewController.swift  # Compose Multiplatform host
└── Info.plist            # App configuration
```

## Next Steps

1. Add Compose Multiplatform UI code to the shared module
2. Create iOS-specific UI components if needed
3. Configure push notifications (APNs)
4. Set up App Store distribution
