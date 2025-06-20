package com.mani_group.mani

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import com.mani_group.mani.data.Pharmacie




@Composable
fun UploadData(navctl: NavHostController) {
    val pharmacies = listOf(
//        Pharmacie("PHARMACIE LES BEATITUDES", listOf("242 03 75 82", "677 96 21 16"), "Yaoundé: Carrefour LYCEE DE BIYEM - ASSI", "Centre", true),
//        Pharmacie("PHARMACIE CAMEROUNAISE", listOf("242 00 11 33"), "CATHEDRALE YAOUNDE", "Centre", true),
//        Pharmacie("PHARMACIE DU CYPRES", listOf("242 06 47 59"), "Yaoundé: 2ième Carrefour NKOLBISSON", "Centre", true),
//        Pharmacie("PHARMACIE D'ODZA", listOf("222 30 51 33", "697 99 85 90"), "Yaoundé: Carrefour MBOG-ABANG", "Centre", true),
//        Pharmacie("PHARMACIE DE MANGUIERS", listOf("242 06 04 09"), "Yaoundé: Rue DES MANGUIERS", "Centre", true),
//        Pharmacie("PHARMACIE DE LA FOI", listOf("234 89 72 04", "655 43 96 62"), "Yaoundé: PETIT MARCHE D'ODZA", "Centre", true),
//        Pharmacie("PHARMACIE LYRIS", listOf("681 32 45 92"), "Yaoundé: MIMBOMAN CHAPELLE, FACE FEICOM", "Centre", true),
//        Pharmacie("PHARMACIE COLOMBE", listOf("699 10 43 73", "654 83 64 71"), "Yaoundé: TEXACO OMNISPORT", "Centre", true),
//        Pharmacie("PHARMACIE DU LYCÉE BILINGUE", listOf("222 22 98 37", "242 72 2203"), "Yaoundé: CARREFOUR HÔTEL DU PLATEAU", "Centre", true),
//        Pharmacie("PHARMACIE D'EMANA", listOf("222 21 42 94"), "Yaoundé: À CÔTÉ DU SUPERMARCHÉ NOBLESSE EMANA", "Centre", true),
//        Pharmacie("PHARMACIE HORTENSIAS", listOf("698 648 095", "243 60 79 84"), "Yaoundé: polyclinique Stsinga", "Centre", true),
//        Pharmacie("PHARMACIE AIDA", listOf("22 28 57 70", "674 14 57 57"), "Bafia", "Centre", true),
//        Pharmacie("PHARMACIE BASIA", listOf("222 28 54 04"), "Bafia", "Centre", true),
//        Pharmacie("PHARMACIE VIMLI", listOf("222 04 85 38"), "Mbalmayo", "Centre", true),
//        Pharmacie("PHARMACIE NYONG", listOf("697 40 54 36"), "Mbalmayo", "Centre", true),
//        Pharmacie("PHARMACIE PATIENCE", listOf("222 28 17 56"), "Mbalmayo", "Centre", true),
//        Pharmacie("PHARMACIE CANNES", listOf("222 23 68 30"), "Mbandjock", "Centre", true),
//        Pharmacie("PHARMACIE CAMERIC", listOf("679 79 50 79"), "Mbankomo", "Centre", true),
//        Pharmacie("PHARMACIE AFAMBA", listOf("222 20 68 48"), "Obala: NOUVELLE GESTION", "Centre", true),
//        Pharmacie("PHARMACIE SAINT THOMAS", listOf("699 68 59 35"), "Obala", "Centre", true),
//        Pharmacie("PHARMACIE D'OBALA", listOf("222 28 56 78", "677 63 98 26"), "Obala", "Centre", true),
//        Pharmacie("PHARMACIE DE LA RENAISSANCE", listOf("677 98 20 76"), "Sa'a", "Centre", true),
//        Pharmacie("PHARMACIE DE SA'A", listOf("222 28 58 77"), "Sa'a", "Centre", true),
//        Pharmacie("PHARMACIE LES BEATITUDES", listOf("242 03 75 82", "677 96 21 16"), "Yaoundé: Carrefour LYCEE DE BIYEM - ASSI", "Centre", true),
//        Pharmacie("PHARMACIE CAMEROUNAISE", listOf("242 00 11 33"), "CATHEDRALE YAOUNDE", "Centre", true),
//        Pharmacie("PHARMACIE DU CYPRES", listOf("242 06 47 59"), "Yaoundé: 2ième Carrefour NKOLBISSON", "Centre", true),
//        Pharmacie("PHARMACIE D'ODZA", listOf("222 30 51 33", "697 99 85 90"), "Yaoundé: Carrefour MBOG-ABANG", "Centre", true),
//        Pharmacie("PHARMACIE DE MANGUIERS", listOf("242 06 04 09"), "Yaoundé: Rue DES MANGUIERS", "Centre", true),
//        Pharmacie("PHARMACIE DE LA FOI", listOf("234 89 72 04", "655 43 96 62"), "Yaoundé: PETIT MARCHE D'ODZA", "Centre", true),
//        Pharmacie("PHARMACIE VIMLI", listOf("222 04 85 38"), "Mbalmayo", "Centre", true),
//        Pharmacie("PHARMACIE NYONG", listOf("697 40 54 36"), "Mbalmayo", "Centre", true),
//        Pharmacie("PHARMACIE PATIENCE", listOf("222 28 17 56"), "Mbalmayo", "Centre", true),
//        Pharmacie("PHARMACIE CANNES", listOf("222 23 68 30"), "Mbandjock", "Centre", true),
//        Pharmacie("PHARMACIE CAMERIC", listOf("679 79 50 79"), "Mbankomo", "Centre", true),
//        Pharmacie("PHARMACIE AFAMBA", listOf("222 20 68 48"), "Obala: NOUVELLE GESTION", "Centre", true),
//        Pharmacie("PHARMACIE SAINT THOMAS", listOf("699 68 59 35"), "Obala", "Centre", true),
//        Pharmacie("PHARMACIE D'OBALA", listOf("222 28 56 78", "677 63 98 26"), "Obala", "Centre", true),
//        Pharmacie("PHARMACIE DE LA RENAISSANCE", listOf("677 98 20 76"), "Sa'a", "Centre", true),
//        Pharmacie("PHARMACIE DE SA'A", listOf("222 28 58 77"), "Sa'a", "Centre", true),
//        Pharmacie("PHARMACIE DU MAYO BANYO", listOf("699 52 24 77", "695 20 97 01"), "Banyo", "Adamaoua", true),
//        Pharmacie("PHARMACIE CARREFOUR AOUDI", listOf("233 08 08 48", "677 71 64 93"), "Ngaoundéré", "Adamaoua", true),
//        Pharmacie("PHARMACIE ADAMA", listOf("242 25 24 64"), "Ngaoundéré", "Adamaoua", true),
//        Pharmacie("PHARMACIE DE L'ESPERANCE", listOf("222 25 11 76"), "Ngaoundéré", "Adamaoua", true),
//        Pharmacie("PHARMACIE DE L'ENTENTE", listOf("22 19 80 51"), "Ngaoundéré", "Adamaoua", true),
//        Pharmacie("PHARMACIE DE LA GARE NGAOUNDERE", listOf("222 25 16 88", "694 01 01 45"), "Ngaoundéré", "Adamaoua", true),
//        Pharmacie("PHARMACIE GRAND MARCHE NGAOUNDERE", listOf("22 15 78 44", "699 10 05 54"), "Ngaoundéré", "Adamaoua", true),
//        Pharmacie("PHARMACIE LA VINA", listOf("222 25 14 68"), "Ngaoundéré", "Adamaoua", true),
//        Pharmacie("PHARMACIE DE L'ESPERANCE", listOf("698 00 96 72"), "Abong-Mbang", "Est", true),
//        Pharmacie("ORIENT PHARMACY", listOf("242 63 56 33"), "Batouri", "Est", true),
//        Pharmacie("PHARMACIE LA GLOIRE", listOf("222 12 47 48", "6222 24 23 23"), "Bertoua: À côté du SENAJES, quartier TINDAMBA", "Est", true),
                Pharmacie("PHARMACIE STELLA", listOf("222 24 23 37", "6677 42 93 49"), "Bertoua: Quartier BAVELE", "Est", true),
    Pharmacie("PHARMACIE LE BRUXELLOIS", listOf("222 24 17 17", "696 39 93 23"), "Bertoua: Face Délégation du Travail", "Est", true),
    Pharmacie("PHARMACIE LA GÉNÉRALE", listOf("222 24 21 24", "699 53 70 46"), "Bertoua: Derrière Radio AURORE", "Est", true),
    Pharmacie("PHARMACIE GALIEN", listOf("222 24 14 16", "677 89 57 58"), "Bertoua: Face Légion de Gendarmerie", "Est", true),
    Pharmacie("PHARMACIE MOKOLO", listOf("222 24 21 24", "699 53 70 46"), "Bertoua: Derrière Radio AURORE", "Est", true),
    Pharmacie("PHARMACIE DE LA FRONTIÈRE", listOf("699 22 32 99"), "Garoua-Boulaï", "Est", true),
    Pharmacie("PHARMACIE QUSSUR", listOf("675 08 69 08"), "Kousseri", "Extrême-Nord", true),
    Pharmacie("PHARMACIE DU CHARI", listOf("222 29 40 80", "675 25 43 01"), "Kousseri", "Extrême-Nord", true),
    Pharmacie("PHARMACIE CARREFOUR AOUDI", listOf("222 29 40 82", "242 17 24 88"), "Kousseri", "Extrême-Nord", true),
    Pharmacie("PHARMACIE DE MAGA", listOf("699 51 90 59"), "Maga", "Extrême-Nord", true),
    Pharmacie("PHARMACIE KALIAO", listOf("222 29 32 59"), "Maroua", "Extrême-Nord", true),
    Pharmacie("PHARMACIE FERNGO", listOf("222 29 15 43"), "Maroua: Quartier Kakatare", "Extrême-Nord", true),
    Pharmacie("PHARMACIE EXTREME NORD", listOf("22 29 12 40"), "Maroua: Quartier Kakatare", "Extrême-Nord", true),
    Pharmacie("PHARMACIE EMERAUDE", listOf("696 29 73 96"), "Maroua: Quartier Kakatare", "Extrême-Nord", true),
    Pharmacie("PHARMACIE DU SAHEL", listOf("22 29 16 56"), "Maroua", "Extrême-Nord", true),
    Pharmacie("PHARMACIE DU CENTRE MAROUA", listOf("222 29 12 09"), "Maroua", "Extrême-Nord", true),
    Pharmacie("PHARMACIE DU BOULEVARD", listOf("222 29 32 31"), "Maroua", "Extrême-Nord", true),
        Pharmacie("PHARMACIE DE MAROUA", listOf("6222 29 33 14"), "Maroua", "Extrême-Nord", true),
    Pharmacie("PHARMACIE DOMAYO SARL", listOf("699 59 84 83"), "Maroua", "Extrême-Nord", true),
    Pharmacie("PHARMACIE DU MAYO DANAY", listOf("222 29 65 20"), "Yagoua", "Extrême-Nord", true),
    Pharmacie("PHARMACIE DU BON SECOURS", listOf("222 20 25 19", "677 73 68 82"), "Yagoua", "Extrême-Nord", true),
    Pharmacie("PHARMACIE AMBRE", listOf("222 04 01 44"), "Édéa", "Littoral", true),
    Pharmacie("PHARMACIE ARC EN CIEL", listOf("233 46 41 32", "657 699 94 83"), "Édéa", "Littoral", true),
    Pharmacie("PHARMACIE DU PEUPLE", listOf("233 14 61 79"), "Édéa", "Littoral", true),
    Pharmacie("PHARMACIE DE LOUM", listOf("233 49 35 45", "699 53 27 91"), "Loum", "Littoral", true),
    Pharmacie("PHARMACIE NOVA MACK", listOf("243 77 46 95", "677 66 73 37"), "Mbanga", "Littoral", true),
    Pharmacie("PHARMACIE DE MBANGA", listOf("33 49 16 15", "699 75 47 55"), "Mbanga", "Littoral", true),
    Pharmacie("PHARMACIE DE MELONG", emptyList(), "Melong", "Littoral", true),
    Pharmacie("PHARMACIE PRINCIPALE", listOf("233 49 12 11"), "Nkongsamba", "Littoral", true),
    Pharmacie("PHARMACIE MANENGOUBA", listOf("33 49 16 15", "233 49 30 03"), "Nkongsamba", "Littoral", true),
    Pharmacie("PHARMACIE DU TEMPLE", listOf("33 49 16 15", "674 45 85 73"), "Nkongsamba", "Littoral", true),
    Pharmacie("PACIFIC PHARMACY", listOf("33 49 24 26", "677 70 26 31"), "Nkongsamba", "Littoral", true),
    Pharmacie("PHARMACIE DU CARREFOUR", listOf("222 27 56 97"), "Figuil", "Nord", true),
    Pharmacie("PHARMACIE POPULAIRE", listOf("222 27 25 11", "699 86 97 20"), "Garoua", "Nord", true),
    Pharmacie("PHARMACIE PROVINCIALE", listOf("677 64 18 56"), "Garoua", "Nord", true),
    Pharmacie("PHARMACIE MERE D'ENFANT", listOf("2698 48 72 00"), "Garoua", "Nord", true),
    Pharmacie("PHARMACIE DU NORD", listOf("222 27 13 79"), "Garoua", "Nord", true),
    Pharmacie("PHARMACIE DU LAMIDAT", listOf("222 27 22 61"), "Garoua", "Nord", true),
    Pharmacie("PHARMACIE DU CENTRE GAROUA", listOf("22 27 33 10"), "Garoua", "Nord", true),
    Pharmacie("PHARMACIE DE NGONG", listOf("670 57 67 40"), "Garoua", "Nord", true),
    Pharmacie("PHARMACIE DE LA GRANDE MOSQUÉE", listOf("222 27 27 04"), "Garoua", "Nord", true),
    Pharmacie("PHARMACIE DE L'ETOILE", listOf("22 27 13 77"), "Garoua", "Nord", true),
    Pharmacie("PHARMACIE DE L'AMITIE", listOf("222 27 25 86"), "Garoua", "Nord", true),
    Pharmacie("PHARMACIE ASSALAM", listOf("242 17 12 79", "677 18 53 97"), "Garoua", "Nord", true),
    Pharmacie("PHARMACIE CARREFOUR BARMARI", listOf("77 92 54 15"), "Garoua", "Nord", true),
    Pharmacie("PHARMACIE DE GAROUA", listOf("22 27 24 78"), "Garoua", "Nord", true),
    Pharmacie("PHARMACIE DE L'ESPOIR", listOf("222 27 50 49"), "Guider", "Nord", true),
    Pharmacie("PHARMACIE DE TOUBORO", listOf("699 36 92"), "Touboro", "Nord", true),
    Pharmacie("CORPORATE PHARMACY", listOf("222 27 56 97", "33 09 54 56"), "Bamenda", "Nord-Ouest", true),
    Pharmacie("FAMILY PHARMACY", listOf("233 36 24 26", "699 87 50 69"), "Bamenda", "Nord-Ouest", true),
            Pharmacie("GRACE PHARMACY LIMITED", listOf("673 47 01 81"), "Bamenda", "Nord-Ouest", true),
    Pharmacie("KAREM PHARMACY", listOf("233 36 30 09", "695 26 52 59"), "Bamenda", "Nord-Ouest", true),
    Pharmacie("MEZAM PHARMACY", listOf("233 36 22 08", "677 76 90 99"), "Bamenda", "Nord-Ouest", true),
    Pharmacie("MILLENIUM PHARMACY", listOf("233 36 37 49", "677 64 59 60"), "Bamenda", "Nord-Ouest", true),
    Pharmacie("NOBLE PHARMACY", listOf("33 05 23 95"), "Bamenda", "Nord-Ouest", true),
    Pharmacie("PARK PHARMACY", listOf("233 36 37 12", "699 37 62 05"), "Bamenda", "Nord-Ouest", true),
    Pharmacie("PHARMACIE AMEN", listOf("233 36 11 85", "677 54 41 61"), "Bamenda", "Nord-Ouest", true),
    Pharmacie("PHARMACIE ANDREG PHARMACY", listOf("233 36 23 31", "677 64 71 99"), "Bamenda", "Nord-Ouest", true),
    Pharmacie("PHARMACIE BLACK STAR", listOf("233 36 12 04", "677 96 46 79"), "Bamenda", "Nord-Ouest", true),
    Pharmacie("PHARMACIE CITY CHEMIST", listOf("233 36 11 34", "677 76 47 96"), "Bamenda", "Nord-Ouest", true),
    Pharmacie("PHARMACIE CRYSTAL PHARMACY", listOf("233 36 21 55", "677 76 05 08"), "Bamenda", "Nord-Ouest", true),
    Pharmacie("PHARMACIE PROFESSIONAL PLAZA", listOf("233 36 33 20", "677 75 55 73"), "Bamenda", "Nord-Ouest", true),
    Pharmacie("PHARMACIE SAN PAOLO", listOf("233 36 29 31", "677 32 21 93"), "Bamenda", "Nord-Ouest", true),
    Pharmacie("VILEN PHARMACY", listOf("233 36 26 73", "677 54 41 77"), "Bamenda", "Nord-Ouest", true),
    Pharmacie("LIBERTY PHARMACY", listOf("222 27 56 97", "677 68 41 52"), "Mbengwy", "Nord-Ouest", true),
    Pharmacie("PHARMACIE ANDRE DJONGOUE", listOf("233 48 69 51", "699 58 01 25"), "Bafang", "Ouest", true),
    Pharmacie("PHARMACIE DU MARCHE BAFANG", listOf("233 48 65 28", "699 28 35 73"), "Bafang", "Ouest", true),
            Pharmacie("PHARMACIE MOUANKEU", listOf("233 48 70 56", "699 81 78 75"), "Bafang", "Ouest", true),
    Pharmacie("NOUVELLE PHARMACIE DU BENIN", listOf("233 44 15 57"), "Carrefour Auberge, Bafoussam", "Ouest", true),
    Pharmacie("PHARMACIE AMIENOISE", listOf("233 44 51 10"), "Place des Fêtes, Bafoussam", "Ouest", true),
    Pharmacie("PHARMACIE ANGE GABRIEL", listOf("233 44 18 33"), "Feu Rouge, Bafoussam", "Ouest", true),
    Pharmacie("PHARMACIE BINAM", listOf("233 44 32 31"), "Route Foumban, Bafoussam", "Ouest", true),
    Pharmacie("PHARMACIE DE LA MIFI", listOf("233 44 11 63", "677 75 09 38"), "Carrefour TOTAL, Bafoussam", "Ouest", true),
    Pharmacie("PHARMACIE DE LA VISION", listOf("233 44 63 15", "699 51 25 16"), "Sens Interdit, Bafoussam", "Ouest", true),
    Pharmacie("PHARMACIE DES MARTYRS", listOf("233 44 66 80", "699 92 57 69"), "Carrefour BIAO, Bafoussam", "Ouest", true),
    Pharmacie("PHARMACIE DES MONTAGNES", listOf("233 44 44 56", "699 99 41 94"), "Face UCCAO, Bafoussam", "Ouest", true),
    Pharmacie("PHARMACIE DE L'ESPOIR", listOf("233 48 45 93", "699 55 91 34"), "Bagangté", "Ouest", true),
    Pharmacie("PHARMACIE DE LA DIGNITÉ", listOf("233 48 91 27", "677 74 25 63"), "Bagangté", "Ouest", true),
    Pharmacie("PHARMACIE LE VRAI MEDICAMENT", listOf("677 65 92 62"), "Bandja", "Ouest", true),
    Pharmacie("PHARMACIE DU KOUNG-KHI", listOf("699 78 41 98"), "Badjoun", "Ouest", true, "4"),
    Pharmacie("PHARMACIE NEW TODJOM", listOf("699 82 11 12"), "Badjoun", "Ouest", true, "4"),
    Pharmacie("PHARMACIE DE LA MENOUA", listOf("233 45 11 93", "677 77 15 95"), "Dschang", "Ouest", true, "4"),
    Pharmacie("PHARMACIE DE LAH", listOf("233 45 12 79", "677 81 20 85"), "Dschang", "Ouest", true, "4"),
    Pharmacie("PHARMACIE DU CENTRE", listOf("233 45 11 94", "699 98 98 86"), "Dschang", "Ouest", true, "4"),
            Pharmacie("PHARMACIE DU MARCHE", listOf("233 45 21 37", "699 96 61 27"), "Dschang", "Ouest", true, "4"),
    Pharmacie("PHARMACIE PERVENCHE PLUS", listOf("233 45 17 39"), "Dschang", "Ouest", true, "4"),
    Pharmacie("PHARMACIE CENTRALE", listOf("699 45 19 44"), "Foumban", "Ouest", true, "4"),
    Pharmacie("PHARMACIE GRENIER/REPUBLIQUE", listOf("233 44 63 56", "699 66 46 91"), "Foumban", "Ouest", true, "4"),
    Pharmacie("PHARMACIE DU BIEN ETRE PLUS", listOf("696 92 72 22", "699 66 46 91"), "Mbouda", "Ouest", true, "4"),
    Pharmacie("PHARMACIE TOP ETOILE", listOf("233 48 51 19", "699 98 82 36"), "Mbouda", "Ouest", true, "4"),
    Pharmacie("PHARMACIE D'AMBAM", listOf("699 78 25 75"), "Ambam", "Sud", true, "4"),
    Pharmacie("PHARMACIE KYE OSSI 3 NATIONS", listOf("656 20 40 30"), "Ambam", "Sud", true, "4"),
    Pharmacie("PHARMACIE DE LA MVILA", listOf("222 28 49 10"), "Ebolowa", "Sud", true, "4"),
    Pharmacie("PHARMACIE DU BERCAIL", listOf("222 00 87 37", "222 66 08 99"), "Ebolowa", "Sud", true, "4"),
    Pharmacie("PHARMACIE ROSA PARK", listOf("222 47 90 51"), "Sangmelima", "Sud", true, "4"),
    Pharmacie("AMAZING PHARMACY SARL", listOf("233 32 32 24"), "Buea", "Sud-Ouest", true, "4"),
    Pharmacie("COMPASSIONATE HEALTH PHARMACY", listOf("233 32 25 66", "678 77 24 34"), "Buea", "Sud-Ouest", true, "4"),
    Pharmacie("ENAMEN PHARMACY", listOf("233 32 20 11", "677 31 73 63"), "Buea", "Sud-Ouest", true, "4"),
    Pharmacie("MOUNTAIN II PHARMACY", listOf("233 32 25 46", "243 18 42 72"), "Buea", "Sud-Ouest", true, "4"),
    Pharmacie("PHARMACIE SALVATION", listOf("33 32 25 45", "33 32 30 88"), "Buea", "Sud-Ouest", true, "4"),
    Pharmacie("WINNER'S PHARMACY", listOf("33 32 33 98"), "Buea", "Sud-Ouest", true, "4"),
        Pharmacie("CENTRAL PHARMACY", listOf("677 30 19 26"), "Kumba", "Sud-Ouest", true, "4"),
    Pharmacie("KUMBA CITY PHARMACY LTD", listOf("233 35 45 82"), "Kumba", "Sud-Ouest", true, "4"),
    Pharmacie("PREMIER PHARMACY", listOf("233 35 42 47", "677 54 73 87"), "Kumba, Face Weekend Bar", "Sud-Ouest", true, "4"),
    Pharmacie("CORE PHARMACY", listOf("233 35 16 09"), "Likomba", "Sud-Ouest", true, "4"),
    Pharmacie("DESTINY PHARMACY", listOf("233 61 24 64"), "Limbe", "Sud-Ouest", true, "4"),
    Pharmacie("FAKO ATLANTIC PHARMACY", listOf("233 33 26 30"), "Limbe", "Sud-Ouest", true, "4"),
    Pharmacie("LIMBE PHARMACY LTD Mile One", listOf("233 33 21 97"), "Limbe", "Sud-Ouest", true, "4"),
    Pharmacie("MEDICINE SHOPPE PHARMACY", listOf("233 33 30 73", "679 67 75 57"), "Limbe", "Sud-Ouest", true, "4"),
    Pharmacie("PRIME PHARMACY", listOf("233 33 29 28"), "Limbe", "Sud-Ouest", true, "4"),
    Pharmacie("RAINBOW CHEMISTS", listOf("233 33 24 25"), "Limbe", "Sud-Ouest", true, "4"),
    Pharmacie("PHARMACIE FRIVERALIN", listOf("699 98 35 05"), "Mutengene", "Sud-Ouest", true, "4"),
    Pharmacie("MIDLAND PHARMACY", listOf("670 53 18 60"), "Muyuka", "Sud-Ouest", true, "3"),
    Pharmacie("PHARMACIE DU SOURIRE", listOf("699 81 46 35"), "Muyuka", "Sud-Ouest", true, "2"),
    Pharmacie("PHARMACIE FAITH STANDARD", listOf("242 66 01 21", "679 73 78 96"), "Tiko", "Sud-Ouest", true, "1"),



    )



    val db = FirebaseFirestore.getInstance()

    fun ajouterPharmacies() {
        pharmacies.forEach { pharmacie ->
            db.collection("pharmacies")
                .add(pharmacie)
                .addOnSuccessListener {
                    println("Pharmacie ajoutée : ${pharmacie.nom}")
                }
                .addOnFailureListener { e ->
                    println("Erreur : ${e.message}")
                }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { ajouterPharmacies() }) {
            Text("Uploader les pharmacies")
        }
    }

}

@Composable
fun UploadPharmaciesScreen() {

}