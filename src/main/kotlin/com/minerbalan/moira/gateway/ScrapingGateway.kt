package com.minerbalan.moira.gateway

interface ScrapingGateway {
    fun fetchOgpImageProperties(url: String): String?
}
