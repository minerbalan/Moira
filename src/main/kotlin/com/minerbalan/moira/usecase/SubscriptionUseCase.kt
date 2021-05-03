package com.minerbalan.moira.usecase

interface SubscriptionUseCase {
    fun createSubscription(url: String, name: String?)

    fun existsSubscription(url: String): Boolean
}
