package com.github.nenadjakic.eav.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@Configuration
@EnableJpaRepositories(basePackages = ["com.github.nenadjakic.eav.repository.*"])
open class MainConfiguration(
    private val userDetailsService: UserDetailsService,

) {

    @Bean
    @Throws(Exception::class)
    open fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }
    @Bean
    open fun authenticationProvider(userDetailsService: UserDetailsService?): AuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setUserDetailsService(userDetailsService)
        provider.setPasswordEncoder(BCryptPasswordEncoder())
        return provider
    }
}