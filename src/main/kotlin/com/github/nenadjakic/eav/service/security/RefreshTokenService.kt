package com.github.nenadjakic.eav.service.security

import com.github.nenadjakic.eav.entity.security.RefreshToken
import com.github.nenadjakic.eav.repository.security.RefreshTokenRepository
import com.github.nenadjakic.eav.repository.security.UserRepository
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
open class RefreshTokenService(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    open fun findByUsernameAndToken(username: String, token: String): RefreshToken? {
        return refreshTokenRepository.findByUserUsernameAndTokenAndExpireAtGreaterThanEqual(username, token, OffsetDateTime.now())
    }

    open fun create(username: String): RefreshToken? {
        val user = userRepository.findByUsername(username)
        if (user != null) {
            val refreshToken = RefreshToken(user)
            return refreshTokenRepository.save(refreshToken)
        }
        return null
    }
}