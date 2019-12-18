package com.mobile.harsoft.clubsdefootball.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.mobile.harsoft.clubsdefootball.model.FavoriteMatch
import org.jetbrains.anko.db.*

class FavoriteMatchHelper(context: Context) :
    ManagedSQLiteOpenHelper(context, "FavoriteMatch.db", null, 1) {

    companion object {
        private var instance: FavoriteMatchHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): FavoriteMatchHelper {
            if (instance == null) {
                instance = FavoriteMatchHelper(ctx.applicationContext)
            }
            return instance as FavoriteMatchHelper

        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            FavoriteMatch.TABLE_FAVORITE, true,
            FavoriteMatch.EVENT_ID to TEXT + PRIMARY_KEY,
            FavoriteMatch.EVENT_NAME to TEXT,
            FavoriteMatch.EVENT_FILE to TEXT,
            FavoriteMatch.LEAGUE to TEXT,
            FavoriteMatch.SEASON to TEXT,
            FavoriteMatch.SPORT to TEXT,
            FavoriteMatch.ID_LEAGUE to TEXT,
            FavoriteMatch.DESCRIPTION to TEXT,
            FavoriteMatch.HOME_TEAM to TEXT,
            FavoriteMatch.AWAY_TEAM to TEXT,
            FavoriteMatch.HOME_SCORED to INTEGER,
            FavoriteMatch.AWAY_SCORED to INTEGER,
            FavoriteMatch.EVENT_DATE to TEXT,
            FavoriteMatch.EVENT_TIME to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable("TABLE_FAVORITE", true)
    }
}

val Context.database: FavoriteMatchHelper
    get() = FavoriteMatchHelper.getInstance(applicationContext)