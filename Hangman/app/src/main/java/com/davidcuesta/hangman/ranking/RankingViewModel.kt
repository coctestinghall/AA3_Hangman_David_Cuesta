package com.davidcuesta.hangman.ranking

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RankingViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val rtdb = FirebaseDatabase.getInstance("https://hangman-davidcuesta-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users")
    val db = Firebase.firestore

    //var fragmentOpened = true


}