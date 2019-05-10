package edu.washington.briando.quizdroid

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.util.Log


class TopicFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        container!!.removeAllViews()
        return inflater!!.inflate(R.layout.fragment_topic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val quizApp = QuizApp.defaultRepo

        val overview = getView()!!.findViewById<TextView>(R.id.overview)
        val numQuestions = getView()!!.findViewById<TextView>(R.id.numQuestions)
        val startBtn = getView()!!.findViewById<Button>(R.id.start)

        val topic = arguments!!.getString("topic")
        val descr = getView()!!.findViewById<TextView>(R.id.longDescr)
        descr.text = quizApp.getTopicData(topic).longDescr

        val size = quizApp.getQuestions(topic).size
        var questions = quizApp.getQuestions(topic)
        val questionNames = quizApp.getQuestionNames(questions)

        overview.text = topic
        numQuestions.text = "$size questions"

        startBtn.setOnClickListener {
            val fragment = QuestionFragment()
            val bundle = Bundle()

            bundle.putStringArrayList("questions", questionNames)
            bundle.putString("topic", topic)
            bundle.putInt("questionNum", 0)
            fragment.arguments = bundle

            val transaction = fragmentManager!!.beginTransaction()

            transaction.replace(R.id.fragmentLayout, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}
