package edu.washington.briando.quizdroid

import android.widget.Button
import android.widget.TextView
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class Topics : AppCompatActivity() {

    private val questionMap: Map<String, Array<String>> = mapOf(
        "math" to arrayOf<String>(
            "3 + 5 = ?",
            "5 * 6 = ?"
        ),
        "physics" to arrayOf<String>(
            "Force = mass * ?",
            "What is a valid way to measure energy?"
        ),
        "marvelSuperHeroes" to arrayOf<String>(
            "How many Iron Man movies are there? (Excluding Avengers movies)",
            "How many members are in the Guardians of the Galaxy?"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topics)
        val quizIntent = getIntent()
        val topic = quizIntent.getStringExtra("topic")

        val overview = findViewById<TextView>(R.id.overview)
        overview.text = topic + " Overview"

        val size = questionMap.get(topic)!!.size
        val numQuestions = findViewById<TextView>(R.id.numQuestions)
        numQuestions.text = size.toString() + " Questions"

        val startBtn = findViewById<Button>(R.id.start)
        startBtn.setOnClickListener {
            val quizIntent = Intent(baseContext, Questions::class.java)
            quizIntent.putExtra("questions", questionMap[topic])
            quizIntent.putExtra("questionNum", 0)
            quizIntent.putExtra("numCorrectAns", 0)
            startActivity(quizIntent)
        }
    }
}