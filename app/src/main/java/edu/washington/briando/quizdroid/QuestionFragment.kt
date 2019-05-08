package edu.washington.briando.quizdroid

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.*
import android.util.Log
import java.util.*

class QuestionFragment : Fragment() {
    private lateinit var questions: Array<String>
    private var questionNum = 0
    private var numCorrectAns = 0
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        container!!.removeAllViews()
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val submitButton = getView()!!.findViewById<Button>(R.id.submit)
        submitButton.setEnabled(false)

        questions = arguments!!.getStringArray("questions")
        questionNum = arguments!!.getInt("questionNum")
        numCorrectAns = arguments!!.getInt("numCorrectAns")
        val currentQuestion = questions[questionNum]
        correctAns = answerKey.get(currentQuestion)!![0]

        this.getAnswers(questions)

        val buttonGroup = getView()!!.findViewById<RadioGroup>(R.id.answer_group)
        submitButton.setOnClickListener {
            val fragment = AnswerFragment()
            val bundle = Bundle()

            val id = buttonGroup.checkedRadioButtonId
            val btn = buttonGroup.findViewById<RadioButton>(id)
            if (btn.text.equals(correctAns)) {
                numCorrectAns++
            }
            bundle.putString("correctAns", correctAns)
            val userAnswer = btn.text.toString()

            bundle.putStringArray("questions", questions)
            bundle.putInt("numCorrectAns", numCorrectAns)
            bundle.putInt("questionNum", questionNum)
            bundle.putString("userAnswer", userAnswer)
            fragment.arguments = bundle

            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.fragmentLayout, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun getAnswers(questions: Array<String>) {
        val question = questions[questionNum]

        val ans = answerKey.get(question)

        val currentTitle = getView()!!.findViewById<TextView>(R.id.question)
        currentTitle.text = question

        var options: Set<Int> = setOf(5)
        for (i in 0..3) {
            var position = 5
            while (options.contains(position)) {
                position = (0..3).random()
            }
            options = options.plus(position)
            val id = resources.getIdentifier("ans$position", "id", getActivity()!!.getPackageName())
            val btn = getView()!!.findViewById<RadioButton>(id)
            btn.text = ans!![i]
            btn.setOnClickListener {
                getView()!!.findViewById<Button>(R.id.submit).setEnabled(true)
            }
        }
    }
}
