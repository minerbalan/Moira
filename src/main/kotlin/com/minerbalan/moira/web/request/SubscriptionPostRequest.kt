package com.minerbalan.moira.web.request

import org.hibernate.validator.constraints.URL
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class SubscriptionPostRequest(
    @field:Size(min = 1, max = 255)
    var name: String? = null,
    @field:NotBlank
    @field:Size(min = 1, max = 2048)
    var url: String? = null
)
