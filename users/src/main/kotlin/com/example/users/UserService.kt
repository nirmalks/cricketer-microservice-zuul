package com.example.users

import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Service
class UserService (
    @Autowired val userRepository: UserRepository,
    @Autowired val bcryptEncoder: BCryptPasswordEncoder) {

    fun getUsers(): Flow<User> {
        return userRepository.findAll().asFlow()
    }

    suspend fun save(user: User): User {
        return userRepository.save(user).awaitFirst()
    }

    suspend fun createUser(user: UserRequest): User {
        return save(User(user.name, user.email, bcryptEncoder.encode(user.password)))
    }

    suspend fun findById(id:String): User? {
        return userRepository.findById(id).awaitFirstOrNull()
    }

    suspend fun deleteById(id: String): Void? {
        return userRepository.deleteById(id).awaitFirstOrNull()
    }
}