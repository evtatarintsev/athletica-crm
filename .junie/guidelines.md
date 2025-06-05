# Junie guidelines для разработки

- для комментариев и документации используй русский язык
- директория фронтенд проекта frontend

### Структура проекта:

Для копирования структуры в терминале используйте:
```bash
.
├── backend
│   ├── src
│   │   ├── main
│   │   │   ├── kotlin
│   │   │   │   └── ru
│   │   │   │       └── athletica
│   │   │   │           ├── crm
│   │   │   │           │   ├── api
│   │   │   │           │   ├── config
│   │   │   │           │   ├── db
│   │   │   │           │   ├── modules
│   │   │   │           │   ├── security
│   │   │   │           │   └── AthleticaCrmApplication.kt
│   │   │   │           └── json
│   │   │   └── resources
│   │   │       ├── db
│   │   │       │   ├── changelogs
│   │   │       │   ├── changelog.template
│   │   │       │   └── changelog.yaml
│   │   │       ├── application.properties
│   │   │       └── logback.xml
│   │   └── test
│   │       └── kotlin
│   │           └── ru
│   │               └── athletica
│   │                   ├── crm
│   │                   └── json
│   ├── Makefile
│   ├── README.md
│   ├── build.gradle.kts
│   ├── docker-compose.yaml
│   ├── gradle.properties
│   └── settings.gradle.kts
├── frontend
│   ├── api_client
│   ├── app
│   │   ├── (dashboard)
│   │   │   ├── customers
│   │   ├── login
│   │   │   └── page.tsx
│   │   ├── globals.css
│   │   ├── layout.tsx
│   │   └── providers.tsx
│   ├── components
│   │   ├── ui
│   ├── lib
│   │   ├── swr
│   │   ├── api-client.ts
│   │   └── utils.ts
│   ├── public
│   ├── README.md
│   ├── components.json
│   ├── package.json
│   ├── postcss.config.js
│   ├── tailwind.config.ts
│   └── tsconfig.json
├── openapi
│   ├── README.md
│   └── openapi.yaml
└── README.md
```


### Описание ключевых элементов структуры:

- **`frontend/api_client`** - автоматически сгенеренный http клиент для typescript.
В этой директории нельзя вносить изменения вручную, только через генерацию кода.
Для генерации используется команда `cd backend && ./gradlew generateNextjsClient`
- **`frontend/package.json`**  - зависимости frontend проекта
- **`openapi/openapi.yaml`** - основной файл с описанием контрактов. 
Все изменения в HTTP API в первую очередь вносятся сюда, а затем генерируются схемы для backend `cd backend && ./gradlew generateKotlinApi` 
и клиент для frontend `cd backend && ./gradlew generateNextjsClient`
- **`src/main/resources/db`** - директория для liquibase миграций. Изменения схем БД должны быть отображены в миграциях
