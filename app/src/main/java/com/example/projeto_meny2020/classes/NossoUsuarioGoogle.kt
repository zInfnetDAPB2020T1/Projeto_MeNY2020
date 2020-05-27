package com.example.projeto_meny2020.classes

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.io.Serializable

class NossoUsuarioGoogle(
    val usuario: String
) : Serializable{
}