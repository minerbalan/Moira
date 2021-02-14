package com.minerbalan.moira.javaconfig

import com.minerbalan.moira.config.WebConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SpringSecurityConfig(val webConfig: WebConfig) : WebSecurityConfigurerAdapter() {
    //静的ファイルに認証をかけない
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/favicon.ico", "/css/**", "/js/**")
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests().anyRequest().permitAll()
        http.csrf().disable()
        http.cors().configurationSource(createCorsConfigSource())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    private fun createCorsConfigSource(): CorsConfigurationSource {
        val corsConfig = CorsConfiguration()
        corsConfig.addAllowedHeader(CorsConfiguration.ALL)
        corsConfig.allowedOrigins = webConfig.allowOrigin

        val source = UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig)

        return source
    }
}
