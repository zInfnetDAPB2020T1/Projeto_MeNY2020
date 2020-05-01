package com.example.projeto_meny2020.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_meny2020.R
import com.example.projeto_meny2020.classes.recycleInfosModel
import kotlinx.android.synthetic.main.recycler_layout_model.view.*

class InfosTempoAdapter(val infosLista : List<recycleInfosModel>): RecyclerView.Adapter<InfosTempoAdapter.InfosViewHolder>() {

    class InfosViewHolder(view: View): RecyclerView.ViewHolder(view){
        val campoNome =view.typeMetVariable
        val campoInfo = view.dataMetVariable
        val campoIcone = view.iconMetVariable
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfosViewHolder {
        val v = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_layout_model,
                parent,
                false
            )

        val infosViewHolder = InfosViewHolder(v)

        return infosViewHolder
    }

    override fun getItemCount(): Int {
        return infosLista.size
    }

    override fun onBindViewHolder(holder: InfosViewHolder, position: Int) {
        val infoAtual = infosLista[position]
        holder.campoNome.text = infoAtual.nome
        holder.campoIcone.setImageResource(infoAtual.icone)
        holder.campoInfo.text = infoAtual.info
    }
}