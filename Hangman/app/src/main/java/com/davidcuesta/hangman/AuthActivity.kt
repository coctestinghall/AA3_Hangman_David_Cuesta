package com.davidcuesta.hangman

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.davidcuesta.hangman.databinding.ActivityAuthBinding
import com.davidcuesta.hangman.menus.LoginFragment
import com.davidcuesta.hangman.menus.NicknameFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.fragment_login.*


class AuthActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private lateinit var binding: ActivityAuthBinding
    private val rtdb = FirebaseDatabase.getInstance("https://hangman-davidcuesta-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Setup
        setup()


    }

    private fun setup() {

        title = "Configuration"
        val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()

        if (FirebaseAuth.getInstance().currentUser?.isAnonymous == false) {
            db.collection("users")
                .document(FirebaseAuth.getInstance().currentUser?.email.toString())
                .get().addOnSuccessListener {

                    usernameText.text = it.get("username").toString()
                    //editUsername.hint = it.get("username").toString()
                }
        } else {

            usernameText.text = "Anonymous"
            //editUsername.hint = "Anonymous"

        }

        val logFrag = supportFragmentManager.findFragmentByTag("FragmentLogin")
        val nameFrag = supportFragmentManager.findFragmentByTag("FragmentNickname")

        loginButton.setOnClickListener() {

            supportFragmentManager.beginTransaction().apply {
                if (!LoginFragment().isAdded)
                    add(fragmentLayout.id, LoginFragment(), "FragmentLogin")
                else
                    show(LoginFragment())
                commit()

            }


        }


        changeUsernameButton.setOnClickListener() {

            supportFragmentManager.beginTransaction().apply {
                if (!NicknameFragment().isAdded)
                    add(fragmentLayout.id, NicknameFragment(), "FragmentNickname")
                else
                    show(NicknameFragment())
                commit()

            }



        }


        goHomeButton.setOnClickListener() {
            goHome()
        }



        logOutButton.setOnClickListener()
        {
            if (FirebaseAuth.getInstance().currentUser?.isAnonymous == false) {
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(this, "You are no longer logged in", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "You are not logged in", Toast.LENGTH_SHORT).show()
            }
        }

        //WE CHECK IF THE SLIDER MUST BE TO TRUE OR TO FALSE
        //db.collection("users").document(FirebaseAuth.getInstance().currentUser?.email.toString()).get().addOnSuccessListener {
//
        //    if (it.get("notifications").toString() == "true")
        //        notificationsSwitch.isChecked = true
        //    else if (it.get("notifications").toString() == "false")
        //        notificationsSwitch.isChecked = false
//
        //}

        // WE UPDATE THE SLIDER WITH IT'S VALUE
        //notificationsSwitch.setOnClickListener(){
//
        //    if (notificationsSwitch.isChecked){
        //        db.collection("users").document(emailText2.text.toString()).set(
        //            hashMapOf("notifications" to "true")
        //        )
        //        FirebaseMessaging.getInstance().subscribeToTopic("notifications")
        //    }else{
        //        db.collection("users").document(emailText2.text.toString()).set(
        //            hashMapOf("notifications" to "false")
        //        )
        //        FirebaseMessaging.getInstance().unsubscribeFromTopic("notifications")
//
        //    }
//
        //}



    }


// private fun addScore(score: Int) {

//     val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()

//     rtdb.child(uid).child("score").get().addOnSuccessListener {

//         val recoveredScore = rtdb.child(uid).child("score").get().toString().toInt()


//         if (score > recoveredScore) {
//             rtdb.child(uid).setValue(score)

//         }

//     }.addOnFailureListener() {

//         rtdb.child(uid).setValue(score)

//     }


// }


    private fun goHome(email: String = "") {
        val homeIntent = Intent(this, MainActivity::class.java).apply {

            putExtra("email", email)
        }
        startActivity(homeIntent)

    }


}