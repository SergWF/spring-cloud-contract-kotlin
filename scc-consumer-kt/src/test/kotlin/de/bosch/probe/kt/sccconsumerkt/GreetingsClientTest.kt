package de.bosch.probe.kt.sccconsumerkt

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureStubRunner(
        ids = arrayOf("de.bosch.probe.kt:scc-producer-kt:+:stubs:8090"),
        stubsMode = StubRunnerProperties.StubsMode.LOCAL)
class GreetingsClientTest {
    val expected = GreetingDao("john", "hello", LocalDateTime.parse("2018-12-12T10:45:55"))
    val expected2 = GreetingDao("jane", "Hi", LocalDateTime.parse("2018-12-12T11:45:55"))
    val expected3 = GreetingDao("ken", "Hallo", LocalDateTime.parse("2018-12-12T12:45:55"))

    @Autowired
    lateinit var helloClient: HelloClient

    @Test
    fun shouldReturnJohnHello() {
        assertThat(helloClient.sayHello(expected.name)).isEqualTo(expected)
    }

    @Test
    fun shouldReturnList() {
        assertThat(helloClient.getList()).containsExactly(expected, expected2, expected3)
    }
}