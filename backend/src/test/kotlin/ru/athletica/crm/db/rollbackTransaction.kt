package ru.athletica.crm.db

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

/**
Обертка над newSuspendedTransaction, запускающая транзакцию в IO пуле.
 */
suspend fun <T> rollbackTransaction(block: suspend Transaction.() -> T): T {
    val result = newSuspendedTransaction(Dispatchers.IO, PostgresConfig.db) {
        val result = block()
        rollback()
        result
    }
    return result
}