package com.github.nenadjakic.eav.security

import com.github.nenadjakic.eav.security.model.SecurityUser
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value("\${eav-platform.security.jwt.secret-key}") private val secretKey: String,
    @Value("\${eav-platform.security.jwt.access-token.valid-minutes}") private val accessTokenValidMinutes: Long
) {

    fun createToken(user: SecurityUser, claims: MutableMap<String, Any>): String {
        val created = Date(OffsetDateTime.now().toEpochSecond())
        val expireAt = Date(OffsetDateTime.now().toEpochSecond() + (accessTokenValidMinutes * 60))

        val roles = user.authorities.stream().map { it.authority } .collect(Collectors.toList())
        claims["roles"] = roles

        return Jwts
            .builder()
            .claims(claims)
            .subject(user.username)
            .issuedAt(created)
            .expiration(expireAt)
            .signWith(getSignInKey())
            .compact()
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts
            .parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    fun <T> extractClaim(token: String, claimsResolver: Function1<Claims?, T?>): T? {
        val claims = extractAllClaims(token)
        return claimsResolver.invoke(claims)
    }

    fun extractUserName(token: String): String? {
        return extractClaim(token) { x -> x?.subject }
    }

    fun isTokenExpired(token: String): Boolean {
        val expireAt = extractClaim(token) { x -> x?.expiration}
        return expireAt?.before(Date()) ?: false
    }

    fun isValid(token: String): Boolean {
        try {
            Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token)
            return !isTokenExpired(token)
        } catch (ex: Exception) {
            return false
        }
    }

    private fun getSignInKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}