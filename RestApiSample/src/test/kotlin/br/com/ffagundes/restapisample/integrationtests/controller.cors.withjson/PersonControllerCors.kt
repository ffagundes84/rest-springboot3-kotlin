package br.com.ffagundes.restapisample.integrationtests.controller.cors.withjson

import br.com.ffagundes.restapisample.config.TestConfigs
import br.com.ffagundes.restapisample.integrationtests.testcontainers.AbstractIntegrationTest
import br.com.ffagundes.restapisample.integrationtests.vo.PersonVO
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.LogDetail
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.annotation.Order
import kotlin.test.assertEquals

// Para execução desse teste, o Docker precisa estar inicializado!
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class) // recomendado utilizar somente em testes integrados.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonControllerCors() : AbstractIntegrationTest() {
	private lateinit var specification: RequestSpecification
	private lateinit var objectMapper: ObjectMapper
	private lateinit var person: PersonVO

	@BeforeAll
	fun setupTests(): Unit {
		objectMapper = ObjectMapper()
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
		person = PersonVO()
	}

    @Test
	@Order(1)
	fun testCreatePerson() {
		mockPerson()

		// Configuração da request pro serviço
		specification = RequestSpecBuilder()
			.addHeader(
				TestConfigs.HEADER_PARAM_ORIGN,
				TestConfigs.ORIGIN_GOOGLE
			)
			.setBasePath(TestConfigs.BASE_PATH_SUFIX)
			.setPort(TestConfigs.SERVER_PORT)
			.addFilter(RequestLoggingFilter(LogDetail.ALL)) // log do request
			.addFilter(ResponseLoggingFilter(LogDetail.ALL)) // log do response
			.build()

		val content = given()
			.spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.body(person)
			.`when`()
			.post() // verbo a ser executado na chamada
			.then()
			.statusCode(200)
			.extract()
			.body()
			.asString()

		val createdPerson = objectMapper.readValue(
			content,
			PersonVO::class.java
		)

		person = createdPerson // armazena para ser usado nos próximos testes

		assertNotNull(createdPerson.id)
		assertNotNull(createdPerson.firstName)
		assertNotNull(createdPerson.lastName)
		assertNotNull(createdPerson.address)
		assertNotNull(createdPerson.gender)

		assertTrue(createdPerson.id > 0)

		Assertions.assertEquals("Nelson", createdPerson.firstName)
		Assertions.assertEquals("Piquet", createdPerson.lastName)
		Assertions.assertEquals("Brasilia, DF - Brasil", createdPerson.address)
		Assertions.assertEquals("Male", createdPerson.gender)
	}

	@Test
	@Order(2)
	fun testCreateWithWrongOrigin() {
		mockPerson()

		// Configuração da request pro serviço
		specification = RequestSpecBuilder()
			.addHeader(
				TestConfigs.HEADER_PARAM_ORIGN,
				TestConfigs.ORIGIN_NOT_CONFIGURED
			)
			.setBasePath(TestConfigs.BASE_PATH_SUFIX)
			.setPort(TestConfigs.SERVER_PORT)
			.addFilter(RequestLoggingFilter(LogDetail.ALL)) // log do request
			.addFilter(ResponseLoggingFilter(LogDetail.ALL)) // log do response
			.build()

		val content = given()
			.spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.body(person)
			.`when`()
			.post() // verbo a ser executado na chamada
			.then()
			.statusCode(403)
			.extract()
			.body()
			.asString()

		assertEquals("Invalid CORS request", content)
	}

	@Test
	@Order(3)
	fun findById() {
		mockPerson()

		// Configuração da request pro serviço
		specification = RequestSpecBuilder()
			.addHeader(
				TestConfigs.HEADER_PARAM_ORIGN,
				TestConfigs.ORIGIN_LOCALHOST
			)
			.setBasePath(TestConfigs.BASE_PATH_SUFIX)
			.setPort(TestConfigs.SERVER_PORT)
			.addFilter(RequestLoggingFilter(LogDetail.ALL)) // log do request
			.addFilter(ResponseLoggingFilter(LogDetail.ALL)) // log do response
			.build()

		val content = given()
			.spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.pathParam("id", person.id)
			.`when`()["{id}"]
			.then()
			.statusCode(200)
			.extract()
			.body()
			.asString()

		val createdPerson = objectMapper.readValue(
			content,
			PersonVO::class.java
		)

		assertNotNull(createdPerson.id)
		assertNotNull(createdPerson.firstName)
		assertNotNull(createdPerson.lastName)
		assertNotNull(createdPerson.address)
		assertNotNull(createdPerson.gender)

		assertTrue(createdPerson.id > 0)

		Assertions.assertEquals("Nelson", createdPerson.firstName)
		Assertions.assertEquals("Piquet", createdPerson.lastName)
		Assertions.assertEquals("Brasilia, DF - Brasil", createdPerson.address)
		Assertions.assertEquals("Male", createdPerson.gender)
	}

	@Test
	@Order(4)
	fun findByIdWithWrongOrigin() {
		mockPerson()

		// Configuração da request pro serviço
		specification = RequestSpecBuilder()
			.addHeader(
				TestConfigs.HEADER_PARAM_ORIGN,
				TestConfigs.ORIGIN_NOT_CONFIGURED
			)
			.setBasePath(TestConfigs.BASE_PATH_SUFIX)
			.setPort(TestConfigs.SERVER_PORT)
			.addFilter(RequestLoggingFilter(LogDetail.ALL)) // log do request
			.addFilter(ResponseLoggingFilter(LogDetail.ALL)) // log do response
			.build()

		val content = given()
			.spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.pathParam("id", person.id)
			.`when`()["{id}"]
			.then()
			.statusCode(403)
			.extract()
			.body()
			.asString()

		assertEquals("Invalid CORS request", content)
	}

	private fun mockPerson() {
		person.firstName = "Nelson"
		person.lastName = "Piquet"
		person.address = "Brasilia, DF - Brasil"
		person.gender = "Male"
	}
}