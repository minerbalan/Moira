package com.minerbalan.moira.web.request

import org.hibernate.validator.constraints.Range
import javax.validation.constraints.Min

data class ArticleGetRequest(
    @field:Range(min = 1, max = 50)
    val limit: Int?,
    @field:Min(0)
    val offset: Int?
)
