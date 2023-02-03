package com.davidcuesta.hangman.utils

import com.google.gson.annotations.SerializedName

data class HangmanNewGameResponse(
    @SerializedName("token") var token: String,
    @SerializedName("language") var language: String,
    @SerializedName("maxTries") var maxTries: String,
    @SerializedName("hangman") var word: String,
    @SerializedName("incorrectGuesses") var incorrectGuesses: String,
)
