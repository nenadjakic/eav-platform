package com.github.nenadjakic.eav.security

import com.github.nenadjakic.eav.security.model.SecurityUser
import com.github.nenadjakic.eav.service.security.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val userService: UserService
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) {
            throw UsernameNotFoundException("User not found.")
        }
        return SecurityUser(userService.findByUsername(username) ?: throw UsernameNotFoundException("User not found."))
    }
}