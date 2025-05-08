package ru.athletica.crm

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
class AthleticaCrmApplicationTests {
	companion object {
		@Container
		@JvmStatic
		val db = PostgreSQLContainer("postgres:17.4-alpine")

		@DynamicPropertySource
		@JvmStatic
		fun registerDBContainer(registry: DynamicPropertyRegistry) {
			registry.add("spring.datasource.url", db::getJdbcUrl)
			registry.add("spring.datasource.username", db::getUsername)
			registry.add("spring.datasource.password", db::getPassword)
		}
	}
	@Test
	fun contextLoads() {
	}

}
