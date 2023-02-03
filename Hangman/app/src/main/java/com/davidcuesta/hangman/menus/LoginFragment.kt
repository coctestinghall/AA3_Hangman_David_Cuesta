package com.davidcuesta.hangman.menus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.davidcuesta.hangman.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_nickname.*

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel



        signupButton2.setOnClickListener() {
            if (emailText2.text.isNotEmpty() && passwordText2.text.isNotEmpty() && passwordText2.text.length >= 6) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    emailText2.text.toString(),
                    passwordText2.text.toString()
                ).addOnCompleteListener() {
                    if (it.isSuccessful) {
                        //WE CREATE RTDB ENTRY FOR THIS USER. AFTER A LOT OF TRY AND ERROR THE RTDB IS WORKING WITH USERS AS A ROOT. EACH ONE SORTED BY IT'S UID WITH IT'S MAX SCORE (SCORE) INSIDE
                        val uid = FirebaseAuth.getInstance().uid.toString()
                        val score  = 0
                        viewModel.rtdb.child(uid).child("score").setValue(score.toString())
                        viewModel.rtdb.child(uid).child("username").setValue("Anonymous")

                        //WE SET DEFAULT USERNAME ON THE FIRESTORE DATABASE AS "ANONYMOUS"
                        viewModel.db.collection("users").document(emailText2.text.toString()).set(
                            hashMapOf("username" to "Anonymous")
                        )
                        viewModel.db.collection("users").document(emailText2.text.toString()).set(
                            hashMapOf("notifications" to "true")
                        )

                        //WE SUSCRIBE THE USER TO THE TOPIC NOTIFICATIONS TO ONLY SEND HIM NOTIFICATIONS WHEN WANTED
                        FirebaseMessaging.getInstance().subscribeToTopic("notifications")

                        goHome()

                    } else {
                        Toast.makeText(requireContext().applicationContext, "Error during Sign-Up", Toast.LENGTH_SHORT).show()

                    }

                }
            }else{
                Toast.makeText(requireContext().applicationContext, "Email or password are not valid. Password should be at least 6 characters long", Toast.LENGTH_SHORT).show()

            }
        }

        signinButton2.setOnClickListener() {

            if (emailText2.text.isNotEmpty() && passwordText2.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    emailText2.text.toString(),
                    passwordText2.text.toString()
                ).addOnCompleteListener() {
                    if (it.isSuccessful) {
                        goHome()

                    } else {
                        Toast.makeText(requireContext().applicationContext, "Error during Login", Toast.LENGTH_SHORT).show()
                    }

                }
            }else{
                Toast.makeText(requireContext().applicationContext, "Email or password are not valid.", Toast.LENGTH_SHORT).show()

            }

        }

        exitLoginButton.setOnClickListener() {

            goHome()


        }

    }

    private fun goHome() {
        parentFragmentManager.beginTransaction().remove(this).commit()

    }


}

