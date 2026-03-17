package br.com.ffagundes.restapisample.integrationtests.controller.withyml.mapper

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.type.TypeFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.databind.ObjectMapper as JacksonObjectMapper
import io.restassured.mapper.ObjectMapper
import io.restassured.mapper.ObjectMapperDeserializationContext
import io.restassured.mapper.ObjectMapperSerializationContext
import java.util.logging.Logger

class YMLMapper: ObjectMapper {
    private val objectMapper: JacksonObjectMapper = JacksonObjectMapper(YAMLFactory())
    private val typeFactory: TypeFactory = TypeFactory.defaultInstance()

    private val logger = Logger.getLogger(YMLMapper::class.java.name)
    init {
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.findAndRegisterModules()
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }
    override fun deserialize(context: ObjectMapperDeserializationContext): Any? {
        try {
            val dataToDeserialize = context.dataToDeserialize.asString()
            val type = context.type as Class<*>
            return objectMapper.readValue(dataToDeserialize, typeFactory.constructType(type))
        } catch(e: JsonMappingException) {
            e.printStackTrace()
        } catch(e: JsonProcessingException) {
            e.printStackTrace()
        }
        return null
    }

    override fun serialize(context: ObjectMapperSerializationContext): Any? {
        try {
            val serializedObj = objectMapper.writeValueAsString(context.objectToSerialize)
            return serializedObj
        } catch(e: JsonProcessingException) {
            e.printStackTrace()
        }
        return null
    }
}