package com.mobile.harsoft.clubsdefootball.api

import com.mobile.harsoft.clubsdefootball.BuildConfig

object FootballSportAPI {
    fun getNextMatch(idLeague: String?): String {
        return BuildConfig.BASE + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventsnextleague.php?id=" + idLeague
    }

    fun getPrevMatch(idLeague: String?): String {
        return BuildConfig.BASE + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventspastleague.php?id=" + idLeague
    }

    fun getDetailMatch(idEvent: String?): String {
        return BuildConfig.BASE + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupevent.php?id=" + idEvent
    }

    fun getSearchMatch(event: String?): String {
        return BuildConfig.BASE + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/searchevents.php?e=" + event
    }

    fun getTeams(teamName: String?): String {
        return BuildConfig.BASE + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/searchteams.php?t=" + teamName
    }

    fun getLeague(idLeague: String?): String{
        return BuildConfig.BASE + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupleague.php?id=" + idLeague
    }

}