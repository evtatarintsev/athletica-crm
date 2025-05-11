package ru.athletica.crm.config

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.http.codec.json.KotlinSerializationJsonEncoder
import org.springframework.web.reactive.config.WebFluxConfigurer
import ru.athletica.json.UuidSerializer
import java.util.*


@Configuration
class WebFluxConfig : WebFluxConfigurer {
    /**
     * Добавляет кастомные сериализаторы.
     */
    override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
        val json = Json {
            serializersModule = SerializersModule {
                contextual(UUID::class, UuidSerializer)
            }
        }
        configurer.defaultCodecs().kotlinSerializationJsonEncoder(KotlinSerializationJsonEncoder(json))
        configurer.defaultCodecs().kotlinSerializationJsonDecoder(KotlinSerializationJsonDecoder(json))
    }
}