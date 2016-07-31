package com.elekt.dreamcatcher.model

import android.app.PendingIntent
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

class Alarm(val calendar: Calendar, var isRepeating: Boolean = false) {
     private var intentSettings: AlarmIntentSettings = AlarmIntentSettings()

    fun getAlarmIntent(context: Context, isCancelling: Boolean = false):PendingIntent {
        return intentSettings.getPendingIntent(context, isCancelling)
    }

    fun whichDay(): String {
        val currentCalendar = Calendar.getInstance()
        val todayEnd = GregorianCalendar(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DATE), 23, 59, 59)
        val alarmDayEnd = GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59)

        if(currentCalendar.after(calendar) ){
            return "In the past"
        }

        if(calendar.before(todayEnd)){
            return "Today"
        }

        if(calendar.before(alarmDayEnd)){
            return "Tomorrow"
        }

        return "In the future"
    }

    override fun equals(other: Any?): Boolean {
        if(other is Alarm) {
            return intentSettings.equals(other.intentSettings) && calendar.time.equals(other.calendar.time)
        }
        return false
    }

    override fun toString(): String {
        val df = SimpleDateFormat("HH:mm", Locale.getDefault())

        return df.format(calendar.time)
    }

    override fun hashCode(): Int{
        var result = calendar.hashCode()
        result = 31 * result + isRepeating.hashCode()
        result = 31 * result + intentSettings.hashCode()
        return result
    }
}
