package com.github.nenadjakic.eav.dto.converter

import com.github.nenadjakic.eav.dto.RegisterRequest
import com.github.nenadjakic.eav.entity.security.User
import org.modelmapper.AbstractConverter
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.OffsetDateTime

class RegisterRequestToUserConverter(private val passwordEncoder: PasswordEncoder) : AbstractConverter<RegisterRequest, User>() {
    override fun convert(source: RegisterRequest?): User {

        val destination = User()
        destination.enabled = false
        destination.emailConfirmed = false
        destination.username = source!!.email
        destination.email = source.email
        destination.password = passwordEncoder.encode(source.password)
        destination.expireAt = OffsetDateTime.now().plusYears(5L)

        return destination
    }
}