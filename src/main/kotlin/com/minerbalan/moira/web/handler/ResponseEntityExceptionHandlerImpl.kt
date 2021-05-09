package com.minerbalan.moira.web.handler

import com.minerbalan.moira.web.response.ErrorField
import com.minerbalan.moira.web.response.ErrorResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ResponseEntityExceptionHandlerImpl : ResponseEntityExceptionHandler() {
    override fun handleBindException(
        ex: BindException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors = ArrayList<ErrorField>()
        for (error in ex.bindingResult.fieldErrors) {
            errors.add(ErrorField(fieldName = error.field, errorMessage = error.defaultMessage))
        }
        for (error in ex.bindingResult.globalErrors) {
            errors.add(ErrorField(fieldName = "global", errorMessage = error.defaultMessage))
        }
        val errorResponse = ErrorResponse(isSuccess = false, message = "Validation Failed", errors = errors)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }
}
