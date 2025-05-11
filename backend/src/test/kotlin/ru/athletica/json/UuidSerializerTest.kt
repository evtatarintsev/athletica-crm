package ru.athletica.json

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class UuidSerializerTest {
    @Test
    fun `UUID should be serialized correctly`() {
        @Serializable
        data class TestClass(@Contextual val id: UUID)

        val json = Json {
            this.serializersModule = SerializersModule {
                contextual(UUID::class, UuidSerializer) // Регистрация UUIDSerializer
            }
        }

        val result = json.encodeToString(TestClass(UUID.fromString("ad60d7e0-7ebe-4cd6-ab34-188ef9258b08")))

        assertEquals("{\"id\":\"ad60d7e0-7ebe-4cd6-ab34-188ef9258b08\"}", result)
    }

    @Test
    fun `UUID should be deserialized correctly`() {
        @Serializable
        data class TestClass(@Contextual val id: UUID)

        val json = Json {
            this.serializersModule = SerializersModule {
                contextual(UUID::class, UuidSerializer) // Регистрация UUIDSerializer
            }
        }

        val result = json.decodeFromString<TestClass>("{\"id\":\"ad60d7e0-7ebe-4cd6-ab34-188ef9258b08\"}")

        assertEquals(TestClass(UUID.fromString("ad60d7e0-7ebe-4cd6-ab34-188ef9258b08")), result)
    }
}