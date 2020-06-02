package com.tempigo.projeto_meny2020.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tempigo.projeto_meny2020.R
import com.tempigo.projeto_meny2020.classes.modelsRetrofit.RespostaTempoDaily
import com.tempigo.projeto_meny2020.viewModel.DadosTempoViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.File
import java.lang.Exception

//val SPREFNAME = "rcyVwPref"

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
        if (dadosTempoViewModel.dadosLiveDataDaily.hasObservers()) {
            dadosTempoViewModel.dadosLiveDataDaily.removeObservers(viewLifecycleOwner)
        }
        val observer = Observer<RespostaTempoDaily> {
            currentTempMinTxtVwHome.text = it.data!![0].getMin().removeSuffix("C")
            currentTempMaxTxtVwHome.text = it.data!![0].getMax().removeSuffix("C")
        }
        dadosTempoViewModel.dadosLiveDataDaily.observe(viewLifecycleOwner, observer)

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dadosTempoViewModel.currentFragment = this

        currentTempMaxTxtVwHome.setColors(R.color.primaryColor, R.color.colorMax)
        currentTempMinTxtVwHome.setColors2(R.color.colorMinGradient, R.color.colorMin)
        currentTempTxtVwHome.setColors3(R.color.primaryColor, R.color.primaryLightColor)

        DadosEViews()
    }

    fun DadosEViews() {
        if (!dadosTempoViewModel.getJaDeuGet()) {
            Toast.makeText(
                this@HomeFragment.context!!,
                R.string.carregando_dados,
                Toast.LENGTH_SHORT
            ).show()
        }

        getDadosViewModel()
    }

    fun getDadosViewModel(){
        val fileCEscrever = File(dadosTempoViewModel.fileDir, "getRequestTimeCurrent.txt")
        val fileDEscrever = File(dadosTempoViewModel.fileDir,"getRequestTimeDaily.txt")

        val fileCSalvar = File(dadosTempoViewModel.fileDir, "dadosSalvosCurrent.txt")
        val fileDSalvar = File(dadosTempoViewModel.fileDir, "dadosSalvosDaily.txt")

        val callback: () -> Unit = {
            currentDescriptionTxtVwHome.text = dadosTempoViewModel.DadosCurrent().getDescricao()

            currentTempTxtVwHome.text = dadosTempoViewModel.DadosCurrent().getTemperatura()

//            currentTempMinTxtVwHome.text = dadosTempoViewModel.DadosCurrent().getTemperaturaMin()
//
//            currentTempMaxTxtVwHome.text = dadosTempoViewModel.DadosCurrent().getTemperaturaMax()

            dicaDoDiaTxtVwHome.text = dadosTempoViewModel.DadosCurrent().getDicaDoDia()

            currentSensacaoDataTxtVwHome.text =
                dadosTempoViewModel.DadosCurrent().getSensacaoTerminca()
            currentSunriseCardVwHome.text = dadosTempoViewModel.DadosCurrent().getNascerSol()
            currentSunsetCardVwHome.text = dadosTempoViewModel.DadosCurrent().getPorSol()
            currentCityTxtVwHome.text = dadosTempoViewModel.DadosCurrent().getNomeCidade()

            iconImgVwHome.setImageResource(dadosTempoViewModel.DadosCurrent().getIconeTemp())
            //test.text = dadosTempoViewModel.DadosCurrent().getNuvensPrc()
        }

        val passarRc = infosRcyVwHome
        try {
            dadosTempoViewModel.RetrofitGetDataWeatherComplete(fileCEscrever, fileDEscrever, fileCSalvar, fileDSalvar, passarRc, this.context!!, callback)
        }catch (e: Exception){
            Log.e("ERROR VIEWMODEL", e.message!!)

        }
    }
}
