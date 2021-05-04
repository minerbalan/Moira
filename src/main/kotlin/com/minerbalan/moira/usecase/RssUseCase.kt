package com.minerbalan.moira.usecase

interface RssUseCase {
    /**
     * 　RSSフィードを取得し、DBに登録する.
     */
    fun fetchArticleFromSubscription()
}
