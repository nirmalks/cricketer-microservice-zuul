package com.example.cricketerservice

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class CricketerController(private val cricketerService: CricketerService ,
                          private val commonService: CommonService) {
    @GetMapping("/cricketers")
    @FlowPreview
    fun getAllCricketers(): Flow<Cricketer?>? {
        return cricketerService.getAllPlayers()
    }

    @GetMapping("/cricketers/{id}")
    suspend fun getCricketer(@PathVariable("id") id: String): ResponseEntity<CricketerResponse> {
        val cricketer: Cricketer = cricketerService.findById(id) ?:
            return ResponseEntity(HttpStatus.NOT_FOUND)
        val cricketerWithTeam = if (cricketer.teamId != null) {
            val team = commonService.getTeamById(cricketer.teamId)
            CricketerResponse.fromCricketerAndTeam(cricketer, team)
        } else CricketerResponse.fromCricketerAndTeam(cricketer, null)
        return ResponseEntity.ok(cricketerWithTeam)
    }

    @PostMapping("/cricketers")
    suspend fun addCricketer(@RequestBody cricketer: Cricketer): ResponseEntity<Cricketer> {
        return ResponseEntity(cricketerService.save(cricketer),HttpStatus.CREATED)
    }

    @PutMapping("/cricketers/{id}")
    suspend fun updateCricketer(@PathVariable("id") id: String, @RequestBody cricketer: Cricketer): ResponseEntity<Cricketer> {
        val existingCricketer = cricketerService.findById(id) ?:
                return ResponseEntity(HttpStatus.NOT_FOUND)
        val newCricketer = cricketerService.save(existingCricketer.copy(country = cricketer.country, name = cricketer.name,
                    highestScore = cricketer.highestScore))
        return ResponseEntity(newCricketer, HttpStatus.OK)
    }

    @DeleteMapping("/cricketers/{id}")
    suspend fun deleteCricketer(@PathVariable("id") id: String): ResponseEntity<Void> {
        if(cricketerService.findById(id) == null) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        return ResponseEntity(cricketerService.deleteById(id),HttpStatus.OK)
    }
}