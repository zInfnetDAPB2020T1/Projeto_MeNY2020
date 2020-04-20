package com.example.projeto_meny2020.viewModel

import androidx.lifecycle.ViewModel
import com.example.projeto_meny2020.classes.UsuarioMock

class UsuarioMockViewModel: ViewModel() {
    var usuario: MutableList<UsuarioMock> = mutableListOf<UsuarioMock>(UsuarioMock("teste", "t@teste.com", "123456"))
    val checaUsuario: UsuarioMock = UsuarioMock("teste", "teste@teste.com", "senhateste")

    fun AdicionarConta(_email: String, _nome : String, _senha : String){
        this.usuario.add(UsuarioMock(_email,_nome,_senha))
    }

    fun ChecarCriarConta(valorEmail: String): Boolean{
        var controle = true
        usuario.forEach {
            if(it.email == valorEmail){
                controle = false
                return@forEach
            }
        }

        if(controle) return true
        return false
    }

    fun ConfereUsuario(valorEmail: String, valorSenha: String): Boolean{
        usuario.forEach {
            if(valorEmail == it.email && valorSenha == it.senha){
                return false
            }
        }
        return true
    }
}