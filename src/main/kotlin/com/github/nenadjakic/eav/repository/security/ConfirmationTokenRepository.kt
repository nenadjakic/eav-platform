package com.github.nenadjakic.eav.repository.security

import com.github.nenadjakic.eav.entity.security.ConfirmationToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ConfirmationTokenRepository : JpaRepository<ConfirmationToken, Long> {
    fun findByToken(token: String): Optional<ConfirmationToken>
}