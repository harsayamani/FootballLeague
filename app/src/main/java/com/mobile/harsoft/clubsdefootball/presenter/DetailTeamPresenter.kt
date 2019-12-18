package com.mobile.harsoft.clubsdefootball.presenter

import com.google.gson.Gson
import com.mobile.harsoft.clubsdefootball.api.ApiRepo
import com.mobile.harsoft.clubsdefootball.api.FootballSportAPI
import com.mobile.harsoft.clubsdefootball.model.response.ResponseTeams
import com.mobile.harsoft.clubsdefootball.util.CoroutineContextProvider
import com.mobile.harsoft.clubsdefootball.view.TeamView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailTeamPresenter(
    private val view: TeamView,
    private val apiRepo: ApiRepo,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getDetailTeam(idTeam: String?) {
        try {
            GlobalScope.launch(context.main) {
                val data = gson.fromJson(
                    apiRepo.doRequestAsync(FootballSportAPI.getDetailTeam(idTeam)).await(),
                    ResponseTeams::class.java
                )
                view.teamData(data.teams)
            }
        } catch (e: Exception) {
            view.showAlert()
        }
    }
}