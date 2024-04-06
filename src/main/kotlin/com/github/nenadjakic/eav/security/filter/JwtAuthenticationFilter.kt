package com.github.nenadjakic.eav.security.filter

import com.github.nenadjakic.eav.security.JwtService
import com.github.nenadjakic.eav.security.model.SecurityUser
import io.jsonwebtoken.Claims
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.ObjectUtils
import org.springframework.web.filter.OncePerRequestFilter


/**
 * Custom JWT authentication filter.
 *
 * This filter is responsible for intercepting incoming HTTP requests and processing JWT authentication.
 */
class JwtAuthenticationFilter(private val jwtService: JwtService) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (!hasAuthorizationBearer(request)) {
            return
        }
        val token = getAccessToken(request)

        if (!jwtService.isValid(token)) {
            filterChain.doFilter(request, response)
            return
        }

        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);
    }

    private fun setAuthenticationContext(token: String, request: HttpServletRequest) {
        val userDetails: UserDetails = getUserDetails(token)
        val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun hasAuthorizationBearer(request: HttpServletRequest): Boolean {
        val header = request.getHeader("Authorization");
        return ObjectUtils.isEmpty(header) && header.startsWith("Bearer")
    }

    private fun getAccessToken(request: HttpServletRequest): String {
        val header = request.getHeader("Authorization")
        return header.split(" ")[1].trim()
    }

    private fun getUserDetails(token: String): UserDetails {
        val userDetails = SecurityUser()
        val claims = jwtService.extractAllClaims(token)
        userDetails.username = claims.get(Claims.SUBJECT) as String
        val anyRoles = claims["roles"]
        if (anyRoles != null && anyRoles is List<*>) {
            anyRoles.filterIsInstance<String>().forEach { it -> userDetails.addAuthority(SimpleGrantedAuthority(it)) }
        }
        userDetails.username = claims.get(Claims.SUBJECT) as String

        return userDetails
    }
}