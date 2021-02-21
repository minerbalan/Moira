package com.minerbalan.moira.javaconfig

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.minerbalan.moira.web.request.AuthenticationRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.authentication.session.ChangeSessionIdAuthenticationStrategy
import org.springframework.security.web.util.matcher.RequestMatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JsonAuthenticationFilter : AbstractAuthenticationProcessingFilter {
    constructor(authenticationManager: AuthenticationManager, defaultFilterProcessesUrl: String) : super(defaultFilterProcessesUrl) {
        this.authenticationManager = authenticationManager
    }

    constructor(authenticationManager: AuthenticationManager, requiresAuthenticationRequestMatcher: RequestMatcher) : super(requiresAuthenticationRequestMatcher) {
        this.authenticationManager = authenticationManager
    }


    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val requestValue = ObjectMapper()
                .registerKotlinModule()
                .readValue<AuthenticationRequest>(request?.inputStream, AuthenticationRequest::class.java)
        val email = requestValue.email?.trim()
        val password = requestValue.password
        val authRequest = UsernamePasswordAuthenticationToken(email, password)
        authRequest.details = authenticationDetailsSource.buildDetails(request)

        setSessionAuthenticationStrategy(ChangeSessionIdAuthenticationStrategy())

        return authenticationManager.authenticate(authRequest)
    }
}
