package com.mani_group.mani

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.mani_group.mani.ui.theme.page.Actus
import com.mani_group.mani.ui.theme.page.Aide
import com.mani_group.mani.ui.theme.page.CategoriParProduit
import com.mani_group.mani.ui.theme.page.Chat
import com.mani_group.mani.ui.theme.page.DetailPage
import com.mani_group.mani.ui.theme.page.DetailPagePharmacie
import com.mani_group.mani.ui.theme.page.Donsang
import com.mani_group.mani.ui.theme.page.EditUser
import com.mani_group.mani.ui.theme.page.Menu
import com.mani_group.mani.ui.theme.page.PagePro
import com.mani_group.mani.ui.theme.page.Pharmacie
import com.mani_group.mani.ui.theme.page.Post
//import com.mani_group.mani.ui.theme.page.Pharmacie
import com.mani_group.mani.ui.theme.page.ResultProduit
import com.mani_group.mani.ui.theme.page.UtilisateurInfo
import com.mani_group.mani.ui.theme.page.chat_interface.Conversation
import com.mani_group.mani.ui.theme.page.chat_interface.LoadConversation
import com.mani_group.mani.ui.theme.page.maniai.ChatBot

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isConnected = Firebase.auth.currentUser != null
            val firepage = if (isConnected) Route.Home else Route.Choixconnexion
        val navctl = rememberNavController()
            GlobalNav.navctl = navctl
            NavHost(navController = navctl, startDestination = Route.Actus, builder = {
                composable(Route.Login){
                    Login(navctl)
                }
                composable("${Route.Singin}"){
                    val img = it.arguments?.getString("idimg")
                    Singin(navctl,)
                }
                composable(Route.Home){
                    Home(navctl)
                }
                composable(Route.Menu){
                    Menu(navctl)
                }
                composable(Route.Pharmacie){
                    Pharmacie(navctl)
                }
                composable(Route.Actus){
                    Actus(navctl)
                }

                composable(Route.Chat){
                    Chat(navctl)
                }

                composable(Route.Choixconnexion){
                    Choixconnexion(navctl)
                }

                composable("${Route.CategoriParProduit}/{id}"){
                    val categorie = it.arguments?.getString("id")
                    CategoriParProduit(navctl, categorie = categorie!!)
                }
                composable(Route.UtilisateurInfo){
                    UtilisateurInfo(navctl)
                }
                composable("${Route.DetailProduit}/{idproduit}"){
                    val produit = it.arguments?.getString("idproduit")
                    DetailPage(navctl, produit = produit!!)
                }
                composable("${Route.DetailPharmacie}/{idpharmacie}"){
                    val pharmacie = it.arguments?.getString("idpharmacie")
                    DetailPagePharmacie(navctl, produit = pharmacie!!)
                }
                composable(Route.PagePro){
                    PagePro(navctl)
                }
                composable(Route.Aide){
                    Aide(navctl)
                }
                composable("${Route.ResultProduit}/{filtre}"){
                    val medicament = it.arguments?.getString("filtre")
                    ResultProduit(navctl, medicament)
                }
                composable(Route.EditUser){
                    EditUser(navctl)
                }
                composable(Route.UploadData){
                    UploadData(navctl)
                }
                composable(Route.Post){
                    Post(navctl)
                }
                composable(Route.Donsang){
                    Donsang(navctl)
                }
                composable("${Route.Conversation}/{id}"){
                    val id = it.arguments?.getString("id")
                    Conversation(navctl, id)
                }
                composable("${Route.LoadConversation}/{id}"){
                    val id = it.arguments?.getString("id")
                    LoadConversation(navctl, id)
                }
                composable(Route.ChatBot){
                    ChatBot()
                }

            })
        }
    }
}

object GlobalNav{
    lateinit var navctl: NavHostController
}

