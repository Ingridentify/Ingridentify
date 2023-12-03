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

3. **Build and Run:**
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
│   |   ├── ui
│   |   |   ├── login
│   |   |   |   └── LoginFragment.kt
│   |   |   └── register
│   |   |       └── RegisterFragment.kt
│   |   ├── AuthActivity.kt
│   |   ├── MainActivity.kt
│   |   └── SplashActivity.kt
│   ├── com.ingridentify (androidTest)
|   |   └── ExampleInstrumentedTest.kt
│   └── com.ingridentify (test)
|       └── ExampleUnitTest.kt
└── res
    ├── drawable
    |   ├── ic_email.xml
    |   ├── ic_launcher_background.xml
    |   ├── ic_launcher_foreground.xml
    |   ├── ic_lock.xml
    |   ├── ic_person.xml
    |   └── typography.png
    ├── layout
    |   ├── activity_auth.xml
    |   ├── activity_main.xml
    |   ├── activity_splash.xml
    |   ├── fragment_login.xml
    |   └── fragment_register.xml
    ├── navigation
    |   └── auth_navigation.xml
    └── values
        ├── colors.xml
        ├── strings.xml
        └── themes
            ├── themes.xml
            └── themes.xml (night)

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
