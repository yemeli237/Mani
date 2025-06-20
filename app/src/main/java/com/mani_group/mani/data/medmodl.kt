package com.mani_group.mani.data

data class medmodl(
    val id : String = "",
    val nom : String = "",
    val pharmacie : String = "",
    val dosage : String ="",
    val conseil : String = "",
    val description : String ="",
    val prix : String = "",
    val prixnet : String = "",
    val quantite : String ="",
    val age : String ="",
    val image : List<String> = emptyList(),
    val categorie : String = "",
    val autre : Map<String, String> = mapOf(),
    val like : Int = 0
)
