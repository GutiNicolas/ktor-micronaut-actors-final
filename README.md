# Ktor vs Micronaut - Kotlin REST Benchmark

This repository contains two simple Kotlin applications exposing an identical REST endpoint:

- 📦 `ktor-app`: built with [Ktor](https://ktor.io/) using coroutines and channels to emulate Akka-like actors.
- 📦 `micronaut-app`: built with [Micronaut](https://micronaut.io/) using, again, a coroutine actor and Micronaut Serde.

Both expose a POST endpoint at `/process` and expect JSON input like:

```json
{ "input": "Hello, World!" }
```

They respond with:

```json
{ "output": "Hello, from World!" }
```

---

## 🔧 Requirements

- JDK 17+
- Gradle (or generate the wrapper as shown below)
- cURL or Postman for testing

---

## 🚀 Run Instructions

### 1. Generate Gradle Wrapper (if missing)

If you cloned the project without the wrapper files, generate them by running:

```bash
gradle wrapper --gradle-version 8.7
```

This will create:

- `./gradlew`
- `./gradlew.bat`
- `gradle/wrapper/gradle-wrapper.jar`
- `gradle/wrapper/gradle-wrapper.properties`

Make the wrapper executable (Linux/macOS):

```bash
chmod +x ./gradlew
```

---

## ▶️ Running the Applications

### Run Ktor App

```bash
./gradlew clean :ktor-app:run
```

Accessible at: `http://localhost:8080/process`

---

### Run Micronaut App

```bash
./gradlew clean :micronaut-app:run
```

Accessible at: `http://localhost:8080/process`

> ❗ Run only one app at a time on the same port or configure a different port.

---

## 🧪 Test the Endpoint

```bash
curl -X POST http://localhost:8080/process \
  -H "Content-Type: application/json" \
  -d '{ "input": "Hello, World!" }'
```

Expected output:

```json
{ "output": "Hello, from World!" }
```

---

## 📁 Project Structure

```
ktor-micronaut-actors-final/
├── build.gradle.kts
├── settings.gradle.kts
├── ktor-app/
│   └── src/main/kotlin/app/
├── micronaut-app/
│   └── src/main/kotlin/app/
└── README.md
```

---

## ⚙️ Implementation Details

### Ktor App

- Uses embedded Netty server.
- Serialization with `kotlinx.serialization`.
- Actor model via Kotlin `Channel`.

### Micronaut App

- Uses `@Controller`, `@Singleton`, and Micronaut's dependency injection.
- Serialization via `@Serdeable` annotations.
- DTOs and actor separated for modularity.
- Uses `runBlocking` in controller to simplify coroutine interop.

---

## 👨‍💻 Author

Fraud Platform Architecture Team — @nicolas.gutierrez  
Developed as a benchmark to compare modern REST frameworks in Kotlin.

---

## 🪪 License

MIT License — feel free to use, modify, and share.
