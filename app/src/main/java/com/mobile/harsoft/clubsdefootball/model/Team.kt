package com.mobile.harsoft.clubsdefootball.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team(
    val idTeam: String?,
    val strTeam: String?,
    val strAlternate: String?,
    val strStadium: String?,
    val strDescriptionEN: String?,
    val strCountry: String?,
    val strTeamBadge: String?,
    val strTeamJersey: String?,
    val strTeamBanner: String?,
    val strLeague: String?,
    val strStadiumThumb: String?,
    val strWebsite: String?,
    val strStadiumDescription: String?,
    val strStadiumLocation: String?,
    val intStadiumCapacity: Int?,
    val strSport:String?
) : Parcelable