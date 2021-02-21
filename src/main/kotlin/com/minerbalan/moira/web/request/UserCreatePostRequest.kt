package com.minerbalan.moira.web.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class UserCreatePostRequest(
        @field:NotBlank
        @field:Size(min = 1, max = 10)
        var username: String? = null,
        @field:NotBlank
        @field:Size(min = 1, max = 256)
        var email: String? = null,
        @field:NotBlank
        @field:Size(min = 8, max = 128)
        var password: String? = null
)
