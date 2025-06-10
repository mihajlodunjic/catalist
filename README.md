# Catalist – Cat Breed Display App

**Catalist** is a mobile Android application that displays information about different cat breeds using data from [TheCatApi](https://thecatapi.com). It allows users to search breeds, view detailed information about each breed, and open additional information on Wikipedia.

---

## Features

- ✅ Display a list of all available cat breeds
- ✅ Detailed screen with image, description, traits, and statistics
- ✅ Search breeds by name
- ✅ Visual display of temperament using Material Chips
- ✅ Open Wikipedia page for each breed
- ✅ Preserves screen state during rotation
- ✅ Displays loading, success, and error states

---

## 🛠️ Technologies and Libraries

- **Kotlin**
- **Jetpack Compose** – declarative UI
- **Retrofit & OkHttp** – for API calls to TheCatApi
- **Kotlin Coroutines** – asynchronous programming
- **Kotlinx Serialization** – for JSON parsing
- **Jetpack Navigation** – single-activity navigation
- **Hilt (Dependency Injection)**
- **MVVM/MVI architecture**
- **Material Design 3 (Compose)**

---

## 🔐 API Ključ

The app uses a `local.properties` file to store the API key.

1. Add the API key to the `local.properties` file in the root folder:
    ```local.properties
    CAT_API_KEY=your_api_key_here
    ```

2. The API key is accessed via `BuildConfig.CAT_API_KEY` (not hardcoded).

⚠️ The `local.properties` file is included in `.gitignore` and should not be pushed to GitHub.



---

## 🚀 Running the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/mihajlodunjic/catalist.git
   cd catalist
   
2. Open the project in Android Studio Iguana or newer.


## 📚 API Source
Cat data is provided by:

TheCatApi.com
API Documentation (Postman)
