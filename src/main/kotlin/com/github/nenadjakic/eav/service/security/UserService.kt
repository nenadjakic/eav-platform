package com.github.nenadjakic.eav.service.security

import com.github.nenadjakic.eav.entity.security.ConfirmationToken
import com.github.nenadjakic.eav.entity.security.User
import com.github.nenadjakic.eav.exception.EntityExistsException
import com.github.nenadjakic.eav.exception.GeneralException
import com.github.nenadjakic.eav.repository.security.ConfirmationTokenRepository
import com.github.nenadjakic.eav.repository.security.UserRepository
import com.github.nenadjakic.eav.service.EMailService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
open class UserService(
    private val userRepository: UserRepository,
    private val confirmationTokenRepository: ConfirmationTokenRepository,
    private val eMailService: EMailService
) {

    open fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }

    @Transactional
    open fun save(user: User): User {
        if (userRepository.existsByUsername(user.username)) {
            throw EntityExistsException("Username already exists.")
        }
        val savedUser = userRepository.save(user)

        val confirmationToken = ConfirmationToken(savedUser)
        confirmationTokenRepository.save(confirmationToken)

        // async send mail
        eMailService.send(
            user.email,
            "Complete Registration!",
            "To confirm your account, please click here confirm your e-mail: " +
                    "http://localhost:8080/auth/confirm-email?token=" + confirmationToken.token
        )
        return savedUser
    }

    open fun confirmEmail(token: String) {
        val confirmationToken = confirmationTokenRepository.findByToken(token).orElse(null)
        if (confirmationToken != null) {
            if (confirmationToken.expireAt.isBefore(OffsetDateTime.now())) {
                throw GeneralException("Expiration url was expired.")
            }
            val user = confirmationToken.user
            user.enabled = true
            user.emailConfirmed = true
            userRepository.save(user)
        } else {
            throw GeneralException("Confirmation url is incorrect.")
        }
    }
}