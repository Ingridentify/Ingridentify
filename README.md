# Ingridentify Android App

Welcome to the Ingridentify Android App repository! This app is part of the Ingridentify project, which aims to address challenges in modern meal planning, unhealthy eating habits, and food wastage. The app utilizes computer vision and AI to recognize various fruits and vegetables through image scanning, providing users with personalized menu suggestions and detailed cooking recipes.

## Features

- **Ingredient Recognition:** Utilizes computer vision to identify various fruits and vegetables through image scanning.
- **Personalized Menu Suggestions:** Offers users personalized menu suggestions based on their pantry items.
- **Detailed Cooking Recipes:** Provides step-by-step cooking recipes for a diverse culinary experience.

## Screenshots

## Getting Started

To get started with the Ingridentify Android App, follow these steps:

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/Ingridentify/android-app.git Ingridentify
   ```

2. **Open in Android Studio:**
   Open the project in Android Studio and ensure you have the necessary dependencies installed.

3. **Set Base URL:**
   In the `local.properties` file, set the `BASE_URL` variable to the base URL of the Ingridentify API.

   ```properties
   BASE_URL=https://ingridentify-api.herokuapp.com
   ```

4. **Build and Run:**
   Build and run the app on your Android device or emulator.

## Project Structure

The rough project structure is as follows:

```
app
├── manifests
│   └── AndroidManifest.xml
├── java
│   ├── com.ingridentify
│   |   ├── components
│   |   |   ├── EmailEditText.kt
│   |   |   └── PasswordEditText.kt
│   |   ├── data
│   |   |   ├── datastore
│   |   |   |   └── UserPreference.kt
│   |   |   ├── model
│   |   |   |   └── UserModel.kt
│   |   |   ├── remote
│   |   |   |   ├── response
|   │   |   |   |   ├── ErrorResponse.kt
|   │   |   |   |   └── LoginResponse.kt
│   |   |   |   └── retrofit
|   │   |   |       ├── ApiConfig.kt
|   │   |   |       └── ApiService.kt
│   |   |   ├── Repository.kt
│   |   |   └── Result.kt
│   |   ├── ui
│   |   |   ├── add
│   |   |   |   ├── AddFragment.kt
│   |   |   |   ├── AddViewModel.kt
│   |   |   |   └── CameraFragment.kt
│   |   |   ├── auth
│   |   |   |   └── AuthActivity.kt
│   |   |   ├── home
│   |   |   |   └── HomeFragment.kt
│   |   |   ├── login
│   |   |   |   ├── LoginFragment.kt
│   |   |   |   └── LoginViewModel.kt
│   |   |   ├── main
│   |   |   |   ├── MainActivity.kt
│   |   |   |   └── MainViewModel.kt
│   |   |   ├── register
│   |   |   |   └── RegisterFragment.kt
│   |   |   ├── SplashActivity.kt
│   |   |   └── ViewModelFactory.kt
│   |   └── utils
│   |       └── FileUtils.kt
│   ├── com.ingridentify (androidTest)
|   |   └── ExampleInstrumentedTest.kt
│   └── com.ingridentify (test)
|       └── ExampleUnitTest.kt
└── res
    ├── drawable
    |   ├── ic_bottom_camera.xml
    |   ├── ic_bottom_home.xml
    |   ├── ic_camera_filled.xml
    |   ├── ic_camera_outlined.xml
    |   ├── ic_camera_switch.xml
    |   ├── ic_circle.xml
    |   ├── ic_email.xml
    |   ├── ic_home_filled.xml
    |   ├── ic_home_outlined.xml
    |   ├── ic_launcher_background.xml
    |   ├── ic_launcher_foreground.xml
    |   ├── ic_lock.xml
    |   ├── ic_logout.xml
    |   ├── ic_person.xml
    |   ├── ic_placeholder.xml
    |   ├── typography (2)
    |   |   ├── typography.png
    |   |   └── typography.png (night)
    |   └── typography_color.png
    ├── layout
    |   ├── activity_auth.xml
    |   ├── activity_main.xml
    |   ├── activity_splash.xml
    |   ├── fragment_add.xml
    |   ├── fragment_camera.xml
    |   ├── fragment_home.xml
    |   ├── fragment_login.xml
    |   └── fragment_register.xml
    ├── menu
    |   ├── bottom_nav_menu.xml
    |   └── main_menu.xml
    ├── mipmap
    |   ├── ic_launcher (6)
    |   |   ├── ic_launcher.webp (hdpi)
    |   |   ├── ic_launcher.webp (mdpi)
    |   |   ├── ic_launcher.webp (xhdpi)
    |   |   ├── ic_launcher.webp (xxhdpi)
    |   |   ├── ic_launcher.webp (xxxhdpi)
    |   |   └── ic_launcher.xml (anydpi-v26)
    |   └── ic_launcher_round (6)
    |       ├── ic_launcher_round.webp (hdpi)
    |       ├── ic_launcher_round.webp (mdpi)
    |       ├── ic_launcher_round.webp (xhdpi)
    |       ├── ic_launcher_round.webp (xxhdpi)
    |       ├── ic_launcher_round.webp (xxxhdpi)
    |       └── ic_launcher_round.xml (anydpi-v26)
    ├── navigation
    |   ├── auth_navigation.xml
    |   └── main_navigation.xml
    ├── values
    |   ├── colors.xml
    |   ├── strings.xml
    |   └── themes (2)
    |       ├── themes.xml
    |       └── themes.xml (night)
    └── values
        ├── backup_rules.xml
        └── data_extraction_rules.xml

Gradle Scripts
├── build.gradle.kts (Project: Ingridentify)
├── build.gradle.kts (Module: app)
├── proguard-rules.pro (ProGuard Rules for ":app")
├── gradle.properties (Project Properties)
├── gradle-wrapper.properties (Gradle Version)
├── local.properties (SDK Location)
└── settings.gradle.kts (Project Settings)
```

## Dependencies

The Ingridentify Android App utilizes the following dependencies:

## Issues

If you encounter any issues or have suggestions, feel free to open an issue on the [Issue Tracker](https://github.com/Ingridentify/android-app/issues).

## Acknowledgments

We would like to express our gratitude to everyone contributing to the Ingridentify project. Together, we are making strides towards healthier and more sustainable living.

Happy Cooking! :cookie:
