package com.minerbalan.moira.web.response

data class UserResponse(
    override val isSuccess: Boolean,
    override val message: String,
    val username: String
) : BaseResponse
