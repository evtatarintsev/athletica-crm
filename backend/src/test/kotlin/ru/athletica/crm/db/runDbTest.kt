package ru.athletica.crm.db

import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.jetbrains.exposed.sql.Transaction
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Запускает тест в скоупе транзакции и передает скоуп теста в качестве аргумента.
 */
fun runDbTest(
    context: CoroutineContext = EmptyCoroutineContext,
    timeout: Duration = 10.seconds,
    testBody: suspend Transaction.(TestScope) -> Unit
) = runTest(context, timeout) {
    rollbackTransaction {
        testBody(this@runTest)
    }
}
