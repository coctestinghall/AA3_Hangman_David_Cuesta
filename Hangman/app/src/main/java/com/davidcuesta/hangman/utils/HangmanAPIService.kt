package com.davidcuesta.hangman.utils

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface HangmanAPIService {

    @GET//("new")
      suspend fun callNewGameApi(@Url url:String): Response<HangmanNewGameResponse>
    @POST("guess")
     fun sendGuessAPI(@Body info:GuessBody): Call<HangmanResponse>
    @GET//("game")
     suspend fun getGameInfo(@Url url:String): Response<GameState>



}