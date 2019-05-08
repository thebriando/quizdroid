package edu.washington.briando.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class QuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val bundle = Bundle()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        val fragment = TopicFragment()
        val subject = intent.getStringExtra("topic")

        bundle.putString("topic", subject)
        fragment.arguments = bundle

        transaction.replace(R.id.fragmentLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
