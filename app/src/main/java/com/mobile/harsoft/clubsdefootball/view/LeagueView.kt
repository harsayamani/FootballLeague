package com.mobile.harsoft.clubsdefootball.view

import com.mobile.harsoft.clubsdefootball.model.League

interface LeagueView {
    fun leagueData(data: List<League?>)
    fun showAlert()
}