package com.example.demo

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.context.annotation.Configuration
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Configuration
class CustomFilter: GlobalFilter {
    val logger: Logger = LoggerFactory.getLogger(CustomFilter::class.java)
    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        logger.info("Path just for test ${exchange.request.path}")
        return chain.filter(exchange).then(Mono.fromRunnable {
            val resp = exchange.response
            logger.info("test resp code ${resp.statusCode}")
        })
    }
}