package com.mobile.harsoft.clubsdefootball.view

import com.mobile.harsoft.clubsdefootball.model.Team

interface TeamView {
    fun showAlert()
    fun hideAlert()
    fun showLoading()
    fun hideLoading()
    fun teamData(data: List<Team>)
}