package com.example.users

import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import reactor.core.publisher.Mono
import com.example.users.User as UserEntity

@Service
class UserService (
    @Autowired val userRepository: UserRepository
): UserDetailsService {

    fun getUsers(): Flow<UserEntity> {
        return userRepository.findAll().asFlow()
    }

    suspend fun save(user: UserEntity): UserEntity {
        return userRepository.save(user).awaitFirst()
    }

    suspend fun createUser(user: UserRequest): UserEntity {
        return save(UserEntity(user.name, user.email, BCryptPasswordEncoder().encode(user.password)))
    }

    suspend fun findById(id: String): UserEntity? {
        return userRepository.findById(id).awaitFirstOrNull()
    }

    suspend fun deleteById(id: String): Void? {
        return userRepository.deleteById(id).awaitFirstOrNull()
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user: com.example.users.User = userRepository.findByEmail(username)?.block() ?: throw UsernameNotFoundException(username)
        return User(user.email, user.password, true, true, true, true, listOf())
    }

    fun getUserByEmail(email: String): Mono<UserEntity> {
        return userRepository.findByEmail(email) ?: throw UsernameNotFoundException(email)
    }

    suspend fun deleteAll() = userRepository.deleteAll()
}