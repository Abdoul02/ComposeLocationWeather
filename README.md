#Compose Location Weather
A simple weather App that provide weather and forecast based on the user's location and allow user
to save their current location
The user can also see all the saved Location on google map.

The Application has some unit and UI Tests

    Technology used {
        - Kotlin
        - Jetpack compose
        - Coroutine Flow
        - Hilt
        - Room
        - MVVM
        - LiveData
        - Mockito for Unit testing.
        - Github workflows
    } 

Please note the following:

OpenWeatherMap Key should stored in your bash_profile as: `WEATHER_API_KEY`.
Alternatively you can just paste it in your local.properties
and uncomment the following code under build.gradle.kts
`/*val properties = Properties().apply { load(FileInputStream(File(rootProject.rootDir, "local.properties"))) }
        val weatherApiKey = properties.getProperty("WEATHER_API_KEY")
        buildConfigField("String", "WEATHER_API_KEY",weatherApiKey)*/`

The Google maps key is restricted and therefore needs to be replaced with your own. Please replace
`google_maps_key` in the string.xml file. (An APK Will be provided for Test)

The Application was built on `Android Studio Ladybug | 2024.2.` there is currently a bug where tests
do not have access to classes that are in the main Java file. Apparently this was resolved in the
latest update,but if you are struggling to run the tests, a workaround was provided, please
replace `runConfigurations.xml` in the .idea folder with the one found under miscellaneous folder.