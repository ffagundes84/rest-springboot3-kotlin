package br.com.ffagundes.restapisample.integrationtests.controller.withjson

import br.com.ffagundes.restapisample.config.TestConfigs
import br.com.ffagundes.restapisample.integrationtests.testcontainers.AbstractIntegrationTest
import br.com.ffagundes.restapisample.integrationtests.vo.AccountCredentialsVO
import br.com.ffagundes.restapisample.integrationtests.vo.TokenVO
import io.restassured.RestAssured.given
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
class AuthControllerJsonTest : AbstractIntegrationTest(){
    private lateinit var tokenVO: TokenVO

    @BeforeAll
    fun setupTests(){
        tokenVO = TokenVO()
    }

    @Test
    @Order(0)
    fun loginTest() {
        val user = AccountCredentialsVO(
            username = "leandro",
            password = "admin123"
        )

        tokenVO = given()
            .basePath("/auth/signin")
            .port(TestConfigs.SERVER_PORT)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(user)
            .`when`()
            .post()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(TokenVO::class.java)

        assertNotNull(tokenVO.accessToken)
        assertNotNull(tokenVO.refreshToken)
    }

    @Test
    @Order(1)
    fun refreshTest() {
        tokenVO = given()
            .basePath("/auth/refresh")
            .port(TestConfigs.SERVER_PORT)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("username", tokenVO.username)
            .header(
                TestConfigs.HEADER_PARAM_AUTHORIZATION,
                "Bearer ${tokenVO.refreshToken}"
            )
            .`when`()
            .put(
                "{username}"
            )
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(TokenVO::class.java)

        assertNotNull(tokenVO.accessToken)
        assertNotNull(tokenVO.refreshToken)
    }
}