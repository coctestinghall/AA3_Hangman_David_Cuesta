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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_nickname.*

class NicknameFragment : Fragment() {

    companion object {
        fun newInstance() = NicknameFragment()
    }

    private lateinit var viewModel: NicknameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nickname, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NicknameViewModel::class.java)
        // TODO: Use the ViewModel

        changeUsernameButton2.setOnClickListener() {
            if (editUsername.text.isNotEmpty() && FirebaseAuth.getInstance().currentUser?.isAnonymous == false) {
                val uid = FirebaseAuth.getInstance().uid.toString()
                val username = editUsername.text.toString()

                //WE SAVE THE NEW USERNAME ON FIRESTORE AND RTDB FOR RANKING
                viewModel.db.collection("users")
                    .document(FirebaseAuth.getInstance().currentUser?.email.toString()).set(
                        hashMapOf("username" to username)
                    )
                viewModel.rtdb.child(uid).child("username").setValue(username)
                goHome()



            } else if (FirebaseAuth.getInstance().currentUser?.isAnonymous == true) {

                Toast.makeText(context, "Please first login", Toast.LENGTH_SHORT).show()


            } else if (editUsername.text.isEmpty()) {

                Toast.makeText(context, "Please write your new username", Toast.LENGTH_SHORT).show()

            } else {

                Toast.makeText(context, "Unexpected error, try again later", Toast.LENGTH_SHORT).show()
                goHome()

            }

        }

        exitChangeButton.setOnClickListener() {
            goHome()
        }


    }

    private fun goHome() {

        //viewModel.fragmentOpened = false
        parentFragmentManager.beginTransaction().remove(this).commit()

    }


}