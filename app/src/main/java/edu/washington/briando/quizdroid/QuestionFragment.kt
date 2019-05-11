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
    private lateinit var questions: ArrayList<String>
    private var questionNum = 0
    private var numCorrectAns = 0
    private var correctAns = ""
    private lateinit var answerKey: Array<Question>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        container!!.removeAllViews()
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val submitButton = getView()!!.findViewById<Button>(R.id.submit)
        val topic = arguments!!.getString("topic")
        answerKey = QuizApp.defaultRepo.getQuestions(topic)
        submitButton.setEnabled(false)

        questions = arguments!!.getStringArrayList("questions")
        questionNum = arguments!!.getInt("questionNum")
        numCorrectAns = arguments!!.getInt("numCorrectAns")
        val currentQuestion = questions[questionNum]
        correctAns = (answerKey[questionNum].answers)[QuizApp.defaultRepo.getCorrectAns(topic, questionNum)]

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
            bundle.putString("topic", topic)
            val userAnswer = btn.text.toString()

            bundle.putStringArrayList("questions", questions)
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

    private fun getAnswers(questions: ArrayList<String>) {
        val question = questions[questionNum]

        answerKey = QuizApp.defaultRepo.getQuestions(arguments!!.getString("topic"))

//        val ans = answerKey.get(question)
        val ans = (answerKey[questionNum].answers)

        val currentTitle = getView()!!.findViewById<TextView>(R.id.question)
        currentTitle.text = question

        for (i in 0..3) {
            val id = resources.getIdentifier("ans$i", "id", getActivity()!!.getPackageName())
            val btn = getView()!!.findViewById<RadioButton>(id)
            btn.text = ans!![i]
            btn.setOnClickListener {
                getView()!!.findViewById<Button>(R.id.submit).setEnabled(true)
            }
        }
    }
}
