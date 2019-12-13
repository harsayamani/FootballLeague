package com.mobile.harsoft.clubsdefootball.presenter

import com.google.gson.Gson
import com.mobile.harsoft.clubsdefootball.api.ApiRepo
import com.mobile.harsoft.clubsdefootball.api.FootballSportAPI
import com.mobile.harsoft.clubsdefootball.model.Events
import com.mobile.harsoft.clubsdefootball.util.CoroutineContextProvider
import com.mobile.harsoft.clubsdefootball.view.MatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailMatchPresenter(
    private val view: MatchView,
    private val apiRepo: ApiRepo,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getDetailMatch(idEvent: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            try {
                val data = gson.fromJson(
                    apiRepo.doRequestAsync(FootballSportAPI.getDetailMatch(idEvent)).await(),
                    Events::class.java
                )

                view.matchData(data.events)
                view.hideLoading()
            } catch (e: Exception) {
                view.hideLoading()
                view.showAlert()
            }
//            apiRepository.api().getDetailMatch(idEvent)?.enqueue(object : Callback<Events?> {
//                override fun onFailure(call: Call<Events?>, t: Throwable) {
//                    view.hideLoading()
//                    view.showAlert()
//                }
//
//                override fun onResponse(call: Call<Events?>, response: Response<Events?>) {
//                    val data = response.body()
//                    if (data != null) {
//                        view.matchData(data.events)
//                        view.hideLoading()
//                    } else {
//                        view.hideLoading()
//                        view.showAlert()
//                    }
//                }
//            })
        }
    }
}