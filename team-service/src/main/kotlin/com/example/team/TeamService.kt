package com.example.team

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TeamService(@Autowired val teamRepository: TeamRepository) {
    suspend fun save(team: Team): Team {
        return teamRepository.save(team).awaitFirst()
    }

    suspend fun findById(id:String): Team? {
        return teamRepository.findById(id).awaitFirstOrNull()
    }

    @FlowPreview
    fun getAllPlayers(): Flow<Team> {
        return teamRepository.findAll().asFlow()
    }

    suspend fun deleteById(id: String): Void? {
        return teamRepository.deleteById(id).awaitFirstOrNull()
    }
}