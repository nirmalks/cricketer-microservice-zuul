package com.example.cricketerservice

data class CricketerResponse (
        val id:String,
        val name: String,
        val country: String,
        val highestScore: Number,
        val team: Team? = null
) {
    companion object{
        fun fromCricketerAndTeam(cricketer: Cricketer, team: Team?) =
                CricketerResponse(
                        cricketer.id,
                        cricketer.name,
                        cricketer.country,
                        cricketer.highestScore,
                        team
                )
    }
}