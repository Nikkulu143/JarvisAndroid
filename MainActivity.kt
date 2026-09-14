package com.jarvis.voice

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.TextView
import java.util.Locale

class MainActivity : Activity(), TextToSpeech.OnInitListener {
    private lateinit var tts: TextToSpeech
    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        status = findViewById(R.id.status)
        tts = TextToSpeech(this, this)

        requestPermissions(arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CONTACTS
        ), 10)

        findViewById<Button>(R.id.listen).setOnClickListener { listen() }
    }

    private fun listen() {
        val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-IN")
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Jarvis listening...")
        startActivityForResult(i, 20)
    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 20 && resultCode == RESULT_OK) {
            val text = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull() ?: return
            status.text = "You: $text"
            handleCommand(text.lowercase(Locale.getDefault()))
        }
    }

    private fun handleCommand(command:String) {
        when {
            command.contains("hello") || command.contains("hi") ->
                speak("Hello bro, Jarvis ready.")
            command.contains("what") && command.contains("message") ->
                speak("Notification access enable cheste WhatsApp messages ni read chesi cheptha.")
            else -> speak("Nee command vini ardham chesukunna. Ee action next version lo connect cheddam.")
        }
    }

    private fun speak(text:String) {
        status.text = text
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "jarvis")
    }

    override fun onInit(statusCode:Int) {
        if (statusCode == TextToSpeech.SUCCESS) tts.language = Locale("en","IN")
    }
    override fun onDestroy() { tts.shutdown(); super.onDestroy() }
}
