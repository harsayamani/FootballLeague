package com.mobile.harsoft.clubsdefootball.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.mobile.harsoft.clubsdefootball.model.FavoriteTeam
import org.jetbrains.anko.db.*

class FavoriteTeamHelper(context: Context) :
    ManagedSQLiteOpenHelper(context, "FavoriteTeam.db", null, 1) {

    companion object {
        private var instance: FavoriteTeamHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): FavoriteTeamHelper {
            if (instance == null) {
                instance = FavoriteTeamHelper(ctx.applicationContext)
            }
            return instance as FavoriteTeamHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            FavoriteTeam.TABLE_FAVORITE, true,
            FavoriteTeam.TEAM_ID to TEXT + PRIMARY_KEY,
            FavoriteTeam.TEAM_NAME to TEXT,
            FavoriteTeam.TEAM_ALTERNATE to TEXT,
            FavoriteTeam.TEAM_STADIUM to TEXT,
            FavoriteTeam.TEAM_DESC to TEXT,
            FavoriteTeam.TEAM_COUNTRY to TEXT,
            FavoriteTeam.TEAM_BADGE to TEXT,
            FavoriteTeam.TEAM_JERSEY to TEXT,
            FavoriteTeam.TEAM_BANNER to TEXT,
            FavoriteTeam.TEAM_LEAGUE to TEXT,
            FavoriteTeam.TEAM_STADIUM_THUMB to TEXT,
            FavoriteTeam.TEAM_WEBSITE to TEXT,
            FavoriteTeam.TEAM_STADIUM_DESC to TEXT,
            FavoriteTeam.TEAM_STADIUM_LOC to TEXT,
            FavoriteTeam.TEAM_STADIUM_CAPACITY to INTEGER,
            FavoriteTeam.TEAM_SPORT to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.dropTable("TABLE_FAVORITE_TEAM", true)
    }
}

val Context.databaseTeam: FavoriteTeamHelper
    get() = FavoriteTeamHelper.getInstance(applicationContext)