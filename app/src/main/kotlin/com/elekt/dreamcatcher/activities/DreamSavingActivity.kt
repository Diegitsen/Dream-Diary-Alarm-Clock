package com.elekt.dreamcatcher.activities

import android.app.ProgressDialog
import android.content.Intent
import android.media.MediaRecorder
import android.media.RingtoneManager
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.elekt.dreamcatcher.DIRECTORY
import com.elekt.dreamcatcher.PLAY_ALARM_SOUND
import com.elekt.dreamcatcher.R
import kotlinx.android.synthetic.main.dream_caching_activity.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class DreamSavingActivity : AppCompatActivity() {

    private val DIARY_DIRECTORY_FILE = File(Environment.getExternalStorageDirectory().path.plus("/DreamDiary/"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isPlayingAlarmSound = intent.getBooleanExtra(PLAY_ALARM_SOUND, true)
        setContentView(R.layout.dream_caching_activity)

        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)

        if(!DIARY_DIRECTORY_FILE.exists()){
            DIARY_DIRECTORY_FILE.mkdirs()
        }

        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val ringtone = RingtoneManager.getRingtone(applicationContext, notification)
        if(isPlayingAlarmSound == true) {
            ringtone.play()
        }

        record_button.setOnClickListener { view ->
            if(isPlayingAlarmSound == true) {
                ringtone.stop()
            }

            val mediaRecorder = MediaRecorder()
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            val timestamp = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault()).format(Calendar.getInstance().time)
            val audioFile = File(DIARY_DIRECTORY_FILE.path, timestamp.plus(".3gp"))
            audioFile.createNewFile()

            mediaRecorder.setOutputFile(audioFile.path)
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
            mediaRecorder.prepare()
            mediaRecorder.start()

            val recordDialog = ProgressDialog(this@DreamSavingActivity)
            recordDialog.setTitle("Recording Dream Entry")
            recordDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            recordDialog.setCancelable(false)
            recordDialog.setButton(ProgressDialog.BUTTON_POSITIVE, "Stop Recording", { dialogInterface, i ->
                mediaRecorder.stop()
                mediaRecorder.release()
                finish()
            })
            recordDialog.show()
        }

        write_button.setOnClickListener { view ->
            if(isPlayingAlarmSound == true) {
                ringtone.stop()
            }

            val i = Intent(this@DreamSavingActivity, WriteDreamActivity::class.java)
            i.putExtra(DIRECTORY, DIARY_DIRECTORY_FILE.path)
            startActivity(i)
            finish()
        }

        cancel_button.setOnClickListener { view ->
            if(isPlayingAlarmSound == true) {
                ringtone.stop()
            }
            finish()
        }

    }

    override fun onPause() {
        super.onPause()
    }
}
