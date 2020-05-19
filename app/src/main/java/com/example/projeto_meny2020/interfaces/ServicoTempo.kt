package com.example.projeto_meny2020.interfaces

import com.example.projeto_meny2020.classes.modelsRetrofit.RespostaTempoCurrent
import com.example.projeto_meny2020.classes.modelsRetrofit.RespostaTempoDaily
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServicoTempo {
    @GET("current?")
    fun getCurrentWeatherData(@Query("lat") lat: String,
                              @Query("lon") lon: String,
                              @Query("key") key: String,
                              @Query("lang") lang: String = "pt"): Call<RespostaTempoCurrent>

    @GET("forecast/daily?")
    fun getDailyWeatherData(@Query("lat") lat: String,
                            @Query("lon") lon: String,
                            @Query("key") key: String): Call<RespostaTempoDaily>
}