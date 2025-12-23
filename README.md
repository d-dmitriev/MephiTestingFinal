# UI Automation Project: Wikipedia Web & Mobile

## Описание
Автоматизированные UI-тесты для:
- Веб-сайта https://www.wikipedia.org/
- Мобильного приложения Wikipedia (Android)

Реализовано:
- 4 веб-сценария (поиск, навигация, язык, повторный поиск)
- 3 мобильных сценария (поиск статьи, прокрутка, проверка меню)

## Требования
- JDK 11+
- Maven
- Android SDK + эмулятор (API 33 рекомендуется)
- Appium Server (4723 порт)
- Установленное приложение Wikipedia на эмуляторе

## Запуск

### Веб-тесты
```bash
mvn test -Dtest=WikipediaWebTests