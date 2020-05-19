package com.example.projeto_meny2020.classes.modelsRetrofit

class RespostaTempoCurrent(
    val data: List<Data>? = listOf(Data()),
    val count: Int = 0
) {}

class Data(val rh: Int = 0,
           val pod: String = "0",
           val lon: Float = 0f,
           val pres: Float = 0f,
           val timezone: String = "0",
           val ob_time: String = "0",
           val country_code: String = "0",
           val clouds: Int = 0,
           val ts: Int = 0,
           val solar_rad: Float = 0f,
           val state_code: Int = 0,
           val city_name: String = "0",
           val wind_spd: Float = 0f,
           val last_ob_time: String = "0",
           val wind_cdir_full: String = "0",
           val wind_cdir: String = "0",
           val slp: Float = 0f,
           val vis: Float = 0f,
           val h_angle: Int = 0,
           val sunset: String = "00:00",
           val dni: Float = 0f,
           val dewpt: Float = 0f,
           val snow: Float = 0f,
           val uv: Float = 0f,
           val precip: Float = 0f,
           val wind_dir: Int = 0,
           val sunrise: String = "00:00",
           val ghi: Float = 0f,
           val dhi: Float = 0f,
           val aqi: Int = 0,
           val lat: Float = 0f,
           val weather: DataTempoWheater? = DataTempoWheater(),
           val datetime: String = "0",
           val temp: Float = 0f,
           val station: String = "0",
           val elev_angle: Float = 0f,
           val app_temp: Float = 0f) {}

class DataTempoWheater(
    val icon: String = "0",
    val code: String = "0",
    val description: String = "0"
) {}