package com.randolphledesma.TestLab

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_countries.*
import java.util.*

class CountriesActivity : AppCompatActivity() {
    private val allCountries = Locale.getAvailableLocales().map { it.displayCountry }.filter { it.trim().isNotBlank() }.distinct().sorted()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false)
        }
        val searchText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)

        searchText.setOnEditorActionListener{ v, actionId, event ->
            println("onSearch")
            searchView.onq
            true
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun handleIntent(intent: Intent) {
        val countryList = country_list as RecyclerView
        countryList.layoutManager = LinearLayoutManager(this)
        var query = ""
        if (Intent.ACTION_SEARCH == intent.action) {
            query = intent.getStringExtra(SearchManager.QUERY)
        }

        val countries = allCountries.filter {
            if (query.isNotBlank()) {
                it.toLowerCase().contains(query.toLowerCase())
            } else {
                true
            }
        }

        countryList.adapter = CountryListAdapter(countries) {
            applicationContext.toast(it)
        }
    }
}

class CountryListAdapter(val items: List<String>, val itemClick: (String) -> Unit) : RecyclerView.Adapter<CountryListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = TextView(parent.context)
        if (Build.VERSION.SDK_INT < 23) {
            view.setTextAppearance(parent.context, R.style.TextAppearance_AppCompat_Medium)
        } else {
            view.setTextAppearance(R.style.TextAppearance_AppCompat_Medium)
        }
        view.width = parent.measuredWidth
        view.setPadding(8,12,8,12)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val textView: TextView, val itemClick: (String) -> Unit): RecyclerView.ViewHolder(textView) {
        fun bind(item: String) {
            textView.text = item
            itemView.setOnClickListener { itemClick(item) }
        }
    }
}