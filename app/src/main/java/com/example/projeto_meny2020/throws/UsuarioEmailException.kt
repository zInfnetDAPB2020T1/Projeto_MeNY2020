package com.example.projeto_meny2020.throws

class UsuarioEmailException : Throwable() {
    override val message: String?
        get() = "Mensagem de ERRO do EMAIL"
}
