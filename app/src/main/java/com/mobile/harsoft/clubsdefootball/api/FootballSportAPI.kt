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

    fun getAllTeams(idLeague: String?): String {
        return BuildConfig.BASE + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookup_all_teams.php?id=" + idLeague
    }

    fun getDetailTeam(idTeam: String?): String {
        return BuildConfig.BASE + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupteam.php?id=" + idTeam
    }

    fun getKlasemen(idLeague: String?): String {
        return BuildConfig.BASE + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookuptable.php?l=" + idLeague
    }

    fun getPlayersTeam(team: String?): String {
        return BuildConfig.BASE + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/searchplayers.php?t=" + team
    }

}