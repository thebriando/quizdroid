package edu.washington.briando.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Answers : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answers)

        var quizIntent: Intent = getIntent()

        val numCorrectAns = quizIntent.getIntExtra("numCorrectAns", 10)
        val questionNum = quizIntent.getIntExtra("questionNum", 10)
        val questions = quizIntent.getStringArrayExtra("questions")
        val answer = quizIntent.getStringExtra("userAnswer")
        val correct = quizIntent.getStringExtra("correctAnswer")

        var userCorrect = findViewById<TextView>(R.id.userCorrect)
        var userAns = findViewById<TextView>(R.id.userAns)
        var correctAns = findViewById<TextView>(R.id.correctAns)

        userCorrect.text = "You have " + numCorrectAns.toString() + " out of " + questions.size + " correct"
        userAns.text = "You chose $answer"
        correctAns.text = "The correct answer is $correct"

        if (answer.equals(correct)) {
            userAns.text = "You are correct!"
        }
        val nextBtn = findViewById<Button>(R.id.next)
        if (questionNum == questions.size) {
            nextBtn.text = "FINISH"
            nextBtn.setOnClickListener {
                val quizIntent = Intent(baseContext, MainActivity::class.java)
                startActivity(quizIntent)
            }
        } else {
            nextBtn.text = "NEXT"
            nextBtn.setOnClickListener {
                val quizIntent = Intent(baseContext, Questions::class.java)
                quizIntent.putExtra("questions", questions)
                quizIntent.putExtra("questionNum", questionNum)
                quizIntent.putExtra("numCorrectAns", numCorrectAns)
                startActivity(quizIntent)
            }
        }


    }
}