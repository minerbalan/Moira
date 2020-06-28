package com.minerbalan.moira.repository

import com.minerbalan.moira.entity.Subscription
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubscriptionsRepository : JpaRepository<Subscription?, Long?>