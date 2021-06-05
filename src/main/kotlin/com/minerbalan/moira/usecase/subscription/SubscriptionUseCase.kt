package com.minerbalan.moira.usecase.subscription

import com.minerbalan.moira.usecase.UseCaseResult

interface SubscriptionUseCase {
    fun createSubscription(userEmail: String, url: String, name: String?): UseCaseResult

    fun existsSubscription(url: String): Boolean
}
