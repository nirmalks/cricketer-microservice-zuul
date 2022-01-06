package com.example.team

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface TeamRepository : ReactiveMongoRepository<Team, String?>