package com.tempigo.projeto_meny2020.classes

import com.google.gson.GsonBuilder

class DataTempoWheater(
    val icon: String,
    val code: String,
    val description: String
){}

class DataTempo(
    val rh: Int,
    val pod: String,
    val lon: Float,
    val pres: Float,
    val timezone: String,
    val ob_time: String,
    val country_code: String,
    val clouds: Int,
    val ts: Int,
    val solar_rad: Float,
    val state_code: Int,
    val city_name: String,
    val wind_spd: Float,
    val last_ob_time: String,
    val wind_cdir_full: String,
    val wind_cdir: String,
    val slp: Float,
    val vis: Float,
    val h_angle: Int,
    val sunset: String,
    val dni: Float,
    val dewpt: Float,
    val snow: Float,
    val uv: Float,
    val precip: Float,
    val wind_dir: Int,
    val sunrise: String,
    val ghi: Float,
    val dhi: Float,
    val aqi: Int,
    val lat: Float,
    val weather: DataTempoWheater,
    val datetime: String,
    val temp: Float,
    val station: String,
    val elev_angle: Float,
    val app_temp: Int
){}

class DadosTempoJSON(private val data: List<DataTempo>) {
    fun getTemperatura(): String = data[0].temp.toString() ?: "0"

    fun getDescricao(): String = data[0].weather.description ?: "0"

    fun getNomeCidade(): String = data[0].city_name ?: "0"

    fun getVisibilidade(): String = data[0].vis.toString() ?: "0"

    fun getUmidadeRelativa(): String = data[0].rh.toString() ?: "0"

    fun getVentoDirMin(): String = data[0].wind_cdir ?: "0"

    fun getVentoDirMax():String = data[0].wind_cdir_full ?: "0"

    fun getVentoVel():String = data[0].wind_spd.toString() ?: "0"

    fun getSensacaoTerminca(): String = data[0].app_temp.toString() + "°C" ?: "0"

    fun getNuvensPrc(): String = data[0].clouds.toString() ?: "0"

    fun getNascerSol(): String{

        if(data[0] != null){
            val hora = data[0].sunrise
            val newHora = hora.split(":")
            val horaNasc: Int = newHora[0].toInt() - 3
            val retornar: String
            if(horaNasc <= 9){
                retornar = "0" + horaNasc.toString() + ":" + newHora[1]
            }else{
                retornar= horaNasc.toString() + ":" + newHora[1]
            }
            return retornar

        }else{
            return "00:00"
        }
    }

    fun getPorSol(): String{
        if(data[0] != null){
            val hora = data[0].sunset
            val newHora = hora.split(":")
            val horaNasc: Int = newHora[0].toInt() - 3
            val retornar: String = horaNasc.toString() + ":" + newHora[1]
            return retornar
        }else{
            return "00:00"
        }
    }

    fun getPressao(): String = data[0].pres.toString() ?: "0"

    fun getUV(): String = data[0].uv.toString() ?: "0"

    fun getRadiacaoSolar(): String = data[0].solar_rad.toString() ?: "0"

    fun getManhaNoite(): String = data[0].pod ?: "0"

    fun getTemperaturaMin(): String = (getTemperatura().toFloat() - 2).toString() ?: "0"

    fun getTemperaturaMax(): String = (getTemperatura().toFloat() + 3).toString() ?: "0"
}

//classe mockada
class JSONMock(){
    val gson = GsonBuilder().create()
    val dados = gson.fromJson(
        """{
              "data": [
                {
                  "rh": 60,
                  "pod": "d",
                  "lon": -43.17,
                  "pres": 1007.56,
                  "timezone": "America/Sao_Paulo",
                  "ob_time": "2020-04-07 14:41",
                  "country_code": "BR",
                  "clouds": 35,
                  "ts": 1586270513,
                  "solar_rad": 895.402,
                  "state_code": "21",
                  "city_name": "Rio de Janeiro",
                  "wind_spd": 3.19813,
                  "last_ob_time": "2020-04-07T14:16:00",
                  "wind_cdir_full": "Oeste-Sudoeste",
                  "wind_cdir": "OSO",
                  "slp": 1012.93,
                  "vis": 7.82413,
                  "h_angle": -15,
                  "sunset": "20:46",
                  "dni": 925.84,
                  "dewpt": 21.4,
                  "snow": 0,
                  "uv": 6.63754,
                  "precip": 0,
                  "wind_dir": 250,
                  "sunrise": "09:02",
                  "ghi": 914.73,
                  "dhi": 121.72,
                  "aqi": 100,
                  "lat": -22.91,
                  "weather": {
                    "icon": "c02d",
                    "code": "801",
                    "description": "Poucas nuvens"
                  },
                  "datetime": "2020-04-07:14",
                  "temp": 30.1,
                  "station": "F0496",
                  "elev_angle": 57.12,
                  "app_temp": 33
                }
              ],
              "count": 1
            }""",
        DadosTempoJSON::class.java
    )
}