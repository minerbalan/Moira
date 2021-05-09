package com.minerbalan.moira.web.response

data class ErrorResponse(override val isSuccess: Boolean, override val message: String, val errors: List<ErrorField>) :
    BaseResponse

data class ErrorField(val fieldName: String, val errorMessage: String?)
