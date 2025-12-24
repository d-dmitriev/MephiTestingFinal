# 🧪 UI Automation Project: Wikipedia Web, Mobile & API Tests

Проект автоматизированных UI-тестов для:

- **Веб-версии** Wikipedia (https://www.wikipedia.org/)
- **Мобильного приложения** Wikipedia для Android (альфа-версия)
- **API Wikipedia** (опционально)

Реализовано:

- ✅ 8 веб-сценариев (поиск, смена языка, навигация и т.д.)
- ✅ 4 мобильных сценария (поиск статьи, смена языка, поиск на русском, смена языка после поиска)
- ✅ Page Object Model для веба и мобилки
- ✅ Поддержка конфигурации через `config.properties`
- ✅ Опциональные API-тесты (RestAssured)

---

## 🛠️ Требуемое окружение

| Компонент            | Версия / Требования                                     |
|----------------------|---------------------------------------------------------|
| **JDK**              | 21 или выше                                             |
| **Maven**            | 3.6+                                                    |
| **Android SDK**      | Установлен через Android Studio                         |
| **Эмулятор Android** | API 33+ (рекомендуется Pixel 9, Android 16)             |
| **Appium Server**    | Запущен на `http://127.0.0.1:4723`                      |
| **Приложение**       | Установлено **Wikipedia Alpha** (`org.wikipedia.alpha`) |
| **Браузер**          | Chrome или Firefox (для веб-тестов)                     |

> 💡 **Appium Inspector** рекомендуется для отладки локаторов.

---

## 📦 Зависимости (управляются через Maven)

- Selenium WebDriver 4.21.0
- Appium Java Client 9.2.2
- TestNG 7.10.1
- WebDriverManager 5.9.2 (автоматическая загрузка драйверов)
- RestAssured 5.5.0 (для API-тестов)

Все зависимости подтягиваются автоматически при запуске `mvn test`.

---

## ▶️ Запуск тестов

### 1. **Запуск веб-тестов**

```bash
mvn test -Dtest=WikipediaWebTests
```

> Поддерживаемые браузеры: **Chrome**, **Firefox** (указывается в `config.properties`).

---

### 2. **Запуск мобильных тестов**

**Предварительно:**

1. Запустите эмулятор Android (например, через AVD Manager).

```bash
emulator -avd Pixel_9
```

2. Убедитесь, что приложение **Wikipedia Alpha** установлено. Скачать последнюю версию
   можно [тут](https://github.com/wikimedia/apps-android-wikipedia/releases/tag/latest):

```bash
adb -s emulator-5554 install app-alpha-universal-release.apk
```

3. Запустите **Appium Server** (через Appium Desktop или CLI: `appium`).

```bash
appium driver install uiautomator2
appium driver list --installed 
appium plugin install inspector
appium --use-plugins=inspector --allow-cors
```

**Запуск:**

```bash
mvn test -Dtest=WikipediaMobileTests
```

> ⚠️ Убедитесь, что в `src/test/resources/config.properties` указаны корректные параметры:
> ```properties
> platform.version=16.0
> device.name=emulator-5554
> app.package=org.wikipedia.alpha
> ```

---

### 3. **Запуск API-тестов**

```bash
mvn test -Dtest=WikipediaAPITests
```

---

## 📊 Отчёты

После выполнения через Maven:

```bash
mvn clean test
```

TestNG-отчёты генерируются в:

```
target/surefire-reports/
├── index.html
├── emailable-report.html
└── *.xml
```
Отчет [emailable-report.html](target/surefire-reports/emailable-report.html) приложен к поекту ([открыть](https://htmlpreview.github.io/?https://raw.githubusercontent.com/d-dmitriev/MephiTestingFinal/refs/heads/master/target/surefire-reports/emailable-report.html)).

> 💡 Вы можете открыть index.html в браузере для просмотра результатов.

---

## ⚙️ Конфигурация

Основные параметры задаются в файле:

```
src/test/resources/config.properties
```

Пример:

```properties
# Веб
browser=chrome
# Мобилка
platform.name=Android
platform.version=16.0
device.name=emulator-5554
app.package=org.wikipedia.alpha
app.activity=org.wikipedia.main.MainActivity
```

---

## 🚀 CI-готовность

Проект совместим с любым CI/CD (GitHub Actions, GitLab CI, Jenkins):

```yaml
# Пример для GitHub Actions (web-only)
- name: Run Web Tests
  run: mvn test -Dtest=WikipediaWebTests
```

Для мобильных тестов требуется:

- Запущенный эмулятор (например, через `avd-runner`)
- Запущенный Appium Server в фоне

---

## ✅ Проверено на

| Платформа            | Версия                       |
|----------------------|------------------------------|
| **macOS**            | Sequoia 15.6.1               |
| **JDK**              | Temurin 25                   |
| **Chrome**           | 143.0.7499.147               |
| **Firefox**          | 146.0.1                      |
| **Android Emulator** | Pixel 9, API 36 (Android 16) |
| **Wikipedia Alpha**  | 50564-alpha-2025-12-22       |
| **Appium**           | 3.1.2                        |

---

> 📌 **Совет**: для отладки мобильных локаторов используйте **Appium Inspector** — это значительно ускорит разработку
> тестов.
