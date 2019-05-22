package edu.washington.briando.quizdroid

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.util.Log
import android.view.*
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager




class MainActivity : AppCompatActivity() {
    private lateinit var itemList: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val quizApp = QuizApp.defaultRepo
        QuizApp.initSetup()
        Log.d("QuizDroid", "In Main")
        val topicListView = findViewById<ListView>(R.id.topicList)


        val topics = quizApp.getTopics()
        if (::itemList.isInitialized) {
            itemList.clear()
        }
        itemList = ArrayAdapter(this@MainActivity, android.R.layout.simple_expandable_list_item_1, topics)
        topicListView.adapter = itemList

        if (!connectedToInternet()) {
            Toast.makeText(this, "Not connected to internet", Toast.LENGTH_LONG).show()
        }
        topicListView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val intent = Intent(this@MainActivity, QuizActivity::class.java)
                intent.putExtra("topic", topicListView.getItemAtPosition(position).toString())
                startActivity(intent)
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)
        val intent = Intent(this@MainActivity, PreferencesActivity::class.java)
        startActivity(intent)
        return true
    }

    private fun connectedToInternet(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
