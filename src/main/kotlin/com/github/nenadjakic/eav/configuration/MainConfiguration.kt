package com.github.nenadjakic.eav.configuration

import com.github.nenadjakic.eav.dto.converter.*
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
import org.springframework.cache.CacheManager
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*


@Configuration
@EnableJpaRepositories(basePackages = ["com.github.nenadjakic.eav.repository"])
@EnableAsync
open class MainConfiguration(
    private val userDetailsService: UserDetailsService,
    private val passwordEncoder: PasswordEncoder
) {

    private fun createAPIKeyScheme(): SecurityScheme {
        return SecurityScheme().type(SecurityScheme.Type.HTTP)
            .bearerFormat("JWT")
            .scheme("bearer")
    }

    @Bean
    fun openAPI(@Value("\${application.name}") name: String?, @Value("\${application.version}") version: String?): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title(name)
                    .version(version)
            )
            .addSecurityItem(SecurityRequirement().addList("Bearer_Authentication"))
            .components(Components().addSecuritySchemes("Bearer_Authentication", createAPIKeyScheme()))
    }

    @Bean
    open fun cacheManager() : CacheManager {
        return ConcurrentMapCacheManager("roles", "attributePermissions")
    }

    @Bean
    open fun modelMapper(): ModelMapper {
        val modelMapper = ModelMapper()
        modelMapper.addConverter(RegisterRequestToUserConverter(passwordEncoder))
        modelMapper.addConverter(EntityToEntityResponseConverter())
        modelMapper.addConverter(AttributeAddRequestToAttributeConverter())
        modelMapper.addConverter(AttributeToAttributeResponseConverter())
        modelMapper.addConverter(AttributeUpdateRequestToAttributeConverter())
        modelMapper.addConverter(AttributeValueToAttributeValueResponseConverter())
        modelMapper.addConverter(EntityTypeToEntityTypeResponseConverter())
        modelMapper.addConverter(AttributeValueAddRequestToAttributeValueConverter())

        return modelMapper
    }

    @Bean
    @Throws(Exception::class)
    open fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }

    @Bean
    open fun authenticationProvider(): AuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setUserDetailsService(userDetailsService)
        provider.setPasswordEncoder(passwordEncoder)
        return provider
    }
}