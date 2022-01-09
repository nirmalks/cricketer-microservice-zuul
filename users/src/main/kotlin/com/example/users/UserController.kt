package com.example.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType

@RestController
@RequestMapping("/api")
class UserController(
        @Autowired
        val userService: UserService
) {
    private val logger: Logger = LoggerFactory.getLogger(UserController::class.java)
    @GetMapping("/users")
    @FlowPreview
    fun getUsers(): Flow<UserResponse> {
        return userService.getUsers().filterNotNull().map { UserResponse.fromUser(it) }
    }

    @GetMapping("/users/{id}")
    suspend fun getUser(@PathVariable("id") id: String): ResponseEntity<UserResponse> {
        val user = userService.findById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(UserResponse.fromUser(user))
    }


    @PostMapping("/users",
        produces= [ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE],
        consumes = [ MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE] )
    suspend fun addUser(@RequestBody userRequest: UserRequest): ResponseEntity<UserResponse> {
        val user = userService.createUser(userRequest)
        logger.info("encr pw ${user.password}")
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