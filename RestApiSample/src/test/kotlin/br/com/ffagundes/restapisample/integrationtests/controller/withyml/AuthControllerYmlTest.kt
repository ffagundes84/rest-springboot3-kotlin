package br.com.ffagundes.restapisample.integrationtests.controller.withyml

import br.com.ffagundes.restapisample.config.TestConfigs
import br.com.ffagundes.restapisample.integrationtests.controller.withyml.mapper.YMLMapper
import br.com.ffagundes.restapisample.integrationtests.testcontainers.AbstractIntegrationTest
import br.com.ffagundes.restapisample.integrationtests.vo.AccountCredentialsVO
import br.com.ffagundes.restapisample.integrationtests.vo.TokenVO
import io.restassured.RestAssured.given
import io.restassured.config.EncoderConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.http.ContentType
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.assertNotNull

import org.springframework.boot.test.context.SpringBootTest

// Para execução desses testes, o Docker precisa estar inicializado!
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class) // recomendado utilizar somente em testes integrados.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthControllerYmlTest : AbstractIntegrationTest() {
    private lateinit var tokenVO: TokenVO
    private lateinit var objectMapper: YMLMapper

    @BeforeAll
    fun setupTests(){
        tokenVO = TokenVO()
        objectMapper = YMLMapper()
    }

    @Test
    @Order(0)
    fun loginTest() {
        val user = AccountCredentialsVO(
            username = "leandro",
            password = "admin123"
        )

        tokenVO = given()
            .config(
                RestAssuredConfig
                    .config()
                    .encoderConfig(
                        EncoderConfig.encoderConfig()
                            .encodeContentTypeAs(
                                TestConfigs.CONTENT_TYPE_YML,
                                ContentType.TEXT
                            )
                    )
            )
            .basePath("/auth/signin")
            .port(TestConfigs.SERVER_PORT)
            .accept(TestConfigs.CONTENT_TYPE_YML)
            .contentType(TestConfigs.CONTENT_TYPE_YML)
            .body(user, objectMapper)
            .`when`()
            .post()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(TokenVO::class.java, objectMapper)

        assertNotNull(tokenVO.accessToken)
        assertNotNull(tokenVO.refreshToken)
    }

    @Test
    @Order(1)
    fun refreshTest() {
        tokenVO = given()
            .config(
                RestAssuredConfig
                    .config()
                    .encoderConfig(
                        EncoderConfig.encoderConfig()
                            .encodeContentTypeAs(
                                TestConfigs.CONTENT_TYPE_YML,
                                ContentType.TEXT
                            )
                    )
            )
            .basePath("/auth/refresh")
            .port(TestConfigs.SERVER_PORT)
            .accept(TestConfigs.CONTENT_TYPE_YML)
            .contentType(TestConfigs.CONTENT_TYPE_YML)
            .pathParam("username", tokenVO.username)
            .header(
                TestConfigs.HEADER_PARAM_AUTHORIZATION,
                "Bearer ${tokenVO.refreshToken}"
            )
            .`when`()
            .put("{username}")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(TokenVO::class.java, objectMapper)

        assertNotNull(tokenVO.accessToken)
        assertNotNull(tokenVO.refreshToken)
    }
}