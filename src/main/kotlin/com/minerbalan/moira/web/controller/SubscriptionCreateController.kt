package com.minerbalan.moira.web.controller

import com.minerbalan.moira.usecase.SubscriptionUseCase
import com.minerbalan.moira.web.request.SubscriptionPostRequest
import com.minerbalan.moira.web.response.BasicResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SubscriptionCreateController(val subscriptionUseCase: SubscriptionUseCase) {

    @PostMapping("/subscriptions")
    fun createSubscriptionsHandler(
        @AuthenticationPrincipal user: UserDetails?,
        @RequestBody @Validated request: SubscriptionPostRequest,
        bindingResult: BindingResult
    ): ResponseEntity<BasicResponse> {
        if (user == null) {
            throw IllegalArgumentException("userがnull") //認証していない場合、通常はSpringSecurityに弾かれる
        }
        val name = request.name
        val url = request.url
        if (bindingResult.hasErrors() || url == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BasicResponse(false, "Error Message"))
        }

        val result = subscriptionUseCase.createSubscription(user.username, url, name)
        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BasicResponse(false, result.getErrorMessage()))
        }

        return ResponseEntity.status(HttpStatus.OK).body(BasicResponse(true, "Register Success"))
    }
}
