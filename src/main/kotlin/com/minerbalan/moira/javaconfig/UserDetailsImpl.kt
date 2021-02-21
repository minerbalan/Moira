package com.minerbalan.moira.javaconfig

import com.minerbalan.moira.domain.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(private val user: User) : UserDetails {

    fun getUser(): User {
        return user
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return ArrayList() //とりあえずロールなし
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.email
    }

    override fun isAccountNonExpired(): Boolean {
        return true //アカウントの有効期限
    }

    override fun isAccountNonLocked(): Boolean {
        return true //アカウント凍結機能なし
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
