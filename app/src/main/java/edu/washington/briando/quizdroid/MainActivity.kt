package edu.washington.briando.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var topicArray = arrayOf("math", "physics", "marvelSuperHeroes")

        for (i in topicArray) {
            var id = resources.getIdentifier(i, "id", packageName)
            var btn = findViewById<Button>(id)
            btn.setOnClickListener {
                val intent = Intent(this, QuizActivity::class.java)
                intent.putExtra("topic", i)
                startActivity(intent)
            }
        }
    }
}
