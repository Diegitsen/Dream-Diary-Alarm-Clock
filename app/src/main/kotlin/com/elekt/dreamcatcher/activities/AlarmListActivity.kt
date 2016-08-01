package com.elekt.dreamcatcher.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.elekt.dreamcatcher.*
import com.elekt.dreamcatcher.model.Alarm
import com.elekt.dreamcatcher.utils.TimePickerFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.alarm_setting_activity.*
import java.util.*

class AlarmListActivity : AppCompatActivity() {
    private var adapter: AlarmListAdapter? = null
    private val gson = Gson()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater = menuInflater
        inflater.inflate(R.menu.alarm_list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.open_dream_diary -> {
                val i = Intent(applicationContext, DreamSavingActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                i.putExtra(PLAY_ALARM_SOUND, false)
                applicationContext.startActivity(i)
            }
        }
        return true
    }

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

