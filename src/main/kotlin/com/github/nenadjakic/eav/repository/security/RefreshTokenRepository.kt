package com.github.nenadjakic.eav.repository.security

import com.github.nenadjakic.eav.entity.security.RefreshToken
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.time.OffsetDateTime

interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
    @EntityGraph(attributePaths = ["user"])
    fun findByUserUsernameAndTokenAndExpireAtGreaterThanEqual(username: String, token: String, now: OffsetDateTime): RefreshToken?
}