package com.minerbalan.moira.javaconfig

import com.minerbalan.moira.config.WebConfig
import org.springframework.security.web.csrf.CsrfException
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CsrfFilterImpl(private val webConfig: WebConfig) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val originHeader = request.getHeader("Origin")
        if (!originHeader.isNullOrEmpty() && !webConfig.allowOrigin.contains(originHeader)) {
            throw CsrfException("Invalid Origin Header")
        }

        filterChain.doFilter(request, response)
    }
}
