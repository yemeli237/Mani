package com.mani_group.mani.data

//data class pharmacie(
//    val nom: String = "",
//    val id: String ="",
//    val ouverture: String ="",
//    val fermeture : String = "",
//    val localisation : String = "",
//    val ville : String ="",
//    val region : String = "",
//    val image : List<String> = emptyList(),
//    val telephone : String = "",
//    val garde : Boolean = false,
//    val ouvert : Boolean = false,
//)

data class Pharmacie(
    val nom: String = "",
    val telephone: List<String> = emptyList(),
    val adresse: String = "",
    val region: String = "",
    val ouvert: Boolean = true,
    val id : String = ""
)
