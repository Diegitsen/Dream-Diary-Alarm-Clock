package com.elekt.dreamcatcher.utils

import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.format.DateFormat
import android.widget.TimePicker
import com.elekt.dreamcatcher.AlarmListAdapter
import com.elekt.dreamcatcher.utils.SystemAlarmSetter
import com.elekt.dreamcatcher.model.Alarm
import com.elekt.dreamcatcher.model.AlarmIntentSettings
import java.util.*

class TimePickerFragment() : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    private var alarmListAdapter: AlarmListAdapter? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        return TimePickerDialog(activity, this, hour, minute,
                DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        val alarmDate = getAlarmDate(hourOfDay, minute)
        val alarm = Alarm(alarmDate)
        val systemAlarmSetter = SystemAlarmSetter(context)

        systemAlarmSetter.setOneTimeAlarm(alarm)
        alarmListAdapter!!.add(alarm)
    }

    fun getAlarmDate(hourOfDay: Int, minute: Int): Calendar {
        val alarmCalendar = Calendar.getInstance()
        alarmCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        alarmCalendar.set(Calendar.MINUTE, minute)
        alarmCalendar.set(Calendar.SECOND, 0)
        alarmCalendar.set(Calendar.MILLISECOND, 0)

        val currentDate = Calendar.getInstance().time

        if(currentDate.after(alarmCalendar.time)){
            alarmCalendar.add(Calendar.DATE, 1)
        }
        return alarmCalendar
    }

    fun setAlarmListAdapter(adapter: AlarmListAdapter){
        alarmListAdapter = adapter
    }

}