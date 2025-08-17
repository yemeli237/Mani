package com.mani_group.mani.data

import androidx.compose.ui.graphics.Color

data class postdata(
//    val titre : String,
    val type: String = "",
    val contenu: String = "",
    val description: String = "",
    val auteur: String = "",
    val date: String = "",
    val like: Int = 0,
    val comment: List<String> = emptyList(),
    val uid: String = "",
    val couleurtext : String = "${couleurprincipal}",
    val couleurbackground : String = "${Color.White}",
    val id : String = "",

    )
