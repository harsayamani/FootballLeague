package com.mobile.harsoft.clubsdefootball.presenter

import com.google.gson.Gson
import com.mobile.harsoft.clubsdefootball.api.ApiRepo
import com.mobile.harsoft.clubsdefootball.api.FootballSportAPI
import com.mobile.harsoft.clubsdefootball.model.response.ResponseKlasemen
import com.mobile.harsoft.clubsdefootball.util.CoroutineContextProvider
import com.mobile.harsoft.clubsdefootball.view.KlasemenView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class KlasemenPresenter(
    private val view: KlasemenView,
    private val apiRepo: ApiRepo,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getKlasemen(idLeague: String?) {
        try {
            GlobalScope.launch(context.main) {
                val data = gson.fromJson(
                    apiRepo.doRequestAsync(FootballSportAPI.getKlasemen(idLeague)).await(),
                    ResponseKlasemen::class.java
                )
                view.klasemenData(data.table)
            }
        } catch (e: Exception) {
            view.showAlert()
        }
    }
}