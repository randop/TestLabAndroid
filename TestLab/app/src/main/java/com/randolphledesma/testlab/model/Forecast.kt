package com.randolphledesma.testlab

data class ModelForecast(val date: String, val description: String, val high: Int, val low: Int, val iconUrl: String)

data class ForecastList(val city: String, val country: String, val dailyForecast:List<ModelForecast>) {
    val size: Int
        get() = dailyForecast.size

    operator fun get(position: Int): ModelForecast = dailyForecast[position]
}

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