package com.mobile.harsoft.clubsdefootball.view

import com.mobile.harsoft.clubsdefootball.model.Player

interface PlayerView {
    fun showAlert()
    fun hideAlert()
    fun showLoading()
    fun hideLoading()
    fun playerData(data: List<Player>)
}