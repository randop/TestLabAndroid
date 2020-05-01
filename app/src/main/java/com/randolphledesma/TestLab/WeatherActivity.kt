package com.randolphledesma.TestLab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_weather.*

private val items = listOf(
    "Mon 5/4 - Sunny - 31/17",
    "Tue 5/5 - Foggy - 21/8",
    "Wed 5/6 - Cloudy - 22/17",
    "Thu 5/7 - Rainy - 18/11",
    "Fri 5/8 - Foggy - 21/10",
    "Sat 5/9 - Sunny - 23/18",
    "Sun 5/10 - Rainy - 20/7"
)

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        setTitle("${getString(R.string.app_name)} : Message")

        // Get the Intent that started this activity and extract the string
        //val message = intent.getStringExtra(EXTRA_MESSAGE)

        val forecastList = forecast_list as RecyclerView
        forecastList.layoutManager = LinearLayoutManager(this)
        forecastList.adapter = ForecastListAdapter(items)
    }
}

class ForecastListAdapter(val items: List<String>) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TextView(parent.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position]
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)
}