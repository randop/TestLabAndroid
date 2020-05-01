package com.randolphledesma.TestLab

import android.content.ContentResolver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit

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
        //forecastList.adapter = ForecastListAdapter(items)

        GlobalScope.launch(Dispatchers.Main) {
            val result = async(Dispatchers.IO) { RequestForecastCommand("94043").execute() }.await()
            forecastList.adapter = ForecastListAdapter(result)
        }
    }
}

class ForecastListAdapter(val weekForecast: ForecastList) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TextView(parent.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(weekForecast.dailyForecast[position]) {
            holder.textView.text = "$date - $description - $high/$low"
        }
    }

    override fun getItemCount(): Int = weekForecast.dailyForecast.size

    class ViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)
}

class ForecastRequest(val zipCode: String) {
    companion object {
        private val APP_ID = "439d4b804bc8187953eb36d2a8c26a02"
        private val URL = "https://samples.openweathermap.org/data/2.5/forecast/daily?APPID=$APP_ID&q="
    }

    fun execute(): ForecastResult {
        val forecastJsonStr = java.net.URL(URL + zipCode).readText()
        return Gson().fromJson(forecastJsonStr, ForecastResult::class.java)
    }
}

public interface Command<out T> {
    fun execute(): T
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
        return ModelForecast(convertDate(forecast.dt), forecast.weather[0].description, forecast.temp.max.toInt(), forecast.temp.min.toInt())
    }

    private fun convertDate(date: Long): String {
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return df.format(date)
    }
}

class RequestForecastCommand(val zipCode: String): Command<ForecastList> {
    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(forecastRequest.execute())
    }
}

data class ModelForecast(val date: String, val description: String, val high: Int, val low: Int)

data class ForecastList(val city: String, val country: String, val dailyForecast:List<ModelForecast>)

data class ForecastResult(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Forecast>,
    val message: Double
)

data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int
)

data class Forecast(
    val clouds: Int,
    val deg: Int,
    val dt: Long,
    val humidity: Int,
    val pressure: Double,
    val rain: Double,
    val snow: Double,
    val speed: Double,
    val temp: Temp,
    val weather: List<Weather>
)

data class Coord(
    val lat: Double,
    val lon: Double
)

data class Temp(
    val day: Double,
    val eve: Double,
    val max: Double,
    val min: Double,
    val morn: Double,
    val night: Double
)

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)