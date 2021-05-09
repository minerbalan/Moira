package com.minerbalan.moira.web.response

data class BasicResponse(override val isSuccess: Boolean, override val message: String) : BaseResponse
