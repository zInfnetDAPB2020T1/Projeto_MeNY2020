package com.tempigo.projeto_meny2020

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_teste_tela.*

class TesteTelaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teste_tela)

        val valor = intent.getStringExtra("usuario")
        if(valor != null && valor.isNotEmpty()){
            txtViewTesteTela.text = valor
        }
    }
}
