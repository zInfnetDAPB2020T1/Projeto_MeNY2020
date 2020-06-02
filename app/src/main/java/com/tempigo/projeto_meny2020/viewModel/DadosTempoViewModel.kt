package com.tempigo.projeto_meny2020.viewModel

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tempigo.projeto_meny2020.R
import com.tempigo.projeto_meny2020.adapter.InfosTempoAdapter
import com.tempigo.projeto_meny2020.classes.modelsRetrofit.RespostaTempoCurrent
import com.tempigo.projeto_meny2020.classes.modelsRetrofit.RespostaTempoDaily
import com.tempigo.projeto_meny2020.classes.recycleInfosModel
import com.tempigo.projeto_meny2020.interfaces.ServicoTempo
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.PrintWriter
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class DadosTempoViewModel(): ViewModel() {
    var dadosCurrent: RespostaTempoCurrent? = null
    var dadosDaily: RespostaTempoDaily? = null
        set(value){
            field = value
            dadosLiveDataDaily.value = value
        }
    var dadosLiveDataDaily = MutableLiveData<RespostaTempoDaily>()
    var lat = "-22.875113"
        set(value) {
            jaDeuGet = false
            trocar = true
            field = value

        }
    var lon = "-43.277548"
        set(value) {
            jaDeuGet = false
            trocar = true
            field = value
            val fileLatLon = File(fileDir, "latlon.txt")
            if(fileLatLon.exists()){
                fileLatLon.writeText("$lat/$lon")
            }else{
                Log.e("Nao e possivel", "isto nao deveria ser mostrado, algum erro ocorreu.")
            }
        }
    private var trocar = false
    var fileDir = ""
    private var jaDeuGet = false

    var currentFragment: Fragment? = null

    fun getJaDeuGet(): Boolean{
        return jaDeuGet
    }

    fun SalvarDadosCurrent(dir: File){
        val stringSalvar = Gson().toJson(dadosCurrent)
        if(dir.exists()){
            dir.writeText(stringSalvar)
        }else{
            PrintWriter(dir).use { out -> out.print(stringSalvar) }
        }
    }

    fun SalvarDadosDaily(dir: File){
        val stringSalvar = Gson().toJson(dadosDaily)
        if(dir.exists()){
            dir.writeText(stringSalvar)
        }else{
            PrintWriter(dir).use { out -> out.print(stringSalvar) }
        }
    }

    fun PegarDadosCurrent(current: File, rc: RecyclerView?, ctx: Context?, callback: () -> Unit){
        if(current.exists()){
            val stringPegar = current.readText()
            dadosCurrent = Gson().fromJson(stringPegar, RespostaTempoCurrent::class.java)
            if(rc != null || ctx != null) atualizarRecycleInfo(rc!!, ctx!!, callback)
        }else{
            dadosCurrent = RespostaTempoCurrent()
            if(rc != null || ctx != null) atualizarRecycleInfo(rc!!, ctx!!, callback)
        }
    }

    fun PegarDadosDaily(daily: File, callback: () -> Unit, ctxTeste: Context?){
        if(daily.exists()){
            val stringPegar = daily.readText()
            dadosDaily = Gson().fromJson(stringPegar, RespostaTempoDaily::class.java)
            if(ctxTeste == null) atualizarRecycleDaily(callback, ctxTeste)
        }else{
            dadosDaily = RespostaTempoDaily()
            if(ctxTeste == null) atualizarRecycleDaily(callback, ctxTeste)
        }
    }

    fun RetrofitGetDataWeatherComplete(
        fileC: File,
        fileD: File,
        fileSC: File,
        fileSD: File,
        rcH: RecyclerView?,
        ctxH: Context?,
        callback: () -> Unit
    ){
        val fileLatLon = File(fileDir, "latlon.txt")
        if(fileLatLon.exists()){
            lat = fileLatLon.readText().split("/")[0]
            lon = fileLatLon.readText().split("/")[1]
        }else{
            PrintWriter(fileLatLon).use { out -> out.print("$lat/$lon") }
        }
        if(!jaDeuGet || trocar){
            jaDeuGet = true
            Log.d("CONFERIR", "Entrou na RetrofitGetDataWeatherComplete()")
            val retrofit = Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(ServicoTempo::class.java)
            val callCurrent = service.getCurrentWeatherData(lat, lon, Key)
            val callDaily = service.getDailyWeatherData(lat, lon, Key)

            if(confereTimeGetCurrent(fileC) || trocar){
                callCurrent.enqueue(object : Callback<RespostaTempoCurrent> {
                    override fun onFailure(call: Call<RespostaTempoCurrent>?, t: Throwable?) {
                        Log.d("ESTOY DEBUGANDO", t!!.message!!)
                    }
                    override fun onResponse(
                        call: Call<RespostaTempoCurrent>?,
                        response: Response<RespostaTempoCurrent>?
                    ) {
                        try {
                            if(response!!.isSuccessful){
                                dadosCurrent = response.body()
                                Log.d("DADNDO GET", "get foi dado no current")
                                SalvarDadosCurrent(fileSC)
                                if(rcH != null || ctxH != null) {
                                    atualizarRecycleInfo(rcH!!, ctxH!!, callback)
                                }
                            }else{
                                Log.d("DEU ALGUM ERRO", call.toString())
                            }
                        }catch (e: Exception){
                            Log.d("DEBUGANDo", e.message!!)
                        }
                    }
                })
            }else{
                try {
                    PegarDadosCurrent(fileSC, rcH, ctxH, callback)
                    Log.d("NAO DEU GET", "NAO DEU GET")
                }catch (e: Exception){
                    Log.e("ERROR GET CURRENT DATA", e.message!!)
                }
            }
            if(confereTimeGetDaily(fileD) || trocar){
                trocar = false
                callDaily.enqueue(object: Callback<RespostaTempoDaily> {
                    override fun onResponse(
                        call: Call<RespostaTempoDaily>?,
                        response: Response<RespostaTempoDaily>?
                    ) {
                        try {
                            if(response!!.isSuccessful){
                                dadosDaily = response!!.body()
                                Log.d("DADNDO GET", "get foi dado no daily")
                                SalvarDadosDaily(fileSD)
                                atualizarRecycleDaily(callback, ctxH)
                            }
                        }catch (e: Exception){
                            Log.e("ERROR", e.message!!)
                        }
                    }

                    override fun onFailure(call: Call<RespostaTempoDaily>?, t: Throwable?) {
                        Log.d("ON FAILURE", t!!.message!!)
                    }
                })
            }else{
                try {
                    PegarDadosDaily(fileSD, callback, ctxH)
                    Log.d("NAO DEU GET", "NAO DEU GET")
                }catch (e: Exception){
                    Log.e("ERROR GET CURRENT DATA", e.message!!)
                }
            }
        }else{
            if(rcH != null || ctxH != null) {
                PegarDadosCurrent(fileSC, rcH!!, ctxH!!, callback)
            }else{
                PegarDadosCurrent(fileSC, null, null, callback)
            }
            PegarDadosDaily(fileSD, callback, ctxH)
        }
    }

    internal fun confereTimeGetCurrent(file: File): Boolean {
        try {
            val timeAtual = SimpleDateFormat("dd/HH")
            val timeAtualFormatado = timeAtual.format(Date())

            val texto: String?
            if(file.exists()){
                texto = file.readText()
            }else{
                texto = null
            }

            if(!texto.isNullOrBlank()){
                if(texto.split("/")[0].toInt() != timeAtualFormatado.split("/")[0].toInt()){
                    Log.d("TESTE CONFERIR CURRENT", texto)
                    try {
                        file.writeText(timeAtualFormatado)
                    }catch (e: Exception){
                        Log.e("ERROR WRITING NEW FILE", e.message!!)
                    }
                    return true
                }else{
                    if(texto.split("/")[1].toInt() < timeAtualFormatado.split("/")[1].toInt()){
                        try {
                            file.writeText(timeAtualFormatado)
                        }catch (e: Exception){
                            Log.e("ERROR WRITING NEW FILE", e.message!!)
                        }
                        return true
                    }else{
                        return false
                    }
                }
            }else{
                PrintWriter(file).use { out -> out.print(timeAtualFormatado) }
                return true
            }
        }catch (e: Exception){
            Log.e("ERROR", e.message!!)
            return false
        }

    }

    internal fun confereTimeGetDaily(file: File): Boolean{
        try {
            val timeAtual = SimpleDateFormat("dd")
            val timeAtualFormatado = timeAtual.format(Date())

            val texto: String?

            if(file.exists()){
                texto = file.readText()
            }else{
                texto = null
            }

            if(!texto.isNullOrBlank()){
                Log.d("TESTE CONFERIR DAILY", texto)
                try {
                    file.writeText(timeAtualFormatado)
                }catch (e: Exception){
                    Log.e("ERROR WRITING NEW FILE", e.message!!)
                }
                return try {
                    texto.toInt() != timeAtualFormatado.toInt()
                }catch (e: Exception){
                    true
                }
            }else{
                PrintWriter(file).use { out -> out.print(timeAtualFormatado) }
                return true
            }
        }catch (e: Exception){
            Log.d("ERROR", e.message!!)
            return false
        }
    }


    companion object {
        var BaseUrl = "https://api.weatherbit.io/v2.0/"
        var Key = "d23cfc1f907f4ddabb842d94e18eb61d"
    }

    fun atualizarRecycleInfo(rc: RecyclerView, context: Context, callback: ()-> Unit){
        val infosLista = CriaLista()

        val infosTempoAdapter = InfosTempoAdapter(infosLista)
        rc.adapter = infosTempoAdapter
        rc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)

        callback()
    }

    private fun atualizarRecycleDaily(callback: () -> Unit, ctx: Context?){
        if(ctx == null) callback()
    }

    private fun CriaLista(): List<recycleInfosModel>{
        val retornar = listOf(
            recycleInfosModel("UV", R.drawable.icon_recycler_uv, DadosCurrent().getUV(), "(IUV)"),
            recycleInfosModel("Umidade Rel.", R.drawable.icon_current_relative_humidy, DadosCurrent().getUmidadeRelativa(), "(%)"),
            recycleInfosModel("Vento Dir.", R.drawable.icon_recycler_wind_dir, DadosCurrent().getVentoDirMin(), "(Cardinal)"),
            recycleInfosModel("Vento Vel.", R.drawable.icon_recycler_wind_speed, DadosCurrent().getVentoVel(), "(m/s)"),
            recycleInfosModel("Pressão", R.drawable.icon_recycler_pressao, DadosCurrent().getPressao(), "(mb)")
        )

        return retornar
    }

    inner class DadosCurrent(){
        fun getTemperatura(): String = dadosCurrent!!.data!![0].temp.roundToInt().toString() + "°C" ?: "0"

        fun getDescricao(): String = dadosCurrent!!.data!![0].weather!!.description ?: "0"

        fun getIconeTemp(): Int{
            val descri = getDescricao()
            val tempoSunset = getPorSol()
            val tempoSunrise = getNascerSol()
            val tempoSunsetSplitado = tempoSunset.split(":")
            val tempoSunriseSplitado = tempoSunrise.split(":")
            val dateFormatado = SimpleDateFormat("HH").format(Date())
            if(tempoSunsetSplitado[0].toInt() >= dateFormatado.toInt() && tempoSunriseSplitado[0].toInt() <= dateFormatado.toInt() && descri == "Céu limpo")
                return R.drawable.suntest
            return when (descri){
                "Céu limpo" ->  R.drawable.icone_current_ceu_limpo_noite

                "Poucas nuvens" -> R.drawable.icone_current_poucas_nuvens
                "Parcialmente nublado" -> R.drawable.icone_current_parcialmente_nublado
                "Nuvens dispersas" -> R.drawable.icone_current_nuvens_dispersas
                "Nublado" -> R.drawable.icone_current_nublado
                "Chuva fraca" -> R.drawable.icone_current_chuva_fraca
                "Chuva moderada" -> R.drawable.icone_current_chuva_moderada

                "Chuva forte" -> R.drawable.icone_current_chuva_forte
                "Chuva intensa" -> R.drawable.icone_current_chuva_forte

                else -> R.drawable.icone_current_else_tampao
            }
        }

        fun getNomeCidade(): String = dadosCurrent!!.data!![0].city_name ?: "0"

        fun getVisibilidade(): String = dadosCurrent!!.data!![0].vis.toString() ?: "0"

        fun getUmidadeRelativa(): String = dadosCurrent!!.data!![0].rh.toString() ?: "0"

        fun getVentoDirMin(): String = dadosCurrent!!.data!![0].wind_cdir ?: "0"

        fun getVentoDirMax():String = dadosCurrent!!.data!![0].wind_cdir_full ?: "0"

        fun getVentoVel():String = dadosCurrent!!.data!![0].wind_spd.toString() ?: "0"

        fun getSensacaoTerminca(): String = dadosCurrent!!.data!![0].app_temp.roundToInt().toString() + "°C" ?: "0"

        fun getNuvensPrc(): String = dadosCurrent!!.data!![0].clouds.toString() ?: "0"

        fun getNascerSol(): String{

            if(dadosCurrent!!.data!![0] != null){
                val hora = dadosCurrent!!.data!![0].sunrise
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
            if(dadosCurrent!!.data!![0] != null){
                val hora = dadosCurrent!!.data!![0].sunset
                val newHora = hora.split(":")
                val horaNasc: Int = newHora[0].toInt() - 3
                val retornar: String = horaNasc.toString() + ":" + newHora[1]
                return retornar
            }else{
                return "00:00"
            }
        }

        fun getPressao(): String = dadosCurrent!!.data!![0].pres.toString() ?: "0"

        fun getUV(): String = dadosCurrent!!.data!![0].uv.toString() ?: "0"

        fun getRadiacaoSolar(): String = dadosCurrent!!.data!![0].solar_rad.toString() ?: "0"

        fun getManhaNoite(): String = dadosCurrent!!.data!![0].pod ?: "0"

        fun getTemperaturaMin(): String{
            val temp = getTemperatura().split("°")[0].toInt()
            return (temp - 2).toString() + "°"
        }

        fun getTemperaturaMax(): String{
            val temp = getTemperatura().split("°")[0].toInt()
            return (temp + 3).toString() + "°"
        }

        fun getDicaDoDia(): String{
            val desc = getDescricao()

            return when (desc){
                "Céu limpo" -> "Lá fora está belo, abra a janela!"

                "Poucas nuvens" -> "Não se esqueça, o tempo, às vezes, é seu amigo."
                "Parcialmente nublado" -> "Nuvens espessas e acumuladas ventanias certas e continuadas."
                "Nuvens dispersas" -> "Já olhou para o céu e viu o formato das nuvens?"
                "Nublado" -> "Por mais que esteja nublado, tente não ficar na cama."
                "Chuva fraca" -> "A chuva está fraca, a vontade de sair de casa também."
                "Chuva moderada" -> "Beijos na chuva não são tão románticos assim."

                "Chuva forte" -> "Não esqueça o guarda-chuva e, se possivel, fique em casa."
                "Chuva intensa" -> "Não esqueça o guarda-chuva e, se possivel, fique em casa."

                else -> "Acho que não temos nenhuma dica no momento."
            }
        }
    }
}