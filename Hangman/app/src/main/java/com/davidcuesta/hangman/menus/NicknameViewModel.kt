package com.davidcuesta.hangman.menus

import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NicknameViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val db = Firebase.firestore
    val rtdb = FirebaseDatabase.getInstance("https://hangman-davidcuesta-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users")
    //val rtdb = FirebaseDatabase.getInstance("https://hangman-davidcuesta-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users")

}