package com.randolphledesma.testlab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.randolphledesma.testlab.util.Command
import com.squareup.picasso.Picasso
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class ForecastListAdapter(private val weekForecast: ForecastList, private val itemListener: (ModelForecast) -> Unit) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        view.layoutParams.height = parent.measuredHeight / 8
        return ViewHolder(view, itemListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(weekForecast.dailyForecast[position])
    }

    override fun getItemCount(): Int = weekForecast.dailyForecast.size

    class ViewHolder(view: View, val itemClick: (ModelForecast) -> Unit): RecyclerView.ViewHolder(view) {

        private val iconView = view.findViewById<ImageView>(R.id.icon)
        private val dateView = view.findViewById<TextView>(R.id.date)
        private val descriptionView = view.findViewById<TextView>(R.id.description)
        private val maxTemperatureView = view.findViewById<TextView>(R.id.maxTemperature)
        private val minTemperatureView = view.findViewById<TextView>(R.id.minTemperature)

        fun bind(forecast: ModelForecast) {
            with(forecast) {
                Picasso.with(itemView.context).load(iconUrl).into(iconView)
                dateView.text = date
                descriptionView.text = description
                maxTemperatureView.text = "$high"
                minTemperatureView.text = "$low"
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}

class ForecastRequest(val zipCode: String) {
    companion object {
        private const val APP_ID = "439d4b804bc8187953eb36d2a8c26a02"
        private const val URL = "https://samples.openweathermap.org/data/2.5/forecast/daily?APPID=$APP_ID&q="
    }

    fun execute(): ForecastResult {
        val forecastJsonStr = java.net.URL(URL + zipCode).readText()
        return Gson().fromJson(forecastJsonStr, ForecastResult::class.java)
    }
}

class ForecastDataMapper {
    fun convertFromDataModel(forecast: ForecastResult): ForecastList {
        return ForecastList(forecast.city.name, forecast.city.country, convertForecastListToDomain(forecast.list))
    }

    private fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> {
        return list.mapIndexed { index, forecast ->
            val dt = Calendar.getInstance().timeInMillis + TimeUnit.DAYS.toMillis(index.toLong())
            convertForecastItemToDomain(forecast.copy(dt = dt))
        }
    }

    private fun convertForecastItemToDomain(forecast: Forecast): ModelForecast {
        return ModelForecast(convertDate(forecast.dt), forecast.weather[0].description, forecast.temp.max.toInt(), forecast.temp.min.toInt(), generateIconUrl(forecast.weather[0].icon))
    }

    private fun convertDate(date: Long): String {
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return df.format(date)
    }

    private fun generateIconUrl(iconCode: String): String = "https://openweathermap.org/img/w/$iconCode.png"
}

class RequestForecastCommand(val zipCode: String):
    Command<ForecastList> {
    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(forecastRequest.execute())
    }
}