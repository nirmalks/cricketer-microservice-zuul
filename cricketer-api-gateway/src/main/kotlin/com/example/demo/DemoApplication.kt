package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy

@EnableEurekaClient
@SpringBootApplication
@EnableZuulProxy
class DemoApplication

    fun main(args: Array<String>) {
        runApplication<DemoApplication>(*args)
    }



