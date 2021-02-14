package com.minerbalan.moira.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "moira.web")
data class WebConfig (
        var allowOrigin: List<String> = ArrayList()
        )
