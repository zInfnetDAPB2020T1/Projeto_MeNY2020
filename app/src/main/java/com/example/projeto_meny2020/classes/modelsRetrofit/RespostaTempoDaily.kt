package com.example.projeto_meny2020.classes.modelsRetrofit

class RespostaTempoDaily(
    val city_name: String = "STRING CITY_NAME NULL",
    val state_code: String = "STATE_CODE NULL",
    val country_code: String = "country_code NULL",
    val lat: String = "0",
    val long: String = "0",
    val timezone: String = "IANA TIMEZONE",
    val data: List<Forecast>? = listOf(Forecast())
) {}

class Forecast(
    val ts: Long = 0,
    val timestamp_local: String = "0",
    val timestamp_utc : String = "0",
    val datetime: String = "YYYY-MM-DD:HH",
    val snow: Float = 0f,
    val snow_depth: Float = 0f,
    val precip: Float = 0f,
    val temp : Float = 0f,
    val dewpt :Float = 0f,
    val max_temp : Float = 0f,
    val min_temp : Float = 0f,
    val  app_max_temp: Float = 0f,
    val app_min_temp : Float = 0f ,
    val rh : Int = 0,
    val clouds : Int = 0,
    val weather: weatherInfo? = weatherInfo() ,
    val slp : Float = 0f,
    val pres: Float = 0f ,
    val uv : Float = 0f ,
    val max_dhi :Float = 0f ,
    val vis :Float = 0f ,
    val pop :Float = 0f ,
    val moon_phase : Float = 0f ,
    val sunrise_ts : Int = 0 ,
    val sunset_ts : Int = 0 ,
    val moonrise_ts : Int = 0,
    val moonset_ts :  Int = 0,
    val pod : String = "NULL" ,
    val wind_spd :Float = 0f ,
    val wind_dir : Float = 0f ,
    val wind_cdir :String = "NULL" ,
    val wind_cdir_full : String = "NULL"
) {}


class weatherInfo(
    val icon: String = "NULL",
    val code: String = "NULL",
    val description: String = "NULL"
) {}