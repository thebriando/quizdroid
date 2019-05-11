package edu.washington.briando.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.util.Log
import android.view.*


class MainActivity : AppCompatActivity() {
    private lateinit var topicList: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val quizApp = QuizApp.defaultRepo
//        var topicArray = quizApp.getTopics()

        this.topicList = findViewById(R.id.topicList)

        val topics = quizApp.getTopics()
        val itemList : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, topics)
        this.topicList.adapter = itemList

        this.topicList.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val intent = Intent(this@MainActivity, QuizActivity::class.java)
                intent.putExtra("topic", topicList.getItemAtPosition(position).toString())
                startActivity(intent)
            }
        }

//        for (i in topicArray) {
//            var id = resources.getIdentifier(i, "id", packageName)
//            var btn = findViewById<Button>(id)
//            val descrId = resources.getIdentifier(i + "Descr", "id", packageName)
//            var descr = findViewById<TextView>(descrId)
//            descr.text = quizApp.getTopicData(i).shortDescr
//            btn.setOnClickListener {
//                val intent = Intent(this, QuizActivity::class.java)
//                intent.putExtra("topic", i)
//                startActivity(intent)
//            }
//        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val intent = Intent(this@MainActivity, PreferencesActivity::class.java)
        startActivity(intent)
        return true
    }
}
