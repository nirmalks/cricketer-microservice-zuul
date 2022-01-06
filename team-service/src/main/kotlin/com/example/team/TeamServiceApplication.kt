package com.example.team

import lombok.extern.slf4j.Slf4j
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@Slf4j
@EnableEurekaClient
class TeamServiceApplication

fun main(args: Array<String>) {
    runApplication<TeamServiceApplication>(*args)
}
