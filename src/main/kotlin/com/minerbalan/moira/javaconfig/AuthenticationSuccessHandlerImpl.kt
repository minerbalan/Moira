package com.minerbalan.moira.javaconfig

import org.apache.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationSuccessHandlerImpl : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        response?.status = HttpStatus.SC_OK
    }
}
