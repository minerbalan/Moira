package com.minerbalan.moira.javaconfig

import com.minerbalan.moira.config.WebConfig
import com.minerbalan.moira.usecase.UserAuthUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
open class SpringSecurityConfig(private val webConfig: WebConfig, private val userAuthUseCase: UserAuthUseCase) :
    WebSecurityConfigurerAdapter() {
    //静的ファイルに認証をかけない
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/favicon.ico", "/css/**", "/js/**")
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, "/login").permitAll()
            .antMatchers(HttpMethod.POST, "/users").permitAll()
            .anyRequest().authenticated()

        http.exceptionHandling().accessDeniedHandler(AccessDeniedHandlerImpl())

        val jsonAuthenticationFilter =
            JsonAuthenticationFilter(authenticationManager(), AntPathRequestMatcher("/login", "POST"))
        jsonAuthenticationFilter.setAuthenticationSuccessHandler(AuthenticationSuccessHandlerImpl())
        jsonAuthenticationFilter.setAuthenticationFailureHandler(AuthenticationFailureHandlerImpl())
        http.addFilterBefore(jsonAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        http.addFilterBefore(CsrfFilterImpl(webConfig), CsrfFilter::class.java)

        http
            .logout()
            .logoutUrl("/logout")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")


        http.cors().configurationSource(createCorsConfigSource())
        http.csrf().disable()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService(UserDetailsServiceImpl(userAuthUseCase))
            .passwordEncoder(passwordEncoder())

    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    private fun createCorsConfigSource(): CorsConfigurationSource {
        val corsConfig = CorsConfiguration()
        corsConfig.addAllowedHeader(CorsConfiguration.ALL)
        webConfig.allowOrigin.forEach{corsConfig.addAllowedOrigin(it)}
        corsConfig.addAllowedMethod(CorsConfiguration.ALL)
        corsConfig.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig)

        return source
    }
}
