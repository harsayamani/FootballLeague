package com.mobile.harsoft.clubsdefootball.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Match(
    val idEvent: String?,
    val strEvent: String?,
    val strFileName: String?,
    val strLeague: String?,
    val strSeason: String?,
    val strSport: String?,
    val idLeague: String?,
    val strDescription: String?,
    val strHomeTeam: String?,
    val strAwayTeam: String?,
    val intHomeScore: Int?,
    val intAwayScore: Int?,
    val dateEvent: String?,
    val strTime: String?
) : Parcelable