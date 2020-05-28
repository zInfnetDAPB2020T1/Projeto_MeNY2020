package com.example.projeto_meny2020

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.projeto_meny2020.classes.NossoUsuarioGoogle
import com.example.projeto_meny2020.classes.UsuarioMock
import com.example.projeto_meny2020.viewModel.UsuarioMockViewModel
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login_cadastro.*
import java.lang.Exception

class loginCadastro : AppCompatActivity() {

//    lateinit var viewModelMock: UsuarioMockViewModel
    lateinit var googleSignInClient : GoogleSignInClient
    lateinit var callbackManager: CallbackManager
    private val GG_SIGN_IN = 6489
//    private val FC_SIGN_IN = 22
//    private val TW_SIGN_IN = 54
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_cadastro)

        FacebookSdk.sdkInitialize(applicationContext)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()

        val user: FirebaseUser? = auth.currentUser
        if(user != null){
            //ja tem user conectado
            val intent = Intent(this, PrincipalActivity::class.java)
            startActivity(intent)
        }

        //
        //Area de login do facebook
        //
        callbackManager = CallbackManager.Factory.create();
        facebookBtnLogin2.setReadPermissions("email")
        // Callback registration
        facebookBtnLogin2.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {

            override fun onSuccess(loginResult: LoginResult ) {
                // App code
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                // App code
                LoginManager.getInstance().logOut()
                Log.d("Facebook foi cancelado", "Alguem cancelou o facebook")
            }

            override fun onError(exception: FacebookException) {
                // App code
                LoginManager.getInstance().logOut()
                Log.e("Error FB Exception", exception.message!!)
            }
        });

        //
        //Login do google
        //
        googleBtnLogin2.setOnClickListener {
            signInGoogle()
        }

//        viewModelMock = ViewModelProviders.of(this)[UsuarioMockViewModel::class.java]
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GG_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val conta = task.getResult(ApiException::class.java)
                val credencial = GoogleAuthProvider.getCredential(conta!!.idToken, null)
                googleSignInClient.signOut()
                auth.signInWithCredential(credencial)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Bem - vindo, ${auth.currentUser!!.displayName}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            val intent = Intent(this, PrincipalActivity::class.java)
                            startActivity(intent)
                        } else {
                            Log.e("Erro com conexao", "ocorreu algum eror no auth do firebase")
                        }
                    }
            } catch (e: ApiException) {
                Log.e("Erro de API", "Autenticacao com google falhou", e)
            }
        }

        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

//    private fun firebaseAuthComGoogle(act: GoogleSignInAccount){
//        Log.d("act.id", act.id.toString())
//        val credencial = GoogleAuthProvider.getCredential(act.idToken, null)
//        googleSignInClient.signOut()
//        auth.signInWithCredential(credencial)
//            .addOnCompleteListener {
//                if(it.isSuccessful){
//                    val user = auth.currentUser
//                    Toast.makeText(this, "Bem - vindo, ${user!!.displayName}", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this, PrincipalActivity::class.java)
//                    startActivity(intent)
//                }else{
//                    Toast.makeText(this,"ocorreu um erro com a conexao", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }

    private fun handleFacebookAccessToken(token: AccessToken){
        val credencial = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credencial)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    LoginManager.getInstance().logOut()
                    Toast.makeText(
                        this,
                        "Bem - vindo, ${auth.currentUser!!.displayName}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    val intent = Intent(this, PrincipalActivity::class.java)
                    startActivity(intent)
                }else {
                    Log.e("Erro de facebook", "Ocorreu algum eror com o facebook")
                    Toast.makeText(
                        this, "Falha ao conectar.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GG_SIGN_IN)
    }
    //
    // AREA DE LOGIN
    //

//    fun btnCLickLogin(v: View){
//        //Confere o usuario no firebase ou na classe mockada(por enquanto)
//        val valorEmail = emailTxtInputLogin.text.toString()
//        val valorSenha = senhaTxtInputLogin.text.toString()
//
//
//       val resp = viewModelMock.ConfereUsuario(valorEmail, valorSenha)
//        if(!resp){
//            criaToast("Senha ou Email errados, por favor, tente novamente.", Gravity.CENTER)
//        } else{
//            val intent = Intent(this, PrincipalActivity::class.java)
//            startActivity(intent)
//        }
//
//
//    }
//
//    //
//    //FIM AREA DE LOGIN
//    //
//
//    //
//    // AREA DE CADASTRO
//    //
//
//    fun btnClickCadastro(v: View){
//        //Cadastra o usuario no firebase ou na classe mockada(por enquanto)
//        //pega os valores dos campos inseridos
//        val valorEmail = emailTxtInputLogin.text.toString()
//        val valorSenha = senhaTxtInputLogin.text.toString()
//        val valorNome = nomeTxtInputLogin.text.toString()
//
//        //checa se algum valor veio vazio
//
//        if(valorEmail.isEmpty()|| valorSenha.isEmpty() || valorNome.isEmpty()){
//            criaToast("Por favor, preencha os três campos.", Gravity.CENTER)
//        }else{
//            //checa se as funcoes que conferem os inputs retornam false ou true
//            if (!viewModelMock.checaUsuario.checaEmail(valorEmail)){
//                //usando REGEX retono de true caso errado
//                criaToast("Por favor, preencha o email corretamente.",Gravity.CENTER)
//            }else if(!viewModelMock.checaUsuario.checaSenha(valorSenha)){
//                //Feito a mao retorno de true caso errado
//                criaToast("Sua senha precisa ter mais do que 6 digitos.",Gravity.CENTER)
//            }else if(!viewModelMock.checaUsuario.checaNome(valorNome)){
//                //Feito a mao com REGEX retorno de true caso errado
//                criaToast("Por favor, não use numeros ou caracteres especiais.", Gravity.CENTER)
//            }else{
//                //variavel de controle para email
//                var varControle = true
//
//                //forEach para conferir se o email ja existe
//                if(!viewModelMock.ChecarCriarConta(valorEmail)) varControle = false
//
//                //confere a variavel de controle
//                if(varControle){
//                    Log.d("Funcionou", "tudo funcionando, criando a conta")
//                    viewModelMock.AdicionarConta(valorEmail,valorNome,valorSenha)
//
//                    criaToast("Conta criada com sucesso!", Gravity.CENTER)
//
//                    emailTxtInputLogin.setText("")
//                    senhaTxtInputLogin.setText("")
//                    nomeTxtInputLogin.setText("")
//                }else{
//                    criaToast("Esse email ja está em uso.", Gravity.CENTER)
//                }
//
//            }
//        }
//    }

//    fun criaToast(texto: String, local: Int){
//        val toast = Toast.makeText(
//            this,
//            texto,
//            Toast.LENGTH_LONG
//        )
//        toast.setGravity(local,0,0)
//        toast.show()
//    }

    //
    // FIM AREA DE CADASTRO
    //

//    fun btnDummyLogin(v: View){
//        //Dummy para mudar de tela pelo facebook/google
//        val intent = Intent(this, PrincipalActivity::class.java)
//        startActivity(intent)
//    }
}
