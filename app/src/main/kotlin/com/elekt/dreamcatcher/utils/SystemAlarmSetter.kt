package com.elekt.dreamcatcher.utils

import android.app.AlarmManager
import android.content.Context
import android.preference.PreferenceManager
import com.elekt.dreamcatcher.ALARM_STRING_SET
import com.elekt.dreamcatcher.SHARED_PREFERENCES_NAME
import com.elekt.dreamcatcher.model.Alarm
import com.google.gson.Gson
import java.util.*

class SystemAlarmSetter(val context: Context) {
    private val alarmManager: AlarmManager

    init {
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    fun setRepeatingAlarm(alarm: Alarm){
        removeAlarmFromSharedPreferences(alarm)
        alarm.isRepeating = true
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarm.calendar.time.time, 24*60*60*1000, alarm.getAlarmIntent(context))
        saveAlarmToSharedPreferences(alarm)

    }

    fun setOneTimeAlarm(alarm: Alarm){
        removeAlarmFromSharedPreferences(alarm)
        alarm.isRepeating = false
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarm.calendar.time.time, alarm.getAlarmIntent(context))
        saveAlarmToSharedPreferences(alarm)
    }

    fun cancelAlarm(alarm: Alarm) {
        alarmManager.cancel(alarm.getAlarmIntent(context, true))
        removeAlarmFromSharedPreferences(alarm)
    }

    private fun saveAlarmToSharedPreferences(alarm: Alarm){
        val preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val prefsEditor = preferences.edit()
        val gson = Gson()
        val alarmStringSet = HashSet<String>(Collections.unmodifiableSet(preferences.getStringSet(ALARM_STRING_SET, HashSet<String>())))
        val alarmJson = gson.toJson(alarm)
        alarmStringSet.add(alarmJson)
        prefsEditor.putStringSet(ALARM_STRING_SET, alarmStringSet)
        prefsEditor.apply()
    }

    private fun removeAlarmFromSharedPreferences(alarm: Alarm){
        val preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val prefsEditor = preferences.edit()
        val gson = Gson()
        val alarmStringSet = HashSet<String>(Collections.unmodifiableSet(preferences.getStringSet(ALARM_STRING_SET, HashSet<String>())))
        var removeAlarm: String? = null
        for (alarmJson in alarmStringSet){
            val readAlarm = gson.fromJson(alarmJson, Alarm::class.java)
            if(alarm.equals(readAlarm)){
                removeAlarm = alarmJson
            }
        }
        alarmStringSet.remove(removeAlarm)

        prefsEditor.putStringSet(ALARM_STRING_SET, alarmStringSet)
        prefsEditor.apply()
    }
}