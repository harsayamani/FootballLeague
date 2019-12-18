package com.mobile.harsoft.clubsdefootball.presenter

import com.google.gson.Gson
import com.mobile.harsoft.clubsdefootball.api.ApiRepo
import com.mobile.harsoft.clubsdefootball.api.FootballSportAPI
import com.mobile.harsoft.clubsdefootball.model.response.ResponseTeams
import com.mobile.harsoft.clubsdefootball.util.CoroutineContextProvider
import com.mobile.harsoft.clubsdefootball.view.TeamView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamsPresenter(
    private val view: TeamView,
    private val apiRepo: ApiRepo,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getAllTeam(idLeague: String?) {
        view.showLoading()
        view.hideAlert()

        GlobalScope.launch(context.main) {
            try {
                val data = gson.fromJson(
                    apiRepo.doRequestAsync(FootballSportAPI.getAllTeams(idLeague)).await(),
                    ResponseTeams::class.java
                )
                view.teamData(data.teams)
                view.hideLoading()
            } catch (e: Exception) {
                view.hideLoading()
                view.showAlert()
            }
        }
    }
}