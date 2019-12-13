package com.mobile.harsoft.clubsdefootball.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League(
    val idLeaague: String?,
    val strSport: String?,
    val strLeague: String?,
    val strLeagueAlternate: String?,
    val strDivision: String?,
    val intFormedYear: String?,
    val dateFirstEvent: String?,
    val strCountry: String?,
    val strWebsite: String?,
    val strDescriptionEN: String?
) : Parcelable