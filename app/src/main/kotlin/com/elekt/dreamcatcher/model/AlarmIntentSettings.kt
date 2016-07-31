package com.elekt.dreamcatcher.model

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.elekt.dreamcatcher.ALARM_REQUEST_CODE
import com.elekt.dreamcatcher.activities.AlarmRecieverBroadcastListener
import com.elekt.dreamcatcher.SHARED_PREFERENCES_NAME

class AlarmIntentSettings(){
    private var requestCode = -1

    private fun getRequestCode(context: Context): Int {
        if (requestCode == -1){
            val preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
            val prefsEditor = preferences.edit()

            requestCode = preferences.getInt(ALARM_REQUEST_CODE, 1000)
            if (requestCode >= Int.MAX_VALUE) {
                requestCode = 1000
            }
            requestCode += 1
            prefsEditor.putInt(ALARM_REQUEST_CODE, requestCode)
            prefsEditor.apply()
        }
        return requestCode
    }

    fun getPendingIntent(context: Context, isCancelling: Boolean): PendingIntent {
        val intent = Intent(context, AlarmRecieverBroadcastListener::class.java)
        val intentFlag = if(isCancelling) PendingIntent.FLAG_UPDATE_CURRENT else 0
        val pendingIntent = PendingIntent.getBroadcast(context, getRequestCode(context), intent, intentFlag)

        return pendingIntent
    }

    override fun equals(other: Any?): Boolean {
        if(other is AlarmIntentSettings){
            return other.requestCode == requestCode
        }
        return false
    }

    override fun hashCode(): Int{
        return requestCode
    }
}