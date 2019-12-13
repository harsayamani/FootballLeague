package com.mobile.harsoft.clubsdefootball.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LeagueLocal(
    val league_id: String?,
    val league_name: String?,
    val league_logo: Int?,
    val league_desc: String?
) : Parcelable
