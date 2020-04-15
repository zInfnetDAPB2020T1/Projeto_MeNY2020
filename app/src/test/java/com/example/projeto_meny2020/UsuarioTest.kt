package com.example.projeto_meny2020

import com.example.projeto_meny2020.classes.UsuarioMock
import com.example.projeto_meny2020.throws.UsuarioEmailException
import com.example.projeto_meny2020.throws.UsuarioNomeException
import com.example.projeto_meny2020.throws.UsuarioSenhaException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UsuarioTest {

    lateinit var usuarioMock: UsuarioMock

    @Before
    fun setupUsuario(){
        usuarioMock = UsuarioMock("Camargc", "yuri@gmail.com", "camarguinho")
    }

    @Test
    fun checa_nome_test(){
        try {
            usuarioMock = UsuarioMock("@Kamargo", "joao@camargo", "senhasecreta")
            assertTrue(false)
        } catch (e: UsuarioNomeException){
            assertEquals(UsuarioNomeException().message, e.message)
        }
    }

    @Test
    fun checa_senha_test(){
        try{
            usuarioMock = UsuarioMock("Kamargo", "joao@camargo", "123")
            assertTrue(false)
        } catch (e: UsuarioSenhaException){
            assertEquals(UsuarioSenhaException().message, e.message)
        }
    }

    @Test
    fun checa_email_test(){
        try{
            usuarioMock = UsuarioMock("Kamargo", "ab.com", "123456")
            assertTrue(false)
        } catch (e: UsuarioEmailException){
            assertEquals(UsuarioEmailException().message, e.message)
        }
    }

}