package com.github.nenadjakic.eav

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@TestPropertySource(locations=["classpath:application.properties"])
class ApplicationTest {

    @Test
    @DirtiesContext
    fun contextLoads() {
    }
}