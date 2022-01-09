package com.example.users

import org.springframework.beans.factory.annotation.Autowired

data class UserRequest (
    val name: String,
    val email: String,
    val password: String
) {
    fun toUser(request: UserRequest): User {
        with(request) {
            return User(name, email, password)
        }
    }
}