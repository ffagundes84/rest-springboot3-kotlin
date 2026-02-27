package br.com.ffagundes.restapisample.integrationtests.testcontainers

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.MapPropertySource
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.lifecycle.Startables
import java.util.stream.Stream

@ContextConfiguration(initializers = [AbstractIntegrationTest.Initializer::class])
open class AbstractIntegrationTest {

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            startContainers()

            val environment = applicationContext.environment
            val testContainers = MapPropertySource(
                "testcontainers", createConnectionConfiguration()
            )
            // a partir das configurações de inicialização dos testes do spring-boot, adicionamos as do testcontainers
            environment.propertySources.addFirst(testContainers)
        }

        companion object {
            private val mySql = MySQLContainer("mysql:8.0.28") // versão que esta na docker-image
            private fun startContainers() {
                // iniciando o container
                Startables.deepStart(Stream.of(mySql)).join()
            }

            private fun createConnectionConfiguration(): MutableMap<String, Any> =
                java.util.Map.of(
                    "spring.datasource.url", mySql.jdbcUrl,
                    "spring.datasource.username", mySql.username,
                    "spring.datasource.password", mySql.password
                )

        }
    }
}