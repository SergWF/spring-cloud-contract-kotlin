package de.bosch.probe.kt.sccproducerkt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@SpringBootApplication
class SccProducerKtApplication

fun main(args: Array<String>) {
    runApplication<SccProducerKtApplication>(*args)
}

data class Greeting(val name: String, val hello: String, val time: LocalDateTime)


@RestController
@RequestMapping("/greeting", produces = arrayOf(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE))
class GreetingController(val greetingService: GreetingService) {



    @GetMapping
    fun getAll(): List<Greeting> {
        return greetingService.all()
    }

    @PostMapping
    fun hello(@RequestParam name: String) : Greeting {
        return greetingService.sayHello(name)
    }
}

@Service
class GreetingService {

    val helloMap: MutableMap<String, Greeting> = HashMap()

    fun sayHello(name: String): Greeting {
        helloMap.putIfAbsent(name, Greeting(name, "hello", LocalDateTime.now()))
        return helloMap.getValue(name)
    }

    fun all(): List<Greeting> {
        return helloMap.values.toList()
    }
}