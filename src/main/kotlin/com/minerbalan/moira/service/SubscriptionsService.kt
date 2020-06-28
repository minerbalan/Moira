package com.minerbalan.moira.service

import com.minerbalan.moira.entity.Subscription
import com.minerbalan.moira.repository.SubscriptionsRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class SubscriptionsService(private val subscriptionsRepository: SubscriptionsRepository) {
    val subscriptionList: List<Subscription?>
        get() = subscriptionsRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))

}