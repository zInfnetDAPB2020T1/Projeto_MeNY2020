package com.example.projeto_meny2020.classes.modelsRetrofit

import com.example.projeto_meny2020.R

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
    //Precisamos colocar isso
    val temp : Float = 0f,
    val dewpt :Float = 0f,
    val max_temp : Float = 0f,
    val min_temp : Float = 0f,
    val app_max_temp: Float = 0f,
    val app_min_temp : Float = 0f ,
    val rh : Int = 0,
    val clouds : Float = 0f,
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
) {
    fun getDia():String{
        val splitado = datetime.split("-")
        val dia = splitado[2].split(":")[0]
        return dia
    }

    fun getMes():String{
        val splitado = datetime.split("-")
        return when(splitado[1]){
            "01" -> "JAN"
            "02" -> "FEV"
            "03" -> "MAR"
            "04" -> "ABR"
            "05" -> "MAI"
            "06" -> "JUN"
            "07" -> "JUL"
            "08" -> "AGO"
            "09" -> "SET"
            "10" -> "OUT"
            "11" -> "NOV"
            "12" -> "DEZ"
            else -> "ERRO"
        }
    }

    fun getTemp(): String{
        return temp.toInt().toString() + "°C"
    }

    fun getMin(): String{
        return min_temp.toInt().toString() + "°C"
    }

    fun getMax():String{
        return max_temp.toInt().toString() + "°C"
    }

    fun getStatus():String{
        if(clouds in 0.0..50.0){
            return "Ensolarado"
        }else if(clouds in 51.0..70.0){
            return "Parcialmente Nublado"
        }else {
            return "Nublado"
        }
    }

    fun getIcone(): Int{
        val string = getStatus()
        if(string == "Ensolarado"){
            return R.drawable.suniconff9800
        }else if(string == "Parcialmente Nublado"){
            return R.drawable.parcialmentenublado
        }else{
            return R.drawable.nublado
        }
    }

    fun getPct():String{
        return pop.toInt().toString() + "%"
    }
}


class weatherInfo(
    val icon: String = "NULL",
    val code: String = "NULL",
    val description: String = "NULL"
) {}