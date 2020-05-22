package com.example.projeto_meny2020.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_meny2020.R
import com.example.projeto_meny2020.classes.modelsRetrofit.Forecast
import kotlinx.android.synthetic.main.recycler_dias_seguintes.view.*

class RecyclerViewDailyAdapter(val listaForecast: List<Forecast>, val cidadeNome: String): RecyclerView.Adapter<RecyclerViewDailyAdapter.DailyViewHolder>() {
    class DailyViewHolder(v: View): RecyclerView.ViewHolder(v){
        val campoDia = v.diaProxDias
        val campoMes = v.mesProxDias
        val campoMin = v.minProxDias
        val campoMax = v.maxProxDias
        val campoTemp = v.tempProxDias
        val campoStatus = v.statusProxDias
        val campoPorcentagem = v.porcentagemProxDias
        val campoCidade = v.cidadeEscolhida
        //holder e função não feitos
        val campoIconStatus = v.previsaoStatusIconImgVw
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val v = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_dias_seguintes,
                parent, false
            )

        return DailyViewHolder(v)
    }

    override fun getItemCount(): Int = listaForecast.size

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val item = listaForecast[position]
        holder.campoDia.text = item.getDia()
        holder.campoMax.text = item.getMax()
        holder.campoMax.setColors(R.color.primaryColor, R.color.colorMax)
        holder.campoMes.text = item.getMes()
        holder.campoMin.text = item.getMin()
        holder.campoMin.setColors2(R.color.colorMinGradient, R.color.colorMin)
        holder.campoTemp.text = item.getTemp()
        holder.campoTemp.setColors3(R.color.primaryColor, R.color.primaryLightColor)
        holder.campoPorcentagem.text = item.getPct()
        holder.campoStatus.text = item.getStatus()
        holder.campoCidade.text = cidadeNome
    }
}