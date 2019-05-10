package edu.washington.briando.quizdroid

import android.util.Log
import android.app.Application

private const val TAG: String = "QuizDroid"

data class Topic(val title: String, val shortDescr: String, val longDescr: String, val questions: Array<Question>)
data class Question(val question: String, val answers: Array<String>, val correctAns: Int)

interface TopicRepository {
    fun fillData()
    fun getTopicRepo(): ArrayList<Topic>
    fun getTopics(): ArrayList<String>
    fun getTopic(index: Int): Topic
    fun getQuestions(topic: String): Array<Question>
    fun getQuestionNames(questions: Array<Question>): ArrayList<String>
    fun getShortDescr(): ArrayList<String>
}

class DefaultRepository : TopicRepository {
    private val repository = arrayListOf<Topic>()

    override fun fillData() {
        val question1 = Question("3 + 5 = ?", arrayOf("8", "2", "3", "4"), 0)
        val question2 = Question("5 * 6 = ?", arrayOf("30", "56", "45", "22"), 0)
        val question3 = Question(
            "Force = mass * ?", arrayOf(
                "acceleration",
                "velocity",
                "speed",
                "time"
            ), 0
        )
        val question4 = Question(
            "What is a valid way to measure energy?", arrayOf(
                "joules",
                "inches",
                "centimeters",
                "feet"
            ), 0
        )
        val question5 = Question(
            "How many Iron Man movies are there? (Excluding Avengers movies)", arrayOf(
                "3",
                "2",
                "1",
                "5"
            ), 0
        )
        val question6 = Question(
            "How many members are in the Guardians of the Galaxy?", arrayOf(
                "6",
                "4",
                "3",
                "2"
            ), 0
        )

        val topic1 = Topic("math", "Math questions", "Simple math questions to test you on", arrayOf(question1, question2))
        val topic2 = Topic("physics", "Physics questions", "Simple math questions to test you on", arrayOf(question3, question4))
        val topic3 = Topic("marvelSuperHeroes", "Marvel superhero questions", "Simple questions about the MCU", arrayOf(question5, question6))

        repository.add(topic1)
        repository.add(topic2)
        repository.add(topic3)
    }

    override fun getTopicRepo(): ArrayList<Topic> {
        return repository
    }

    override fun getShortDescr(): ArrayList<String> {
        var descriptions = arrayListOf<String>()
        for (topic in repository) {
            descriptions.add(topic.shortDescr)
        }
        return descriptions
    }

    override fun getTopics(): ArrayList<String> {
        var topics = arrayListOf<String>()
        for (topic in repository) {
            topics.add(topic.title)
        }
        return topics
    }

    override fun getTopic(index: Int): Topic {
        return repository[index]
    }

    override fun getQuestions(topic: String): Array<Question> {
        for (currentTopic in repository) {
            if (currentTopic.title.equals(topic)) {
                return currentTopic.questions
            }
        }
        throw NoSuchElementException()
    }

    override fun getQuestionNames(questions: Array<Question>): ArrayList<String> {
        var questionNames = arrayListOf<String>()
        for (q in questions) {
            questionNames.add(q.question)
        }
        return questionNames
    }

    fun getTopicData(topic: String): Topic {
        for (i in repository) {
            if (i.title == topic) {
                return i
            }
        }
        throw NoSuchElementException()
    }
}

class QuizApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "QuizApp loaded")
    }

    companion object {
        val defaultRepo = DefaultRepository()
        init {
            defaultRepo.fillData()
        }
    }
}

