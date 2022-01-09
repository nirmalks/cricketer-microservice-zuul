package com.example.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

@RestController
@RequestMapping("/api")
class UserController(
        @Autowired
        val userService: UserService
) {

    @GetMapping("/users")
    @FlowPreview
    fun getUsers(): Flow<UserResponse>? {
        return userService.getUsers()?.filterNotNull()?.map { UserResponse.fromUser(it) }
    }

    @GetMapping("/users/{id}")
    suspend fun getUser(@PathVariable("id") id: String): ResponseEntity<UserResponse> {
        val user = userService.findById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(UserResponse.fromUser(user))
    }

    @PostMapping("/users")
    suspend fun addUser(@RequestBody userRequest: UserRequest): ResponseEntity<UserResponse> {
        val user = userService.save(userRequest.toUser(userRequest))
        return ResponseEntity(UserResponse.fromUser(user), HttpStatus.CREATED)
    }

    @PutMapping("/users/{id}")
    suspend fun updateUser(@PathVariable("id") id: String, @RequestBody user: User): ResponseEntity<User> {
        val existingUser = userService.findById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val newUser = userService.save(existingUser.copy(password = user.password, name = user.name,
                email = user.email))
        return ResponseEntity(newUser, HttpStatus.OK)
    }

    @DeleteMapping("/users/{id}")
    suspend fun deleteUser(@PathVariable("id") id: String): ResponseEntity<Void> {
        if(userService.findById(id) == null) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        return ResponseEntity(userService.deleteById(id),HttpStatus.OK)
    }
}