package com.example.projeto_meny2020.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projeto_meny2020.R
import com.example.projeto_meny2020.adapter.InfosTempoAdapter
import com.example.projeto_meny2020.classes.recycleInfosModel
import com.example.projeto_meny2020.viewModel.DadosTempoViewModel
import kotlinx.android.synthetic.main.fragment_home.*

val SPREFNAME = "rcyVwPref"

class HomeFragment : Fragment() {

    private lateinit var dadosTempoViewModel: DadosTempoViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let {
            dadosTempoViewModel = ViewModelProviders.of(it).get(DadosTempoViewModel::class.java)
        }

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val infosLista = CriaLista()

        var infosTempoAdapter = InfosTempoAdapter(infosLista)
        infosRcyVwHome.adapter = infosTempoAdapter
        infosRcyVwHome.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)

        currentDescriptionTxtVwHome.text = dadosTempoViewModel.dados.dados.getDescricao()
        currentTempTxtVwHome.text = dadosTempoViewModel.dados.dados.getTemperatura()
        currentTempMinTxtVwHome.text = dadosTempoViewModel.dados.dados.getTemperaturaMin()
        currentTempMaxTxtVwHome.text = dadosTempoViewModel.dados.dados.getTemperaturaMax()
        currentSensacaoDataTxtVwHome.text = dadosTempoViewModel.dados.dados.getSensacaoTerminca()
        currentSunriseCardVwHome.text = dadosTempoViewModel.dados.dados.getNascerSol()
        currentSunsetCardVwHome.text = dadosTempoViewModel.dados.dados.getPorSol()
    }

    private fun CriaLista(): List<recycleInfosModel>{
        val retornar = listOf(
            recycleInfosModel("UV", R.drawable.simple_weather_icon_60, dadosTempoViewModel.dados.dados.getUV()),
            recycleInfosModel("Umidade Rel.", R.drawable.humidity, dadosTempoViewModel.dados.dados.getUmidadeRelativa()),
            recycleInfosModel("Vento Dir.", R.drawable.simple_weather_icon_47, dadosTempoViewModel.dados.dados.getVentoDirMin()),
            recycleInfosModel("Vento Vel.", R.drawable.windicon, dadosTempoViewModel.dados.dados.getVentoVel()),
            recycleInfosModel("Pressao", R.drawable.pressure, dadosTempoViewModel.dados.dados.getPressao())
            )

        return retornar
    }
}
