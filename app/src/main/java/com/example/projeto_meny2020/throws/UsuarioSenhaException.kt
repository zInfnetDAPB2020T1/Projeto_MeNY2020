package com.example.projeto_meny2020.throws

class UsuarioSenhaException : Throwable() {
    override val message: String?
        get() = "Mensagem de ERRO da SENHA"
}
