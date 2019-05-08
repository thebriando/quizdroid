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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        container!!.removeAllViews()
        return inflater!!.inflate(R.layout.fragment_topic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val overview = getView()!!.findViewById<TextView>(R.id.overview)
        val numQuestions = getView()!!.findViewById<TextView>(R.id.numQuestions)
        val startBtn = getView()!!.findViewById<Button>(R.id.start)

        val topic = arguments!!.getString("topic")
        val size = questionMap.get(topic)!!.size
        var questions = questionMap[topic]

        overview.text = topic
        numQuestions.text = "$size questions"

        startBtn.setOnClickListener {
            val fragment = QuestionFragment()
            val bundle = Bundle()

            bundle.putStringArray("questions", questions)
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
