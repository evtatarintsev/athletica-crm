package ru.athletica.crm.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import kotlin.getValue

object PostgresConfig {
    val config = HikariConfig().apply {
        jdbcUrl = "jdbc:postgresql://localhost:5438/athletica_test"
        driverClassName = "org.postgresql.Driver"
        username = "crm"
        password = "crm"
        maximumPoolSize = 6
        isReadOnly = false
    }
    val db by lazy {
        val dataSource = HikariDataSource(config)
        // Пока накатываю миграции руками, т.к. не хочется ждать
        // migrate(dataSource)
        Database.connect(dataSource)
    }

    /**
     * Накатывает миграции.
     */
//    fun migrate(dataSource: HikariDataSource){
//        val liquibase = Liquibase(
//            "db/changelog.yaml",
//            ClassLoaderResourceAccessor(),
//            JdbcConnection(dataSource.connection)
//        )
//        liquibase.update()
//    }
}
