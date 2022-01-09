package com.example.users

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration
open class UsersApplication

fun main(args: Array<String>) {
	runApplication<UsersApplication>(*args)
}
