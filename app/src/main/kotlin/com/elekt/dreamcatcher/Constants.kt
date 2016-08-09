package com.elekt.dreamcatcher

import android.os.Environment
import java.io.File

//  Shared PreferencesConstants
val SHARED_PREFERENCES_NAME = "dream_catcher_prefs"
val ALARM_STRING_SET = "alarm_string_set"
val PLAY_ALARM_SOUND = "play_alarm_sound"
val ALARM_REQUEST_CODE = "alarm_request_code"
val DIARY_DIRECTORY_FILE = File(Environment.getExternalStorageDirectory().path.plus("/DreamDiary/"))

