package com.elekt.dreamcatcher.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.elekt.dreamcatcher.PLAY_ALARM_SOUND
import com.elekt.dreamcatcher.activities.DreamSavingActivity

class AlarmRecieverBroadcastListener : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val i = Intent(context, DreamSavingActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        i.putExtra(PLAY_ALARM_SOUND, true)
        context.startActivity(i)
    }

}