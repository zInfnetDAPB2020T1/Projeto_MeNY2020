package com.example.projeto_meny2020.classes

import android.util.Patterns

class UsuarioMock(
    val nome: String,
    val email: String,
    val senha: String
) {
    fun checaSenha(_senha: String): Boolean{
        val senhaMinima = 6
        if(_senha.length < senhaMinima) return false
        return true
    }

    fun checaNome(_nome: String): Boolean{
        if(_nome.contains(Regex("^([A-Za-z\\u00C0-\\u00D6\\u00D8-\\u00f6\\u00f8-\\u00ff\\s]*)\$"))) return true
        return false
    }

    fun checaEmail(_email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(_email).matches()
}