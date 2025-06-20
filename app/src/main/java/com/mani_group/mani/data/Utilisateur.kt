package com.mani_group.mani.data

data class Utilisateur(
    val nom: String,
    val email: String,
    val password: String,
    val Id: String,
    val numero: String,
    val img: String,
    val genre: String,
    val opperateurbanque: String,
    val profession: String,
    val contact: String,
    val panier: List<String> = emptyList(),
    val like: List<String> = emptyList(),
    val notification: List<String> = emptyList(),
)
