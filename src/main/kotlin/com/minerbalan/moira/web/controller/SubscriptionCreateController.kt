package com.minerbalan.moira.web.controller

import com.minerbalan.moira.usecase.SubscriptionCreateUseCase
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
class SubscriptionCreateController(val subscriptionCreateUseCase: SubscriptionCreateUseCase) {

    @PostMapping("/subscriptions")
    fun createSubscriptionsHandler(@RequestBody @Validated request: SubscriptionPostRequest, bindingResult: BindingResult): ResponseEntity<BasicResponse> {
        val name = request.name
        val url = request.url
        if (bindingResult.hasErrors() || name == null || url == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BasicResponse("Error Message"))
        }

        subscriptionCreateUseCase.createSubscription(name, url)
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponse("Register Success"))
    }
}
