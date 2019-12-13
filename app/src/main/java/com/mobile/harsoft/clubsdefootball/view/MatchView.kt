package com.mobile.harsoft.clubsdefootball.view

import com.mobile.harsoft.clubsdefootball.model.Match

interface MatchView {
    fun showAlert()
    fun hideAlert()
    fun showLoading()
    fun hideLoading()
    fun matchData(data: List<Match>)
}