package com.davidcuesta.hangman

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.davidcuesta.hangman.databinding.ActivityPlayBinding
import com.davidcuesta.hangman.utils.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class PlayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayBinding

    //private var hangmanState = HangmanResponse("", "", 0, false)
    private var score = 0
    private var game = HangmanNewGameResponse("", "", "", "", "")
    val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
    val bundle = Bundle()

    val db = Firebase.firestore
    val rtdb =
        FirebaseDatabase.getInstance("https://hangman-davidcuesta-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference("Users")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)





        newGame("en", "8")

        //level_start analytics
        bundle.putInt("level", game.word.length)
        analytics.logEvent("level_start", bundle)

        sendGuessButton.setOnClickListener() {

            sendGuess(inputZoneText.text.toString())
            inputZoneText.text.clear()


        }

    }


    private fun getAPI(): Retrofit {

        return Retrofit.Builder().baseUrl("http://hangman.enti.cat:5002/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    //WE USE COROUTINES TO SEND INPUT CALLS
    private fun newGame(lanQuery: String, maxTriesQuery: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<HangmanNewGameResponse> =
                getAPI().create(HangmanAPIService::class.java)
                    .callNewGameApi("new?lang=$lanQuery&maxTries=$maxTriesQuery")
            val content: HangmanNewGameResponse? = call.body()
            runOnUiThread {
                if (call.isSuccessful) {

                    game.token = content?.token ?: "no token"
                    game.word = content?.word ?: "no word"
                    wordGuessedText.text = game.word
                    tokenText.text = score.toString()

                } else {
                    Toast.makeText(this@PlayActivity, "An error has occurred", Toast.LENGTH_SHORT)
                        .show()
                }

            }

        }

    }

    private fun sendGuess(query: String) {

        //We check the length of our query
        if (query.length == 1) {
            val body = GuessBody(game.token, query)

            val request = getAPI().create(HangmanAPIService::class.java)
            val call = request.sendGuessAPI(body).enqueue(object : Callback<HangmanResponse> {

                override fun onResponse(
                    call: Call<HangmanResponse>,
                    response: Response<HangmanResponse>
                ) {

                    val currentState = response.body() ?: return

                    //HANDLING GAME LOGIC

                    wordGuessedText.text = currentState.wordState

                    if (currentState.wordCorrect) {
                        score += Random.nextInt(100, 200)
                        //SHOW HANGMAN UI ELEMENTS


                    } else {

                        when (currentState.incorrectGuesses) {
                            1 -> headImage.visibility = View.VISIBLE
                            2 -> bodyImage.visibility = View.VISIBLE
                            3 -> capeImage.visibility = View.VISIBLE
                            4 -> rightlImage.visibility = View.VISIBLE
                            5 -> leftlImage.visibility = View.VISIBLE
                            6 -> rightaImage.visibility = View.VISIBLE
                            7 -> leftaImage.visibility = View.VISIBLE
                        }
                        score -= Random.nextInt(10, 18)
                    }
                    checkState()


                    tokenText.text = score.toString()

                }

                override fun onFailure(call: Call<HangmanResponse>, t: Throwable) {
                    // Handle Failure
                }


            })


        } else {
            Toast.makeText(this@PlayActivity, "Input only one letter", Toast.LENGTH_SHORT)
                .show()
        }


    }

    private fun checkState() {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<GameState> =
                getAPI().create(HangmanAPIService::class.java)
                    .getGameInfo("game?token=${game.token}")
            val content: GameState? = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    val uid = FirebaseAuth.getInstance().uid.toString()

                    //  WE CHECK IF THE SCORE IS BETTER THAN HIS HIGHEST ONE. IF TRUE, WE UPDATE HIS SCORE
                    //if (score > rtdb.child(uid).child("score").get().toString().toInt())
                    //    rtdb.child(uid).child("score").setValue(score.toString())


                    //WE CHECK IF THE STATUS OF THE GAME IS WON OR LOST
                    if (content?.status == "WON") {

                        bundle.putBoolean("won", true)
                        analytics.logEvent("game_result", bundle)

                        val intent = Intent(this@PlayActivity, WonActivity::class.java)
                        startActivity(intent)

                    } else if (content?.status == "LOST") {

                        bundle.putBoolean("view_ad", false)
                        analytics.logEvent("new_chance", bundle)

                        bundle.putBoolean("won", false)
                        analytics.logEvent("game_result", bundle)

                        val intent = Intent(this@PlayActivity, LostActivity::class.java)
                        startActivity(intent)

                    }


                } else {
                    Toast.makeText(this@PlayActivity, "An error has occurred", Toast.LENGTH_SHORT)
                        .show()
                }

            }

        }


    }


}