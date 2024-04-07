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
import java.util.stream.Collectors
import javax.crypto.SecretKey

/**
 * Service class for handling JWT tokens.
 */
@Service
open class JwtService(
    @Value("\${eav-platform.security.jwt.secret-key}") private val secretKey: String,
    @Value("\${eav-platform.security.jwt.access-token.valid-minutes}") private val accessTokenValidMinutes: Long
) {

    open fun createToken(user: SecurityUser): String {
        return createToken(user, mutableMapOf())
    }

    open fun createToken(user: SecurityUser, claims: MutableMap<String, Any>): String {
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

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token The JWT token string.
     * @return An instance of Claims containing all extracted claims from the token.
     * @throws io.jsonwebtoken.JwtException if there is an error while extracting claims from the token.
     */
    open fun extractAllClaims(token: String): Claims {
        return Jwts
            .parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    /**
     * Extracts a specific claim from the JWT token using the provided claims resolver function.
     *
     * @param token The JWT token string.
     * @param claimsResolver The claims resolver function to extract the desired claim.
     * @return The extracted claim value of type T, or null if the claim is not found or extraction fails.
     * @throws io.jsonwebtoken.JwtException if there is an error while extracting the claim.
     * @param T The type of the claim value to extract.
     */
    private fun <T> extractClaim(token: String, claimsResolver: Function1<Claims?, T?>): T? {
        val claims = extractAllClaims(token)
        return claimsResolver.invoke(claims)
    }

    /**
     * Extracts the username from the JWT token.
     *
     * @param token The JWT token string.
     * @return The extracted username as a String, or null if the username is not found.
     * @throws io.jsonwebtoken.JwtException if there is an error while extracting the username.
     */
    private fun extractUserName(token: String): String? {
        return extractClaim(token) { x -> x?.subject }
    }

    /**
     * Checks if the JWT token is expired.
     *
     * @param token The JWT token string.
     * @return true if the token is expired, false otherwise.
     * @throws io.jsonwebtoken.JwtException if there is an error while checking the token expiration.
     */
    private fun isTokenExpired(token: String): Boolean {
        val expireAt = extractClaim(token) { x -> x?.expiration}
        return expireAt?.before(Date()) ?: false
    }

    /**
     * Checks if the JWT token is valid.
     *
     * @param token The JWT token string.
     * @return true if the token is valid, false otherwise.
     * @throws io.jsonwebtoken.JwtException if there is an error while checking the token validity.
     */
    open fun isValid(token: String): Boolean {
        try {
            Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token)
            return !isTokenExpired(token)
        } catch (ex: Exception) {
            return false
        }
    }

    /**
     * Retrieves the signing key used for JWT token generation.
     *
     * @return The signing key as a SecretKey.
     */
    private fun getSignInKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}