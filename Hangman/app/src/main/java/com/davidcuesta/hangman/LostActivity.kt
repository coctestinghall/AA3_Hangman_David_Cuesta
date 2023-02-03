package com.davidcuesta.hangman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.davidcuesta.hangman.menus.LoginFragment
import com.davidcuesta.hangman.ranking.RankingFragment
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_lost.*
import kotlinx.android.synthetic.main.activity_main.*

class LostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost)

        adLButton.setOnClickListener(){

        }
        pAgLButton.setOnClickListener(){
            val intent = Intent(this, PlayActivity::class.java)
            startActivity(intent)
        }
        rankingLButton.setOnClickListener(){
            supportFragmentManager.beginTransaction().apply {
                if (!RankingFragment().isAdded)
                    add(rankingLFrameLayout.id, RankingFragment(), "FragmentRanking")
                else
                    show(RankingFragment())
                commit()

            }
        }
        menuLButton.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



    }
}