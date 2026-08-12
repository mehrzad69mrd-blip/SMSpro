package com.example.smspro.data

import android.content.Context
import android.provider.Telephony

data class SmsMessageModel(
    val id: Long,
    val threadId: Long,
    val address: String,
    val body: String,
    val date: Long,
    val type: Int,
    val read: Boolean
)

class SmsRepository(private val context: Context) {
    fun fetchMessages(): List<SmsMessageModel> {
        val list = mutableListOf<SmsMessageModel>()
        val uri = Telephony.Sms.CONTENT_URI
        val projection = arrayOf(
            Telephony.Sms._ID,
            Telephony.Sms.THREAD_ID,
            Telephony.Sms.ADDRESS,
            Telephony.Sms.BODY,
            Telephony.Sms.DATE,
            Telephony.Sms.TYPE,
            Telephony.Sms.READ
        )

        context.contentResolver.query(uri, projection, null, null, "${Telephony.Sms.DATE} DESC")?.use { cursor ->
            val idCol = cursor.getColumnIndex(Telephony.Sms._ID)
            val threadCol = cursor.getColumnIndex(Telephony.Sms.THREAD_ID)
            val addressCol = cursor.getColumnIndex(Telephony.Sms.ADDRESS)
            val bodyCol = cursor.getColumnIndex(Telephony.Sms.BODY)
            val dateCol = cursor.getColumnIndex(Telephony.Sms.DATE)
            val typeCol = cursor.getColumnIndex(Telephony.Sms.TYPE)
            val readCol = cursor.getColumnIndex(Telephony.Sms.READ)

            while (cursor.moveToNext()) {
                list.add(
                    SmsMessageModel(
                        id = cursor.getLong(idCol),
                        threadId = cursor.getLong(threadCol),
                        address = cursor.getString(addressCol) ?: "",
                        body = cursor.getString(bodyCol) ?: "",
                        date = cursor.getLong(dateCol),
                        type = cursor.getInt(typeCol),
                        read = cursor.getInt(readCol) == 1
                    )
                )
            }
        }
        return list
    }
}
