package com.example.projeto_meny2020.classes

import android.util.Patterns
import com.example.projeto_meny2020.throws.UsuarioEmailException
import com.example.projeto_meny2020.throws.UsuarioNomeException
import com.example.projeto_meny2020.throws.UsuarioSenhaException
import java.util.regex.Pattern

class UsuarioMock(
    val nome: String,
    val email: String,
    val senha: String
) {
    init {
        if (checaNome(nome) != true) throw UsuarioNomeException()
        if (checaSenha(senha) == false) throw UsuarioSenhaException()
        //Esta função é diferente das outras, seu retorno é oposto pois se usa uma classe do Android.
        //if (!checaEmail(email)) throw UsuarioEmailException()   <-ISTO TBM EXISTE
        if (!checaEmail(email)) throw UsuarioEmailException()
    }

    fun checaSenha(_senha: String): Boolean{
        val senhaMinima = 6
        if(_senha.length < senhaMinima) return false
        return true
    }
    //retorna true caso nome esteja correto, e false caso contrário.
    fun checaNome(_nome: String): Boolean{
        if(_nome.contains(Regex("^([A-Za-z\\u00C0-\\u00D6\\u00D8-\\u00f6\\u00f8-\\u00ff\\s]*)\$"))) return true
        return false
    }

    fun checaEmail(_email: String): Boolean {
        //Patterns.EMAIL_ADDRESS.matcher(_email).matches()
        val padrao = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
        )
        return padrao.matcher(_email).matches()
    }
}