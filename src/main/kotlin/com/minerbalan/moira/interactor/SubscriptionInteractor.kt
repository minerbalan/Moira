package com.minerbalan.moira.interactor

import com.minerbalan.moira.domain.repository.subscription.SubscriptionsRepository
import com.minerbalan.moira.domain.repository.user.UsersRepository
import com.minerbalan.moira.domain.repository.subscription.InsertSubscriptionData
import com.minerbalan.moira.gateway.rss.RssGateway
import com.minerbalan.moira.usecase.subscription.SubscriptionUseCase
import com.minerbalan.moira.usecase.UseCaseResult
import com.rometools.rome.io.FeedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.IOException
import java.time.LocalDateTime
import kotlin.IllegalStateException

@Service
@Transactional
class SubscriptionInteractor(
    val subscriptionsRepository: SubscriptionsRepository,
    val usersRepository: UsersRepository,
    val rssGateway: RssGateway
) : SubscriptionUseCase {
    override fun createSubscription(userEmail: String, url: String, name: String?): UseCaseResult {
        var subscription = subscriptionsRepository.getSubscriptionByUrl(url)
        var subscriptionId = subscription?.id

        val user = usersRepository.findUserFromEmail(userEmail)
        val userId = user?.id ?: throw IllegalStateException()

        if (subscriptionId != null && subscriptionsRepository.isUserSubscribe(userId, subscriptionId)) {
            return InteractorResult.createErrorResult("既に登録されています")
        }

        val feedName: String
        try {
            feedName = rssGateway.fetchFeedTitle(url)
        } catch (e: IOException) {
            return InteractorResult.createErrorResult("URLの形式が正しくありません")
        } catch (e: FeedException) {
            return InteractorResult.createErrorResult("URL先のRSSフィードが認識できない形式です")
        }

        if (subscription == null) {
            val newSubscription = InsertSubscriptionData(
                url = url,
                createdAt = LocalDateTime.now(),
                lastFetchedAt = LocalDateTime.now()
            )
            subscriptionsRepository.insertSubscription(newSubscription)
            subscription = subscriptionsRepository.getSubscriptionByUrl(url)
        }

        subscriptionId = subscription?.id
        if (subscriptionId == null) {
            throw IllegalStateException()
        }

        val subscriptionName = name ?: feedName

        subscriptionsRepository.linkingUserAndSubscription(userId, subscriptionId, subscriptionName, LocalDateTime.now())
        return InteractorResult.createSuccessResult()
    }

    override fun existsSubscription(url: String): Boolean {
        return subscriptionsRepository.existsSubscription(url)
    }
}
