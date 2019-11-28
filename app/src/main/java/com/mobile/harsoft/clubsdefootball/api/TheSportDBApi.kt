package com.mobile.harsoft.clubsdefootball.api

import com.mobile.harsoft.clubsdefootball.model.Events
import com.mobile.harsoft.clubsdefootball.model.Search
import com.mobile.harsoft.clubsdefootball.model.Teams
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface TheSportDBApi {

    @GET("eventsnextleague.php")
    fun getNextMatch(
        @Query("id") idLeague: String?
    ): Call<Events?>?

    @GET("eventspastleague.php")
    fun getPrevMatch(
        @Query("id") idLeague: String?
    ): Call<Events?>?

    @GET("lookupevent.php")
    fun getDetailMatch(
        @Query("id") idMatch: String?
    ): Call<Events?>?

    @GET("searchteams.php")
    fun getTeam(
        @Query("t") teamName: String?
    ): Call<Teams?>?

    @GET("searchevents.php")
    fun getSearchEvent(
        @Query("e") search: String?
    ): Call<Search?>?

}