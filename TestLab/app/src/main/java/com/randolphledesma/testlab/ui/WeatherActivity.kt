package com.randolphledesma.testlab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.randolphledesma.testlab.util.toast
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.coroutines.*

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        title = getString(R.string.weather_activity_title)
        val forecastList = forecast_list as RecyclerView
        forecastList.layoutManager = LinearLayoutManager(this)

        GlobalScope.async(Dispatchers.Main) {
            try {
                val deferred = async(Dispatchers.IO) {RequestForecastCommand("94043").execute() }
                val result = deferred.await()
                forecastList.adapter = ForecastListAdapter(result) {
                    applicationContext.toast("Weather on ${it.date} will be ${it.description}")
                }
            } catch (error: Throwable) {
                //void
            }
        }
    }
}