package com.mobile.harsoft.clubsdefootball.view

import com.mobile.harsoft.clubsdefootball.model.Klasemen

interface KlasemenView {
    fun klasemenData(data: List<Klasemen>)
    fun showAlert()
}