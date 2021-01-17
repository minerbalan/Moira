package com.minerbalan.moira.service

import com.minerbalan.moira.domain.entity.Article
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class OgpService {
    /**
     * リストで渡されたArticleリストのOGPサムネイルを取得し、設定する.
     *
     * @param articleList OGPサムネイルを取得したいArticleリスト
     */
    fun getOgpProperties(articleList: List<Article?>): List<Article?> {
        for (article in articleList) {
            try {
                if(article == null) continue;
                val document = Jsoup.connect(article.url).get()
                val elements = document.select("meta[property~=og:image]")
                val element = elements.first()
                var thumbnailImgUrl = ""
                if (element != null) {
                    thumbnailImgUrl = element.attr("content")
                }
                if (thumbnailImgUrl.isBlank()) {
                    thumbnailImgUrl = ""
                }
                article.thumbnail = thumbnailImgUrl
            } catch (e: IOException) {
                logger.error("OGPプロパティ取得時のエラー", e)
            }
        }
        return articleList
    }

    companion object {
        private val logger = LoggerFactory.getLogger(OgpService::class.java)
    }
}
