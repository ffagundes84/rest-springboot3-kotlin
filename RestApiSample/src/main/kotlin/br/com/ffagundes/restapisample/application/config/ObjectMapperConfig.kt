package br.com.ffagundes.restapisample.application.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer

@Configuration
class ObjectMapperConfig {
//    @Bean
//    fun objectMapper(): ObjectMapper {
//        val objectMapper = ObjectMapper()
//        objectMapper.registerModule(JavaTimeModule())
//        objectMapper.findAndRegisterModules()
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
//        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
//        return objectMapper
//    }

//    @Bean
//    fun objectMapper(): Jackson2ObjectMapperBuilderCustomizer {
//        println("DEBUG ===> starting objectMapper...")
//        return Jackson2ObjectMapperBuilderCustomizer { builder ->
//            builder.modules(
//                JavaTimeModule(),
//                KotlinModule.Builder().build()
//            )
//            builder.featuresToDisable(
//                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
//            )
//            builder.featuresToDisable(
//                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
//            )
//        }
//    }
}
