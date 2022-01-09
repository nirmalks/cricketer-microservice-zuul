package com.example.users

data class UserRequest (
    val name: String,
    val email: String,
    val password: String
) {
    fun toUser(request: UserRequest): User {
        return User(name, email, password)
    }
}