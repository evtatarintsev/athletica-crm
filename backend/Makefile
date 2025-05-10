DB_DSN=jdbc:postgresql://127.0.0.1:5436/athletica
TEST_DB_DSN=jdbc:postgresql://127.0.0.1:5438/athletica_test
CHANGELOGFILE=src/main/resources/db/changelog.yaml
DB_DRIVER=org.postgresql.Driver
up:
	docker compose up -d

lint:
	./gradlew ktlintFormat

cloc:
	cloc composeApp/src server/src/ shared/src

db-update:
	liquibase update --driver=$(DB_DRIVER) --url="$(DB_DSN)" --changeLogFile=$(CHANGELOGFILE) --username=crm --password=crm

db-rollback:
	liquibase rollback-count --count=1 --driver=$(DB_DRIVER) --url="$(DB_DSN)" --changeLogFile=$(CHANGELOGFILE) --username=crm --password=crm

db-update-test:
	liquibase update --driver=$(DB_DRIVER) --url="$(TEST_DB_DSN)" --changeLogFile=$(CHANGELOGFILE) --username=crm --password=crm

db-rollback-test:
	liquibase rollback-count --count=1 --driver=$(DB_DRIVER) --url="$(TEST_DB_DSN)" --changeLogFile=$(CHANGELOGFILE) --username=crm --password=crm

db-add-changelog:
	./gradlew addChangelog -Pchangelog=$(word 2, $(MAKECMDGOALS))
