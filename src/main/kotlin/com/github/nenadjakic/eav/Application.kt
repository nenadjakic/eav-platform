package com.github.nenadjakic.eav

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
open class Application : CommandLineRunner {
    override fun run(vararg args: String?) {
        TODO("Not yet implemented")
    }

}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}