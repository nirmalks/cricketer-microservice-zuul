package com.example.users

data class UserResponse(
        val name: String,
        val email: String,
        val id: String
) {
    companion object {
        fun fromUser(user: User): UserResponse {
            return with(user) {
                UserResponse(name, email, id)
            }
        }
    }
}