package com.example.cricketerservice

import com.example.feignclients.TeamFeignClient
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class CommonService(@Autowired val cricketerRepository: CricketerRepository) {
    val logger = LoggerFactory.getLogger(CommonService::class.java)

    @Autowired
    lateinit var teamFeignClient: TeamFeignClient

    @CircuitBreaker(name = "teamService", fallbackMethod = "getTeamByIdFallback")
    fun getTeamById(id: String): Team? {
        return teamFeignClient.getTeam(id)
    }

    fun getTeamByIdFallback(id: String, th: Throwable): Team? {
        logger.info("throwable ${th.localizedMessage}")
        logger.info("inside fallback method $id")
        return null
    }
}