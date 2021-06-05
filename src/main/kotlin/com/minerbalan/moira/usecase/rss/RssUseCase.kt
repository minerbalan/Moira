package com.minerbalan.moira.usecase.rss

interface RssUseCase {
    /**
     * 　RSSフィードを取得し、DBに登録する.
     */
    fun fetchArticleFromSubscription()
}
