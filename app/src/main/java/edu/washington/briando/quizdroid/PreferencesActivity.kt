package edu.washington.briando.quizdroid

import android.app.AlarmManager
import android.app.DownloadManager
import android.app.PendingIntent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.content.*
import android.util.Log

class PreferencesActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_preferences)

        spinner = findViewById(R.id.minSpinner)
        val minutes = arrayOf("1 min", "5 min", "10 min", "30 min", "60 min", "180 min")
        val a = ArrayAdapter(this@PreferencesActivity, android.R.layout.simple_spinner_item, minutes)
        spinner.adapter = a

        val linkInput = findViewById<TextView>(R.id.linkInput)
        val saveBtn = findViewById<Button>(R.id.saveBtn)
        saveBtn.setOnClickListener {
            beginDownload(linkInput.text.toString())

            val intent = Intent(this@PreferencesActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun beginDownload(url: String) {
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.putExtra("jsonUrl", url)

        val receiver = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pending : PendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val interval: Long

        when (spinner.selectedItem.toString()) {
            "1 min" -> interval = 60000
            "5 min" -> interval = 5 * 60000
            "10 min" -> interval = 10 * 60000
            "30 min" -> interval = 30 * 60000
            "60 min" -> interval = 60 * 60000
            "180 min" -> interval = 180 * 60000
            else -> interval = 60 * 60000
        }

        receiver.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            interval,
            pending
        )
    }
}
