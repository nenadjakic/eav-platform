package com.github.nenadjakic.eav.configuration

import com.github.nenadjakic.eav.security.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.vote.RoleVoter
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
open class SecurityConfiguration(
    private val authenticationProvider: AuthenticationProvider,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

    @Bean
    open fun grantedAuthorityDefaults() : GrantedAuthorityDefaults {
        return GrantedAuthorityDefaults("");
    }

    @Bean
    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
        http.httpBasic { it.disable() }
        http.sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

        http.authorizeHttpRequests {
            it.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**").permitAll()
            it.requestMatchers("/auth/**").permitAll()
            it.anyRequest().authenticated()
        }

        http.authenticationProvider(authenticationProvider)
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}