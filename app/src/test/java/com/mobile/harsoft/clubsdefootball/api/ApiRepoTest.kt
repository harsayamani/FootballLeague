package com.mobile.harsoft.clubsdefootball.api

import org.junit.Test
import org.mockito.Mockito

class ApiRepoTest {

    @Test
    fun doRequestAsync() {
        val apiRepository = Mockito.mock(ApiRepo::class.java)
        val url =
            "https://www.thesportsdb.com/api/v1/json/1/lookupleague.php?id=4346"
        apiRepository.doRequestAsync(url)
        Mockito.verify(apiRepository).doRequestAsync(url)
    }
}