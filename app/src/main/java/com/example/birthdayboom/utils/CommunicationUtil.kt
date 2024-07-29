package com.example.birthdayboom.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CommunicationUtil @Inject constructor(@ApplicationContext private val context: Context) {
    fun makePhoneCall(phoneNumber: String){
       try {
           val intent = Intent(Intent.ACTION_DIAL).apply {
               data = Uri.parse("tel:$phoneNumber")
               addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
           }
           context.startActivity(intent)
       } catch (e: Exception){
           Toast.makeText(context, "Unable to make call", Toast.LENGTH_SHORT).show()
       }
    }

    @SuppressLint("IntentReset")
    fun sendTextMessage(message: String, phoneNumber: String){
       try {
           val intent = Intent(Intent.ACTION_SENDTO).apply {
               data = Uri.parse("smsto:$phoneNumber")
               putExtra("sms_body",message)
               addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
           }
           context.startActivity(intent)
       } catch (e: Exception){
           Toast.makeText(context, "Messaging not installed", Toast.LENGTH_SHORT).show()
       }
    }

    fun sendWhatsappMessage(message: String, phoneNumber: String){
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("http://api.whatsapp.com/send?phone=91${phoneNumber}&text=${message}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception){
            Toast.makeText(context, "Whatsapp not installed", Toast.LENGTH_SHORT).show()
        }
    }
}