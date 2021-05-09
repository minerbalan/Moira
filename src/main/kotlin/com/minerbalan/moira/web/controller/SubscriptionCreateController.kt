package com.minerbalan.moira.web.controller

import com.minerbalan.moira.usecase.SubscriptionUseCase
import com.minerbalan.moira.web.request.SubscriptionPostRequest
import com.minerbalan.moira.web.response.BasicResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class SubscriptionCreateController(val subscriptionUseCase: SubscriptionUseCase) {

    @PostMapping("/subscriptions")
    fun createSubscriptionsHandler(
        @AuthenticationPrincipal user: UserDetails?,
        @RequestBody @Validated request: SubscriptionPostRequest
    ): BasicResponse {
        if (user == null) {
            throw IllegalArgumentException() //認証していない場合、通常はSpringSecurityに弾かれる
        }
        val name = request.name
        val url = request.url ?: throw IllegalArgumentException()

        val result = subscriptionUseCase.createSubscription(user.username, url, name)
        if (!result.isSuccess()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, result.getErrorMessage())
        }

        return BasicResponse(true, "Register Success")
    }
}
