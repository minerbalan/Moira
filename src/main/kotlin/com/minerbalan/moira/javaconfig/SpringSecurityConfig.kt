package com.minerbalan.moira.javaconfig

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class SpringSecurityConfig : WebSecurityConfigurerAdapter() {
    //静的ファイルに認証をかけない
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/favicon.ico", "/css/**", "/js/**")
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests().anyRequest().permitAll()
        http.csrf().ignoringAntMatchers("/api/**")
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
