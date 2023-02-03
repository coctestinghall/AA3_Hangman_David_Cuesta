package com.davidcuesta.hangman.utils

import com.google.gson.annotations.SerializedName


data class GuessBody(
    @SerializedName("token") var token: String,
    @SerializedName("letter") var letter: String,
)