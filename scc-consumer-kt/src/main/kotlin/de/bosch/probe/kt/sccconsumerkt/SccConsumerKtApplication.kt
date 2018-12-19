package de.bosch.probe.kt.sccconsumerkt

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDateTime
import javax.ws.rs.QueryParam

@SpringBootApplication
@EnableFeignClients
class SccConsumerKtApplication(val helloClient: HelloClient) : CommandLineRunner {



    override fun run(vararg args: String?) {
        val command = if (args.size >= 1) args[0] else "help"
        val name = if (args.size >= 2) args[1]!! else "unknown"

        when (command) {
            "list" -> showList()
            "hello" -> sayHello(name)
            else -> help()
        }
    }

    private fun help() {
        println("""usage:
            |to show all who said hello:  java -jar scc-consumer.jar list
            |to say hello as John:        java -jar scc-consumer.jar hello John
        """.trimMargin())
    }

    private fun sayHello(name: String) {
        println(helloClient.sayHello(name))
    }

    private fun showList() {
        println(helloClient.getList())
    }
}

data class GreetingDao(val name: String, val hello: String, val time: LocalDateTime)


@FeignClient(name = "greetingsClient")
@RequestMapping("/greeting", produces = arrayOf(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE))
interface HelloClient {
    @GetMapping
    fun getList(): List<GreetingDao>

    @PostMapping
    fun sayHello(@RequestParam name: String): GreetingDao


}

fun main(args: Array<String>) {
    runApplication<SccConsumerKtApplication>(*args)
}

