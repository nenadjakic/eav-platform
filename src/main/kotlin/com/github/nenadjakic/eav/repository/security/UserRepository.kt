package com.github.nenadjakic.eav.repository.security

import com.github.nenadjakic.eav.entity.security.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun existsByUsername(username: String): Boolean
}