package com.github.nenadjakic.eav

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@ComponentScan(basePackages = ["com.github.nenadjakic.eav"])
@TestPropertySource(locations=["classpath:application.properties"])
class ApplicationTest {

    @Test
    fun contextLoads() {
    }
}