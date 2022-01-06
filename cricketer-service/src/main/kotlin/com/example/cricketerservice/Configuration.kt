package com.example.cricketerservice

import org.springframework.beans.factory.ObjectFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.support.SpringDecoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.codec.Decoder
import org.springframework.web.reactive.function.client.WebClient


@Configuration
@EnableFeignClients("com.example.feignclients")
class MyConfig {
    @Value("\${team.service.url}")
    lateinit var teamServiceUrl: String

    @Bean
    fun webClient() = WebClient.builder().baseUrl(teamServiceUrl).build()

    @Bean
    fun feignDecoder(): SpringDecoder {
        val messageConverters: ObjectFactory<HttpMessageConverters> = ObjectFactory<HttpMessageConverters> {
            val converters = HttpMessageConverters()
            converters
        }
        return SpringDecoder(messageConverters)
    }
}