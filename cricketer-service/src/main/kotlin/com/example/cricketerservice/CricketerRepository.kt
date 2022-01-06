package com.example.cricketerservice

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface CricketerRepository : ReactiveMongoRepository<Cricketer, String?>