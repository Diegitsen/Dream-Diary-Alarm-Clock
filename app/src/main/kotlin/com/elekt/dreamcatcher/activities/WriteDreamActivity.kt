package com.elekt.dreamcatcher.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.elekt.dreamcatcher.DIRECTORY
import com.elekt.dreamcatcher.R
import kotlinx.android.synthetic.main.write_dream_activity.*
import java.io.File
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.*


class WriteDreamActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.write_dream_activity)

        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)

        val dirPath = intent.getStringExtra(DIRECTORY)
        val timestamp = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault()).format(Calendar.getInstance().time)
        val out = PrintWriter(File(dirPath, timestamp.plus(".txt")))

        dream_text.setSelection(dream_text.text.length)


        save_button.setOnClickListener {
            out.println(dream_text.text)
            out.flush()
            out.close()
            finish()
        }

        cancel_button.setOnClickListener {
            finish()
        }
    }
}
