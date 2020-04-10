package com.example.projeto_meny2020.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.projeto_meny2020.R
import com.example.projeto_meny2020.viewModel.DadosTempoViewModel
import kotlinx.android.synthetic.main.fragment_home.*

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
        currentDescriptionTxtVwHome.text = dadosTempoViewModel.dados.dados.getDescricao()
        currentTempTxtVwHome.text = dadosTempoViewModel.dados.dados.getTemperatura()
        currentTempMinTxtVwHome.text = dadosTempoViewModel.dados.dados.getTemperaturaMin()
        currentTempMaxTxtVwHome.text = dadosTempoViewModel.dados.dados.getTemperaturaMax()
        currentSensacaoDataTxtVwHome.text = dadosTempoViewModel.dados.dados.getSensacaoTerminca()
        currentSunriseCardVwHome.text = dadosTempoViewModel.dados.dados.getNascerSol()
        currentSunsetCardVwHome.text = dadosTempoViewModel.dados.dados.getPorSol()
    }
}
