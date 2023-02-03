package com.davidcuesta.hangman.utils

import com.google.gson.annotations.SerializedName


data class GameState(
    @SerializedName("token") var token: String,
    @SerializedName("language") var lan: String,
    @SerializedName("maxTries") var maxTries: Int,
    @SerializedName("solution") var solution: String,
    @SerializedName("hangman") var hangman: String,
    @SerializedName("status") var status: String,
    @SerializedName("incorrectGuesses") var incorrectGuesses: Int,
)