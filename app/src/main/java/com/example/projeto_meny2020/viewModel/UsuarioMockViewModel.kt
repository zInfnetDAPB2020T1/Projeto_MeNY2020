package com.example.projeto_meny2020.viewModel

import androidx.lifecycle.ViewModel
import com.example.projeto_meny2020.classes.UsuarioMock

class UsuarioMockViewModel: ViewModel() {
    var usuario: MutableList<UsuarioMock> = mutableListOf<UsuarioMock>()
}