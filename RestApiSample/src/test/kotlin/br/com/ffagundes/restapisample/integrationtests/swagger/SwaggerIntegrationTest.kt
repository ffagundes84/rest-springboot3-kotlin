package br.com.ffagundes.restapisample.integrationtests.swagger

import br.com.ffagundes.restapisample.config.ConfigsTests
import br.com.ffagundes.restapisample.integrationtests.testcontainers.AbstractIntegrationTest
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

// Para execução desse teste, o Docker precisa estar inicializado!
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest() : AbstractIntegrationTest() {

	@Test
	fun shouldDisplaySwaggerUiPage() {
		val content = given()
			.basePath(ConfigsTests.BASE_PATH)
			.port(ConfigsTests.SERVER_PORT)
			.`when`()
			.get() // verbo a ser executado na chamada
			.then()
			.statusCode(200)
			.extract()
			.body()
			.asString()

		assertTrue(content.contains("Swagger UI"))
	}
}