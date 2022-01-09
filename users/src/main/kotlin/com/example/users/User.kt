package com.example.users

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class User (
        val name: String,
        val email: String,
        val password: String
) {
    @Id var id:String = UUID.randomUUID().toString()
}