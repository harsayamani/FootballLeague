package com.mobile.harsoft.clubsdefootball.presenter

import com.google.gson.Gson
import com.mobile.harsoft.clubsdefootball.api.ApiRepo
import com.mobile.harsoft.clubsdefootball.model.League
import com.mobile.harsoft.clubsdefootball.model.response.ResponseLeagues
import com.mobile.harsoft.clubsdefootball.test.TestContextProvider
import com.mobile.harsoft.clubsdefootball.view.LeagueView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailLeaguePresenterTest {

    @Mock
    private lateinit var view: LeagueView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepo

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: DetailLeaguePresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailLeaguePresenter(
            view, apiRepository, gson,
            TestContextProvider()
        )
    }

    @Test
    fun getDetailLeague() {
        val leagues: MutableList<League> = mutableListOf()
        val response =
            ResponseLeagues(
                leagues
            )
        val idLeague = "4346"

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    ResponseLeagues::class.java
                )
            ).thenReturn(response)

            presenter.getDetailLeague(idLeague)

            Mockito.verify(view).leagueData(leagues)
        }
    }
}