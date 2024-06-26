package com.github.nenadjakic.eav.security.model

import com.github.nenadjakic.eav.entity.security.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.OffsetDateTime


class  SecurityUser() : UserDetails {
    private var id: Long? = null
    private val authorities: MutableCollection<GrantedAuthority> = mutableListOf()
    private lateinit var username: String
    private lateinit var password: String
    private var enabled = false
    private lateinit var expireAt: OffsetDateTime

    fun setUsername(value: String) {
        username = value
    }

    constructor(user: User) : this() {
        id = user.id
        username = user.username
        password = user.password
        enabled = user.enabled
        expireAt = user.expireAt
        user.roles.forEach { role -> authorities.add(SimpleGrantedAuthority(role.name)) }
    }

    fun addAuthority(authority: GrantedAuthority): Boolean {
        return authorities.add(authority)
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return !OffsetDateTime.now().isAfter(expireAt)
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return enabled
    }
}
