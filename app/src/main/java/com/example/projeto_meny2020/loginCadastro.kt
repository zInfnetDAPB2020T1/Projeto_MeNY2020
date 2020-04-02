package com.example.projeto_meny2020

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.projeto_meny2020.classes.UsuarioMock
import com.example.projeto_meny2020.viewModel.UsuarioMockViewModel
import kotlinx.android.synthetic.main.activity_login_cadastro.*

class loginCadastro : AppCompatActivity() {

    lateinit var viewModelMock: UsuarioMockViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_cadastro)

        viewModelMock = ViewModelProviders.of(this)[UsuarioMockViewModel::class.java]
    }

    //
    // AREA DE LOGIN
    //

    fun btnCLickLogin(v: View){
        //variavel confere de controle se achou conta ou nao
        var confere = false

        //Confere o usuario no firebase ou na classe mockada(por enquanto)
        val valorEmail = emailTxtInputLogin.text.toString()
        val valorSenha = senhaTxtInputLogin.text.toString()


        viewModelMock.usuario.forEach {
            if(it.email == valorEmail && it.senha == valorSenha){
                val intent = Intent(applicationContext, TesteTelaActivity::class.java)
                intent.putExtra("usuario", it.nome)
                startActivity(intent)
                confere = true
                return@forEach
            }
        }

        if(!confere){
            criaToast("Senha ou Email errados, por favor, tente novamente.", Gravity.CENTER)
        }

    }

    //
    //FIM AREA DE LOGIN
    //

    //
    // AREA DE CADASTRO
    //

    fun btnClickCadastro(v: View){
        //Cadastra o usuario no firebase ou na classe mockada(por enquanto)
        //pega os valores dos campos inseridos
        val valorEmail = emailTxtInputLogin.text.toString()
        val valorSenha = senhaTxtInputLogin.text.toString()
        val valorNome = nomeTxtInputLogin.text.toString()

        //checa se algum valor veio vazio

        if(valorEmail.isEmpty()|| valorSenha.isEmpty() || valorNome.isEmpty()){
            criaToast("Por favor, preencha os três campos.", Gravity.CENTER)
        }else{
            //checa se as funcoes que conferem os inputs retornam false ou true
            if (!checaEmail(valorEmail)){
                //usando REGEX retono de true caso errado
                criaToast("Por favor, preencha o email corretamente.",Gravity.CENTER)
            }else if(!checaSenha(valorSenha)){
                //Feito a mao retorno de true caso errado
                criaToast("Sua senha precisa ter mais do que 6 digitos.",Gravity.CENTER)
            }else if(!checaNome(valorNome)){
                //Feito a mao com REGEX retorno de true caso errado
                criaToast("Por favor, não use numeros ou caracteres especiais.", Gravity.CENTER)
            }else{
                //variavel de controle para email
                var varControle = true

                //forEach para conferir se o email ja existe
                viewModelMock.usuario.forEach {
                    if(it.email == valorEmail){
                        varControle = false
                        return@forEach
                    }
                }

                //confere a variavel de controle
                if(varControle){
                    Log.d("Funcionou", "tudo funcionando, criando a conta")
                    viewModelMock.usuario.add(UsuarioMock(valorNome,valorEmail,valorSenha))

                    criaToast("Conta criada com sucesso!", Gravity.CENTER)

                    emailTxtInputLogin.setText("")
                    senhaTxtInputLogin.setText("")
                    nomeTxtInputLogin.setText("")
                }else{
                    criaToast("Esse email ja está em uso.", Gravity.CENTER)
                }

            }
        }
    }

    fun criaToast(texto: String, local: Int){
        val toast = Toast.makeText(
            this,
            texto,
            Toast.LENGTH_LONG
        )
        toast.setGravity(local,0,0)
        toast.show()
    }

    fun checaSenha(senha: String): Boolean{
        val senhaMinima = 6
        if(senha.length < senhaMinima) return false
        return true
    }

    fun checaNome(nome: String): Boolean{
        if(nome.contains(Regex("^([A-Za-z\\u00C0-\\u00D6\\u00D8-\\u00f6\\u00f8-\\u00ff\\s]*)\$"))) return true
        return false
    }

    fun checaEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    //
    // FIM AREA DE CADASTRO
    //

    fun btnDummyLogin(v: View){
        //Dummy para mudar de tela pelo facebook/google
        val intent = Intent(this, TesteTelaActivity::class.java)
        startActivity(intent)
    }
}
