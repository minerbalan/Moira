package com.minerbalan.moira.rss

import com.minerbalan.moira.gateway.ScrapingGateway
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class ScrapingGatewayImpl : ScrapingGateway {
    private val logger = LoggerFactory.getLogger(ScrapingGateway::class.java)

    override fun fetchOgpImageProperties(url: String): String? {
        try {
            val document = Jsoup.connect(url).get()
            val elements = document.select("meta[property~=og:image]")
            val element = elements.first()
            var thumbnailImgUrl = ""
            if (element != null) {
                thumbnailImgUrl = element.attr("content")
            }
            if (thumbnailImgUrl.isBlank()) {
                thumbnailImgUrl = ""
            }
            return thumbnailImgUrl
        } catch (e: IOException) {
            logger.error("OGPプロパティ取得時のエラー", e)
        }
        return null
    }
}
