package com.elekt.dreamcatcher

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.elekt.dreamcatcher.model.Alarm
import com.elekt.dreamcatcher.utils.SystemAlarmSetter
import java.util.*
import kotlinx.android.synthetic.main.alarm_list_item.view.*

class AlarmListAdapter(context: Context, resId:Int, txtId:Int): ArrayAdapter<Alarm>(context, resId, txtId, ArrayList<Alarm>()) {
    private val systemAlarmSetter: SystemAlarmSetter
    init {
        systemAlarmSetter = SystemAlarmSetter(context)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = super.getView(position, convertView, parent)

        val alarm = getItem(position)
        view.repeat_switch.isChecked = alarm.isRepeating
        view.repeat_switch.text = if(alarm.isRepeating) "Every Day" else alarm.whichDay()

        view.delete_button.setOnClickListener { view ->
            systemAlarmSetter.cancelAlarm(alarm)
            remove(alarm)
        }

        view.repeat_switch.setOnCheckedChangeListener { repeat_switch, state ->
            if(state){
                repeat_switch.text = "Every Day"
                systemAlarmSetter.cancelAlarm(alarm)
                systemAlarmSetter.setRepeatingAlarm(alarm)
            } else {
                repeat_switch.text = alarm.whichDay()
                systemAlarmSetter.cancelAlarm(alarm)
                if(Calendar.getInstance().timeInMillis - alarm.calendar.timeInMillis < 0) {
                    systemAlarmSetter.setOneTimeAlarm(alarm)
                }
            }
        }

        return view
    }
}

