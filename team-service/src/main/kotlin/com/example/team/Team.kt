package com.example.team

import lombok.Data
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
@Data
data class Team (
        @Id val id:String,
        val name: String,
        val baseCountry: String,
        val type: String
)