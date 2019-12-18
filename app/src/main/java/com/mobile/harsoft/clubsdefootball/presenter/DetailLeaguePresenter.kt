package com.mobile.harsoft.clubsdefootball.presenter

import com.google.gson.Gson
import com.mobile.harsoft.clubsdefootball.api.ApiRepo
import com.mobile.harsoft.clubsdefootball.api.FootballSportAPI
import com.mobile.harsoft.clubsdefootball.model.response.ResponseLeagues
import com.mobile.harsoft.clubsdefootball.util.CoroutineContextProvider
import com.mobile.harsoft.clubsdefootball.view.LeagueView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailLeaguePresenter(
    private val view: LeagueView,
    private val apiRepo: ApiRepo,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getDetailLeague(idLeague: String?) {
        try {
            GlobalScope.launch(context.main) {
                val data = gson.fromJson(
                    apiRepo.doRequestAsync(FootballSportAPI.getLeague(idLeague)).await(),
                    ResponseLeagues::class.java
                )
                view.leagueData(data.leagues)
            }
        }catch (e:Exception){
            view.showAlert()
        }
    }
}