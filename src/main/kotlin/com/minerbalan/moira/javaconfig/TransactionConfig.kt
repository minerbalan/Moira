package com.minerbalan.moira.javaconfig

import org.jetbrains.exposed.spring.SpringTransactionManager
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.TransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.annotation.TransactionManagementConfigurer
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
class TransactionConfig(val dataSource: DataSource): TransactionManagementConfigurer {
    override fun annotationDrivenTransactionManager(): TransactionManager {
        return SpringTransactionManager(dataSource)
    }
}
