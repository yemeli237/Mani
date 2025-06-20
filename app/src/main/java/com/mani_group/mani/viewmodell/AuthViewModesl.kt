package com.mani_group.mani.viewmodell

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.mani_group.mani.data.Utilisateur

class AuthViewModesl: ViewModel() {
    private val auth = Firebase.auth
    private val firestore = Firebase.firestore

    fun Login(email: String, password: String,onResult: (Boolean, String) -> Unit){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    // Connexion réussie
                    onResult(true, "")
                }else{
                    // Connexion échouée
                    onResult(false, it.exception?.localizedMessage?: "")
                }
            }
    }
    fun Singin(
        email: String,
        password: String,
        name: String,
        img: String,
        genre: String,
        numero: String,
        opperateurbanque: String,
        contact: String,
        profession: String,
        notification: List<String> = emptyList(),
        panier: List<String> = emptyList(),
        like: List<String> = emptyList(),
        onResult: (Boolean, String) -> Unit,
    ){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val userid = it.result.user?.uid
                    val User = Utilisateur(
                        nom = name,
                        email = email,
                        password = password,
                        Id = userid!!,
                        numero = numero,
                        img = img,
                        genre = genre,
                        opperateurbanque = opperateurbanque,
                        profession = profession,
                        contact = contact,
                        panier = panier,
                        like = like,
                        notification = notification
                    )
                    firestore.collection("users").document(userid).set(User).addOnCompleteListener { dbtask ->
                        if (dbtask.isSuccessful){
                            onResult(true, "Creation du compte reussi")
                        }else{
                            onResult(false, dbtask.exception?.localizedMessage?: "Une erreur c'est produite")
                        }
                    }
                }else{
                    onResult(false, it.exception?.localizedMessage?: "")
                }
            }

    }
    fun anonyme(result: (Boolean, ) -> Unit) {
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    result(true)
                }else{
                    result(false)
                }
            }
    }
}