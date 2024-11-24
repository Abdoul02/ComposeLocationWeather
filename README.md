# 🌤️ Compose Location Weather App

A simple weather App that provide weather and forecast based on the user's location and allow user
to save their current location
The user can also see all the saved Location on google map.
---

## 🚀 Features

- 🌍 **Real-time Weather Updates**: Stay updated with current and future weather forecasts.
- 📊 **Interactive UI**: A smooth and intuitive interface designed using Jetpack Compose.
- 🔍 **Data Storage**: Store and retrieve weather data efficiently with Room Database.
- 💡 **Architecture**: MVVM and clean Architecture.
- 🗺️ **Compose Google Maps**: Google map using Jetpack compose.

---

## 🛠️ Technologies Used

| Technology                                                                                                                | Description                                      |
|---------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------|
| ![Kotlin](https://img.shields.io/badge/-Kotlin-blue?style=flat-square&logo=kotlin&logoColor=white)                        | Language for building Android apps               |
| ![Jetpack Compose](https://img.shields.io/badge/-Jetpack%20Compose-purple?style=flat-square&logo=android&logoColor=white) | Modern UI toolkit for building declarative UIs   |
| ![Coroutine Flow](https://img.shields.io/badge/-Coroutine%20Flow-green?style=flat-square&logo=kotlin&logoColor=white)     | Asynchronous data streams management             |
| ![Hilt](https://img.shields.io/badge/-Hilt-darkgreen?style=flat-square&logo=dagger&logoColor=white)                       | Dependency injection framework                   |
| ![Room](https://img.shields.io/badge/-Room-orange?style=flat-square&logo=android&logoColor=white)                         | Local database persistence                       |
| ![MVVM](https://img.shields.io/badge/-MVVM-yellow?style=flat-square&logo=android&logoColor=white)                         | Architectural pattern for separation of concerns |
| ![LiveData](https://img.shields.io/badge/-LiveData-red?style=flat-square&logo=android&logoColor=white)                    | Observable data holder                           |
| ![Mockito](https://img.shields.io/badge/-Mockito-lightblue?style=flat-square&logo=java&logoColor=white)                   | Unit testing framework                           |
| ![GitHub Workflows](https://img.shields.io/badge/-GitHub%20Workflows-black?style=flat-square&logo=github&logoColor=white) | Continuous integration and automation            |

---

---
## 🛡️ CI/CD with GitHub Workflows
The project leverages GitHub Actions for:

🚨 Running Unit Tests on every pull request

    * Workflow triggers on pull_request targeting dev branch.
📦 Generating debug build APK 

    * Workflow triggers on merge_request targeting main branch.
    
## ‼️ Please note the following:

OpenWeatherMap Key should stored in your bash_profile as: `WEATHER_API_KEY`.
Alternatively you can just paste it in your local.properties
and uncomment the following code under build.gradle.kts

    /*val properties = Properties().apply { load(FileInputStream(File(rootProject.rootDir, "local.properties"))) }
    val weatherApiKey = properties.getProperty("WEATHER_API_KEY")
    buildConfigField("String", "WEATHER_API_KEY",weatherApiKey)*/ 

The Google maps key is restricted and therefore needs to be replaced with your own. Please replace
`google_maps_key` in the string.xml file. (An APK Will be provided for Test)

The Application was built on `Android Studio Ladybug | 2024.2.` there is currently a bug where tests
do not have access to classes that are in the main Java file. Apparently this was resolved in the
latest update,but if you are struggling to run the tests, a workaround was provided, please
replace `runConfigurations.xml` in the .idea folder with the one found under miscellaneous folder.
