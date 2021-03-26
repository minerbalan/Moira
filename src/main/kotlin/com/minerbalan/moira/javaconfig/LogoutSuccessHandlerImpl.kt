package com.minerbalan.moira.javaconfig

import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LogoutSuccessHandlerImpl : LogoutSuccessHandler {
    override fun onLogoutSuccess(request: HttpServletRequest?, response: HttpServletResponse, authentication: Authentication?) {
        if (response.isCommitted) {
            return
        }
        response.status = HttpStatus.OK.value()
    }
}
