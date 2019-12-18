package com.mobile.harsoft.clubsdefootball.presenter

import com.google.gson.Gson
import com.mobile.harsoft.clubsdefootball.api.ApiRepo
import com.mobile.harsoft.clubsdefootball.api.FootballSportAPI
import com.mobile.harsoft.clubsdefootball.model.response.ResponsePlayers
import com.mobile.harsoft.clubsdefootball.util.CoroutineContextProvider
import com.mobile.harsoft.clubsdefootball.view.PlayerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayersPresenter(
    private val view: PlayerView,
    private val apiRepo: ApiRepo,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getPlayers(idTeam: String?) {
        view.showLoading()
        view.hideAlert()

        GlobalScope.launch(context.main) {
            try {
                val data = gson.fromJson(
                    apiRepo.doRequestAsync(FootballSportAPI.getPlayersTeam(idTeam)).await(),
                    ResponsePlayers::class.java
                )

                view.playerData(data.player)
                view.hideLoading()
            } catch (e: Exception) {
                view.hideLoading()
                view.showAlert()
            }
        }
    }
}