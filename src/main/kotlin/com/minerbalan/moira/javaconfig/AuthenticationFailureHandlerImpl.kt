package com.minerbalan.moira.javaconfig

import org.apache.http.HttpStatus
import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFailureHandlerImpl : AuthenticationFailureHandler {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun onAuthenticationFailure(request: HttpServletRequest?, response: HttpServletResponse?, exception: AuthenticationException?) {
        response?.sendError(HttpStatus.SC_BAD_REQUEST)
    }
}
