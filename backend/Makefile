up:
	docker compose up -d

lint:
	./gradlew ktlintFormat

cloc:
	cloc composeApp/src server/src/ shared/src

db-update:
	docker run -v ./migrations:/liquibase/changelog --network="host" liquibase update --driver=org.postgresql.Driver --url="jdbc:postgresql://127.0.0.1:5436/athletica" \
			--changeLogFile=changelog/changelog.yaml --username=crm --password=crm

db-rollback:
	docker run -v ./migrations:/liquibase/changelog --network="host" liquibase rollback-count --count=1 --driver=org.postgresql.Driver --url="jdbc:postgresql://127.0.0.1:5432/athletica" \
			--changeLogFile=changelog/changelog.yaml --username=crm --password=crm

db-add-changelog:
	./gradlew addChangelog -Pchangelog=$(word 2, $(MAKECMDGOALS))
