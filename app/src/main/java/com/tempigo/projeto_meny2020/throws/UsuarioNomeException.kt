package com.tempigo.projeto_meny2020.throws

class UsuarioNomeException: Throwable() {
    override val message: String?
        get() = "Mensagem de ERRO do NOME"
}
