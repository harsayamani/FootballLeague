package com.mobile.harsoft.clubsdefootball.presenter

import com.google.gson.Gson
import com.mobile.harsoft.clubsdefootball.api.ApiRepo
import com.mobile.harsoft.clubsdefootball.api.FootballSportAPI
import com.mobile.harsoft.clubsdefootball.model.Search
import com.mobile.harsoft.clubsdefootball.util.CoroutineContextProvider
import com.mobile.harsoft.clubsdefootball.view.MatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchMatchPresenter(
    private val view: MatchView,
    private val apiRepo: ApiRepo,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getMatch(eventName: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {

            try {
                val data = gson.fromJson(
                    apiRepo.doRequestAsync(FootballSportAPI.getSearchMatch(eventName)).await(),
                    Search::class.java
                )

                view.matchData(data.event)
                view.hideLoading()
            } catch (e: Exception) {
                view.hideLoading()
            }

//            apiRepository.api().getSearchEvent(eventName)?.enqueue(object : Callback<Search?> {
//                override fun onFailure(call: Call<Search?>, t: Throwable) {
//                    view.showAlert()
//                }
//
//                override fun onResponse(call: Call<Search?>, response: Response<Search?>) {
//                    val data = response.body()
//                    try {
//                        if (data != null) {
//                            view.matchData(data.event)
//                        } else {
//                            view.showAlert()
//                        }
//                    }catch (e: Exception){
//                        view.showAlert()
//                    }
//                }
//            })
        }
    }
}