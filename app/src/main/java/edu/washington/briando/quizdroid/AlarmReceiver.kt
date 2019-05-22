package edu.washington.briando.quizdroid

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter
import java.net.HttpURLConnection
import java.net.URL


class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
//        context?.deleteFile("questions.json")
        Log.d("QuizDroid", "in receiver")
        val url = intent?.getStringExtra("jsonUrl")
        downloadFile().execute(url)
    }

    inner class downloadFile : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg url: String?): String {
            val read: String
            val cnxn = URL(url[0]).openConnection() as HttpURLConnection


            try {
                cnxn.connect()
                read = cnxn.inputStream.use { it.reader().use { reader -> reader.readText() } }
            } finally {
                cnxn.disconnect()
            }
            return read
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            writeFile(result)
        }
    }

    private fun writeFile(jsonString: String?) {
        try {
            val directory = Environment.getExternalStorageDirectory()
            if (!directory.exists()) {
                directory.mkdirs()
            }
            val file = File(directory, "questions.json")

            val printWriter = PrintWriter(FileWriter(file, true))
            printWriter.println(jsonString)
            printWriter.close()
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        }
    }
}