package com.example.cricketerservice

import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@EnableEurekaClient
@SpringBootApplication
@Slf4j
@EnableFeignClients
@ComponentScan
class CricketerServiceApplication
	fun main(args: Array<String>) {
		runApplication<CricketerServiceApplication>(*args)
	}
