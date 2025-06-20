package com.mani_group.mani.data

data class donsangmodel(
    val nom : String = "",
    val Prenom : String = "",
    val age : Int = 0,
    val groupesanguin : String = "",
    val taille : Int = 0,
    val antecedent : String = "",
    val maladiehereditaire : Boolean = false,
    val regimealiemtaire : String = "",
    val examain : String = "",
    val sexe : String,
)
