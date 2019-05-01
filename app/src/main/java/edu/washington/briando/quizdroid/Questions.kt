package edu.washington.briando.quizdroid

import android.widget.Button
import android.widget.RadioButton
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.RadioGroup
import android.widget.TextView
import android.os.Bundle

class Questions : AppCompatActivity() {
    private var questionNum = 0
    private var correctAns = ""
    private val answerKey = mapOf(
        "3 + 5 = ?" to arrayOf(
            "8",
            "2",
            "3",
            "4"
        ),
        "5 * 6 = ?" to arrayOf(
            "30",
            "56",
            "45",
            "22"
        ),
        "Force = mass * ?" to arrayOf(
            "acceleration",
            "velocity",
            "speed",
            "time"
        ),
        "What is a valid way to measure energy?" to arrayOf(
            "joules",
            "inches",
            "centimeters",
            "feet"
        ),
        "How many Iron Man movies are there? (Excluding Avengers movies)" to arrayOf(
            "3",
            "2",
            "1",
            "5"
        ),
        "How many members are in the Guardians of the Galaxy?" to arrayOf(
            "6",
            "4",
            "3",
            "2"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        var quizIntent = getIntent()

        val questions = quizIntent.getStringArrayExtra("questions")
        questionNum = quizIntent.getIntExtra("questionNum", 10)
        var numCorrectAns = quizIntent.getIntExtra("numCorrectAns", 10)

        val submitButton = findViewById<Button>(R.id.submit)
        submitButton.setEnabled(false)

        val currentQuestion = questions[questionNum]
        correctAns = answerKey.get(currentQuestion)!![0]


        getAnswers(questions)

        val buttonGroup = findViewById<RadioGroup>(R.id.answer_group)
        submitButton.setOnClickListener {
            val id = buttonGroup.checkedRadioButtonId
            val btn = buttonGroup.findViewById<RadioButton>(id)

            if (btn.text.equals(correctAns)) {
                numCorrectAns++
            }
            questionNum++
            val userAnswer = btn.text.toString()
            quizIntent = Intent(baseContext, Answers::class.java)
            quizIntent.putExtra("numCorrectAns", numCorrectAns)

            quizIntent.putExtra("questionNum", questionNum)
            quizIntent.putExtra("questions", questions)
            quizIntent.putExtra("userAnswer", userAnswer)
            quizIntent.putExtra("correctAnswer", correctAns)
            startActivity(quizIntent)
        }

    }

    private fun getAnswers(questions: Array<String>) {
        val question = questions[questionNum]
        val ans = answerKey.get(question)

        val currentTitle = findViewById<TextView>(R.id.question)
        currentTitle.text = question

        var options: Set<Int> = setOf(5)
        for (i in 0..3) {
            var position = 5
            while (options.contains(position)) {
                position = (0..3).random()
            }
            options = options.plus(position)
            val id = resources.getIdentifier("ans$position", "id", packageName)
            val btn = findViewById<RadioButton>(id)
            btn.text = ans!![i]
            btn.setOnClickListener {
                findViewById<Button>(R.id.submit).setEnabled(true)
            }
        }
    }
}