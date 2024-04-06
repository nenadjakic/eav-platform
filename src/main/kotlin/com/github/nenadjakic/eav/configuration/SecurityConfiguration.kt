package com.github.nenadjakic.eav.configuration

import com.github.nenadjakic.eav.security.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
open class SecurityConfiguration(
    private val authenticationProvider: AuthenticationProvider,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

    @Bean
    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
        http.httpBasic { it.disable() }
        http.sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

        http.authorizeHttpRequests {
            it.requestMatchers("/auth/**").permitAll()
            it.anyRequest().permitAll()
        }

        http.authenticationProvider(authenticationProvider)
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

}