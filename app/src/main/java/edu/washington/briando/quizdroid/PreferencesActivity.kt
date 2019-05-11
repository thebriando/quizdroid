package edu.washington.briando.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.content.*

class PreferencesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_preferences)

        val spinner = findViewById<Spinner>(R.id.minSpinner)
        val minutes = arrayOf("1 min", "5 min", "10 min", "30 min", "60 min", "180 min")
        val a = ArrayAdapter(this@PreferencesActivity, android.R.layout.simple_spinner_item, minutes)
        spinner.adapter = a


        val linkInput = findViewById<TextView>(R.id.linkInput)
        val saveBtn = findViewById<Button>(R.id.saveBtn)
        saveBtn.setOnClickListener {
            val link = linkInput.text
            val refreshRate = spinner.selectedItem
            // do something here when link and refresh rate are inputted
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
