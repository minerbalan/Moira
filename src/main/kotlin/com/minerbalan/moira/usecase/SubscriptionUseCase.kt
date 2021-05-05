package com.minerbalan.moira.usecase

interface SubscriptionUseCase {
    fun createSubscription(userEmail: String, url: String, name: String?): UseCaseResult

    fun existsSubscription(url: String): Boolean
}
