package com.mobile.harsoft.clubsdefootball

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mobile.harsoft.clubsdefootball.fragments.LeagueFragment
import com.mobile.harsoft.clubsdefootball.fragments.SearchMatchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("PrivateResource")
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.content, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.league -> {
                    addFragment(LeagueFragment())
                }
                R.id.search -> {
                    addFragment(SearchMatchFragment())
                }
            }
            true
        }
        navigation.selectedItemId = R.id.league
    }
}
