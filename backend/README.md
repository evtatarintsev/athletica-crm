# Backend сервиса AthleticaCRM

## Технологический стек
- Kotlin - основной язык программирования
- SpringBoot - фреймворк для решения стандартный задач
- PostgreSQL основная база данных
- Стараемся маскимально использовать kotlin библиотеки вместо java
  - Kotlinx-coroutines для асинхронного программирования
  - Kotlinx-serialization для сериализации
  - Kotlinx-datetime для работы с датой/временем
  - jetbrains.exposed в качестве DSL для SQL запросов
- liquibase управление миграциями БД
