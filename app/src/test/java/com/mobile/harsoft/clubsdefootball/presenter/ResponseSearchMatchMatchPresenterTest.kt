package com.mobile.harsoft.clubsdefootball.presenter

import com.google.gson.Gson
import com.mobile.harsoft.clubsdefootball.api.ApiRepo
import com.mobile.harsoft.clubsdefootball.model.Match
import com.mobile.harsoft.clubsdefootball.model.response.ResponseSearchMatch
import com.mobile.harsoft.clubsdefootball.test.TestContextProvider
import com.mobile.harsoft.clubsdefootball.view.MatchView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ResponseSearchMatchMatchPresenterTest {

    @Mock
    private lateinit var view: MatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepo

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: SearchMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchMatchPresenter(
            view, apiRepository, gson,
            TestContextProvider()
        )
    }

    @Test
    fun getMatch() {
        val matches: MutableList<Match> = mutableListOf()
        val response =
            ResponseSearchMatch(
                matches
            )
        val eventName = "man united"

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    ResponseSearchMatch::class.java
                )
            ).thenReturn(response)

            presenter.getMatch(eventName)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).matchData(matches)
            Mockito.verify(view).hideLoading()
        }
    }
}