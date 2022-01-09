package com.example.users

import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull

@Service
class UserService (@Autowired val userRepository: UserRepository) {
    fun getUsers(): Flow<User>? {
        return userRepository.findAll().asFlow()
    }

    suspend fun save(user: User): User {
        return userRepository.save(user).awaitFirst()
    }

    suspend fun findById(id:String): User? {
        return userRepository.findById(id).awaitFirstOrNull()
    }

    suspend fun deleteById(id: String): Void? {
        return userRepository.deleteById(id).awaitFirstOrNull()
    }
}