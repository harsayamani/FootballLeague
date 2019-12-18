package com.mobile.harsoft.clubsdefootball

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mobile.harsoft.clubsdefootball.fragments.FavoriteFragment
import com.mobile.harsoft.clubsdefootball.fragments.LeagueFragment
import com.mobile.harsoft.clubsdefootball.fragments.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.league -> {
                    addFragment(LeagueFragment())
                    supportActionBar?.title = "Football League"
                    supportActionBar?.setIcon(R.drawable.logo_soccer)
                }
                R.id.search -> {
                    addFragment(SearchFragment())
                    supportActionBar?.title = "Search"
                    supportActionBar?.setIcon(R.drawable.ic_search_white_24dp)
                }
                R.id.favorite -> {
                    addFragment(FavoriteFragment())
                    supportActionBar?.title = "Favorite"
                    supportActionBar?.setIcon(R.drawable.ic_star_white_24dp)
                }
            }
            true
        }
        navigation.selectedItemId = R.id.league
    }

    @SuppressLint("PrivateResource")
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction().addToBackStack(null)
            .setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.content, fragment, fragment.javaClass.simpleName)
            .commit()
    }
}
