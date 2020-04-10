package com.example.projeto_meny2020.viewModel

import androidx.lifecycle.ViewModel
import com.example.projeto_meny2020.classes.JSONMock

//
//utilizacao é Nome_View_Model.dados.dados.Funcao_Desejada
//

class DadosTempoViewModel(): ViewModel() {
    val dados = JSONMock()
}