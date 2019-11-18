package com.mobile.harsoft.clubsdefootball.ankolayout

import android.graphics.Color
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.constraint.layout.constraintLayout

class LeagueUI : AnkoComponent<ViewGroup> {

    companion object {
        val id_name = 1
        val id_logo = 2
        val id_line = 3
    }

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        cardView {
            lparams(width = matchParent, height = wrapContent) {
                marginStart = dip(5)
                marginEnd = dip(5)
                verticalMargin = dip(5)
            }
            constraintLayout {
                setBackgroundColor(Color.rgb(205, 220, 57))
                imageView {
                    id = id_logo
                }.lparams(width = dip(60), height = dip(60)) {
                    topToTop = PARENT_ID
                    bottomToBottom = PARENT_ID
                    startToStart = PARENT_ID
                    margin = dip(20)
                }

                textView {
                    id = id_name
                    textSize = 17f
                    textColor = Color.rgb(63, 81, 181)
                }.lparams(width = wrapContent, height = wrapContent) {
                    startToEnd = id_logo
                    bottomToBottom = PARENT_ID
                    endToStart = id_line
                    topToTop = PARENT_ID
                    margin = dip(20)
                }

                relativeLayout {
                    id = id_line
                    setBackgroundColor(Color.rgb(63, 81, 181))
                }.lparams(width = 20, height = 200) {
                    topToTop = PARENT_ID
                    endToEnd = PARENT_ID
                    bottomToBottom = PARENT_ID
                }
            }.lparams(width = matchParent, height = wrapContent)
        }

    }
}