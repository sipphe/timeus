package com.timeus

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.Duration
import java.time.LocalDateTime
import java.time.Month


class MainActivity : AppCompatActivity() {
    var textYears: TextView? = null
    var textMonths: TextView? = null
    var textDays: TextView? = null
    var textHours: TextView? = null
    var textMinutes: TextView? = null
    var textSeconds: TextView? = null
    var duration: LocalDateTime? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textYears = findViewById(R.id.textYears)
        textMonths = findViewById(R.id.textMonths)
        textDays = findViewById(R.id.textDays)
        textHours = findViewById(R.id.textHours)
        textMinutes = findViewById(R.id.textMinutes)
        textSeconds = findViewById(R.id.textSeconds)

        setTexts()
        val handler = Handler()
        object : Runnable {
            override fun run() {

                setTexts()
                handler.postDelayed(this, 1000)
            }
        }
    }

    private fun setTexts() {
        duration = getCounterFromDate(LocalDateTime.of(2019, Month.SEPTEMBER, 28, 20, 0, 0, 0))

        val years = duration!!.year.toString()
        if (years == "0") {
            textYears!!.visibility = View.GONE
        } else {
            textYears!!.visibility = View.VISIBLE
            textYears?.text = years.plus(" years")
        }
        val months = duration!!.monthValue.toString()
        textMonths?.text = months.plus(" months")
        val days = duration!!.dayOfMonth.toString()
        textDays?.text = days.plus(" days")
        val hours = duration!!.hour.toString()
        textHours?.text = hours.plus(" hours")
        val minutes = duration!!.minute.toString()
        textMinutes?.text = minutes.plus(" minutes")
        var seconds = duration!!.second.toString()
        textSeconds?.text = seconds.plus(" seconds")
    }

    fun getCounterFromDate(startDate: LocalDateTime): LocalDateTime {
        var dateNow: LocalDateTime = LocalDateTime.now()

        var duration = Duration.between(startDate, dateNow)
        var allDays = duration.toDays()
        var year = (allDays / 365).toInt()
        var yearDays = duration.toDays() - (year * 365)
        var months = (yearDays / 30.4167).toInt()
        var monthDays = (yearDays - (months * 30.4167)).toInt()
        var allHours = duration.toHours()
        var hours = allHours - (allDays * 24)
        var allMinutes = duration.toMinutes()
        var minutes = allMinutes - (allHours * 60)
        var seconds = duration.seconds - (allMinutes * 60)

        return LocalDateTime.of(
            year,
            months,
            monthDays,
            hours.toInt(),
            minutes.toInt(),
            seconds.toInt()
        )
    }


}
