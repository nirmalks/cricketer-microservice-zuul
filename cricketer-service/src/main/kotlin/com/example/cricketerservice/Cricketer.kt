package com.example.cricketerservice

import lombok.Data
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
@Data
data class Cricketer (
    @Id val id:String,
    val name: String,
    val country: String,
    val highestScore: Number,
    val teamId: String? = null
)