package com.davidcuesta.hangman.utils

import com.google.gson.annotations.SerializedName

data class HangmanResponse(
    @SerializedName("token") var token: String,
    @SerializedName("hangman") var wordState: String,
    @SerializedName("incorrectGuesses") var incorrectGuesses: Int,
    @SerializedName("correct") var wordCorrect: Boolean
)

