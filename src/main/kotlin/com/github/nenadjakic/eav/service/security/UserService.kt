package com.github.nenadjakic.eav.service.security

import com.github.nenadjakic.eav.entity.security.User
import com.github.nenadjakic.eav.repository.security.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username).orElse(null)
    }
}