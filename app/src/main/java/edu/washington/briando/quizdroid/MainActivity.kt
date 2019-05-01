package edu.washington.briando.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var topicArray = arrayOf("math", "physics", "marvelSuperHeroes")

        for (i in topicArray) {
            var id = resources.getIdentifier(i, "id", packageName)
            var btn = findViewById<Button>(id)
            btn.setOnClickListener {
                var quizIntent = Intent(baseContext, Topics::class.java);
                quizIntent.putExtra("topic", i)
                startActivity(quizIntent)
            }
        }
    }
}
