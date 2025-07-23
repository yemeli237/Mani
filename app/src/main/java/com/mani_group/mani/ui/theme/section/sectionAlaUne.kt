package com.mani_group.mani.ui.theme.section

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.mani_group.mani.data.alaune
import com.mani_group.mani.ui.theme.Purple40
import com.mani_group.mani.ui.theme.Purple80


val AlaUne = listOf(
    alaune(
        img = "",
        note = 90,
        couleur = getcolor(Color(0xFF00bf63), Purple80),
        dosage = "200g",
        nom = "Doliprane",
    ),
    alaune(
        img = "",
        note = 90,
        couleur = getcolor(Purple40, Purple80),
        dosage = "500g",
        nom = "Paracetamol",
    ),
    alaune(
        img = "",
        note = 90,
        couleur = getcolor(Color.LightGray, Purple80),
        dosage = "200g",
        nom = "Ubiprofen",
    ),
    alaune(
        img = "",
        note = 90,
        couleur = getcolor(Color.Magenta, Purple80),
        dosage = "500g",
        nom = "Doliprane",
    )
)

fun getcolor(
    color1 : Color,
    color2 : Color
):Brush{
    return Brush.horizontalGradient(
        colors = listOf(color1, color2)
    )
}

@Composable
fun sectionAlaUne(modifire: Modifier) {
    bannier()

}

@Composable
fun bannier(){
    var bannerlist by remember {
        mutableStateOf<List<String>>(emptyList())
    }
    var url by remember {
        mutableStateOf("")
    }


//    LaunchedEffect(Unit) {
////        url = "https://res.cloudinary.com/dcnnsq1xw/image/upload/v1745581244/4_v368dr.png"
//        Firebase.firestore.collection("data")
//            .document("bannier")
//            .get().addOnCompleteListener(){
//                bannerlist = it.result.get("url") as List<String>
//            }
//    }
    LaunchedEffect(url){
        Firebase.firestore.collection("data")
            .document("bannier")
            .addSnapshotListener(){
                value, error ->
                bannerlist = value!!.get("url") as List<String>
            }
    }



    Column(
        modifier = Modifier
    ) {
        val pageetat = rememberPagerState {
            bannerlist.size
        }
        HorizontalPager(state =pageetat, pageSpacing = 16.dp , modifier = Modifier.padding(10.dp).height(160.dp)) {
            AsyncImage(
                model = bannerlist.get(it),
                contentDescription = "Bannier",
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }

}

@Composable
fun AlaUneCard(item:alaune){
    Box(
        modifier = Modifier.clip(RoundedCornerShape(25.dp))
            .background(item.couleur)
            .width(250.dp)
            .height(160.dp)
            .clickable {  }
            .padding(start = 10.dp, end = 10.dp)
    ){
        Column {
            Text(item.nom, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.inverseOnSurface,modifier = Modifier.padding(10.dp))
            Row(modifier = Modifier.padding(10.dp)) {
                Box(
                modifier = Modifier.width(150.dp)
                ) {
                    Icon(imageVector = Icons.Default.ThumbUp, contentDescription = item.img)
                }
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(item.dosage, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.inverseOnSurface)
                    Text("${item.note}", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.inverseOnSurface)
                }
            }
        }
    }
}