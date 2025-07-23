package com.mani_group.mani.data

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Message(
    var text: String = "",
    var id: String = "",
    val uid: String = "",
//    val date: Long = System.currentTimeMillis(),
//    val heure: String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()),
//    val dateheure: String = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date()),
//    val type: String = "text",
    val smsid: String = "",
    var timestamp: Long = System.currentTimeMillis()
)
data class ConversationPreview(
    val smsid: String,
    val lastMessage: String,
    val timestamp: Long,
    val receiverUid: String,
    val img: String
)
