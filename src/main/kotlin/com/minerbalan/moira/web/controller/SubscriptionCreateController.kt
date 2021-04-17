package com.minerbalan.moira.web.controller

import com.minerbalan.moira.usecase.SubscriptionUseCase
import com.minerbalan.moira.web.request.SubscriptionPostRequest
import com.minerbalan.moira.web.response.BasicResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SubscriptionCreateController(val subscriptionUseCase: SubscriptionUseCase) {

    @PostMapping("/subscriptions")
    fun createSubscriptionsHandler(
        @RequestBody @Validated request: SubscriptionPostRequest,
        bindingResult: BindingResult
    ): ResponseEntity<BasicResponse> {
        val name = request.name
        val url = request.url
        if (bindingResult.hasErrors() || url == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BasicResponse("Error Message"))
        }

        if(!subscriptionUseCase.existsSubscription(url)){
            try {
                subscriptionUseCase.createSubscription(url, name)
            } catch (e: IllegalArgumentException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BasicResponse("url is invalid"))
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(BasicResponse("Register Success"))
    }
}
