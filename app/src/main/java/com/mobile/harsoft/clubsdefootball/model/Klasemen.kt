package com.mobile.harsoft.clubsdefootball.model

data class Klasemen(
    val name: String?,
    val teamid: String?,
    val played: Int?,
    val win: Int?,
    val draw: Int?,
    val loss: Int?,
    val total: Int,
    val goalsfor: Int?,
    val goalsagainst: Int?,
    val goalsdifference: Int?
)