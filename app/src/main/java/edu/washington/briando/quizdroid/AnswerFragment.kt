package edu.washington.briando.quizdroid

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.content.Intent
import android.util.Log

class AnswerFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        container!!.removeAllViews()
        return inflater.inflate(R.layout.fragment_answer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userAns = getView()!!.findViewById<TextView>(R.id.userAns)
        val correctAns = getView()!!.findViewById<TextView>(R.id.correctAns)
        val score = getView()!!.findViewById<TextView>(R.id.userCorrect)
        val button = getView()!!.findViewById<Button>(R.id.next)

        val questions = arguments!!.getStringArrayList("questions")
        val topic = arguments!!.getString("topic")
        val questionNum =  arguments!!.getInt("questionNum")
        val numCorrectAns =  arguments!!.getInt("numCorrectAns")
        val userAnswer =  arguments!!.getString("userAnswer")
        val rightAnswer = arguments!!.getString("correctAns")

        userAns.text = "You chose $userAnswer"
        correctAns.text = "The correct answer is $rightAnswer"
        val total = questions.size
        val scoreText = "You have $numCorrectAns out of $total correct"
        score.text = scoreText
        if (questionNum == questions.size - 1) {
            button.text = "Finish"
        }
        if (userAnswer.equals(rightAnswer)) {
            userAns.text = "You are correct!"
        }

        button.setOnClickListener {
            if (button.text == "Finish") {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            } else {
                val fragment = QuestionFragment()

                val bundle = Bundle()
                bundle.putString("topic", topic)
                bundle.putStringArrayList("questions", questions)
                bundle.putInt("questionNum", questionNum + 1)
                bundle.putInt("numCorrectAns", numCorrectAns)

                fragment.arguments = bundle

                val transaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.fragmentLayout, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }
}
