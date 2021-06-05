package com.minerbalan.moira.javaconfig

import com.minerbalan.moira.domain.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(private val userEntity: UserEntity) : UserDetails {

    fun getUser(): UserEntity {
        return userEntity
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return ArrayList() //とりあえずロールなし
    }

    override fun getPassword(): String {
        return userEntity.password
    }

    override fun getUsername(): String {
        return userEntity.email
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
