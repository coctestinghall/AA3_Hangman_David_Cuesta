package com.davidcuesta.hangman

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.davidcuesta.hangman.databinding.ActivityMainBinding
import com.davidcuesta.hangman.ranking.RankingFragment
import com.davidcuesta.hangman.ranking.RankingViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    private lateinit var binding: ActivityMainBinding
   // private val rVM: RankingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cont = intent.extras


        if (FirebaseAuth.getInstance().currentUser?.isAnonymous == false) {

            val userMail = FirebaseAuth.getInstance().currentUser?.email.toString()
            db.collection("users").document(userMail).get().addOnSuccessListener {

                setup(it.get("username").toString())
            }
            db.collection("users").document(userMail).get().addOnFailureListener {
                setup("Anonymous")
            }

        } else {
            FirebaseAuth.getInstance().signInAnonymously()
            setup("Anonymous")

        }


        //Lanzando una primera analytic
        val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("menu", "User entered menu")
        analytics.logEvent("init_screen", bundle)


        configButton.setOnClickListener() {

            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)


        }

        rankingButton.setOnClickListener() {


            supportFragmentManager.beginTransaction().apply {
                if (!RankingFragment().isAdded)
                    add(rankingFrameLayout.id, RankingFragment(), "FragmentRanking")
                else
                    show(RankingFragment())
                commit()

            }

            //configButton.visibility = View.GONE






        }

        playButton.setOnClickListener(){
            val intent = Intent(this, PlayActivity::class.java)
            startActivity(intent)

        }

    }

    private fun setup(t: String = "Anonymous") {
        title = "Home"
        if (t.isNotEmpty())
            userText.text = t
        else
            userText.text = "Anonymous"

    }

}