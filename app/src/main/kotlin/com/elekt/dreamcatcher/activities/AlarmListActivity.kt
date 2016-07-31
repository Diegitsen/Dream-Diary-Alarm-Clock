package com.elekt.dreamcatcher.activities

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.elekt.dreamcatcher.*
import com.elekt.dreamcatcher.model.Alarm
import com.elekt.dreamcatcher.utils.TimePickerFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.alarm_setting_activity.*
import java.util.*

class AlarmListActivity : AppCompatActivity() {
    private var adapter: AlarmListAdapter? = null
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm_setting_activity)
        adapter = AlarmListAdapter(applicationContext, R.layout.alarm_list_item, R.id.line)

        val preferences = applicationContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val alarmStringSet = HashSet<String>(Collections.unmodifiableSet(preferences.getStringSet(ALARM_STRING_SET, HashSet<String>())))

        for (alarmJson in alarmStringSet){
            val alarm = gson.fromJson(alarmJson, Alarm::class.java)
            adapter!!.add(alarm)
        }

        /* Request user permissions in runtime */
        ActivityCompat.requestPermissions(this@AlarmListActivity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                100)

        action_button.setOnClickListener {
            val newFragment = TimePickerFragment()
            newFragment.setAlarmListAdapter(adapter!!)
            newFragment.show(supportFragmentManager, getString(R.string.new_alarm))
        }

        alarm_list.adapter = adapter
    }
}

