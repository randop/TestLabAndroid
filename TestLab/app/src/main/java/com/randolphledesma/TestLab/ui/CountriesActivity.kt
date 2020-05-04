package com.randolphledesma.TestLab

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_countries.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class CountriesActivity : AppCompatActivity() {
    private val allCountries = Locale.getAvailableLocales().map { it.displayCountry }.filter { it.trim().isNotBlank() }.distinct().sorted()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.countries_activity_title)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false)
        }

        val searchText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchText.setOnEditorActionListener{ view, actionId, event ->
            GlobalScope.launch(Dispatchers.Main) {
                doCountrySearch(view.text.toString())
            }
            /** hide keyboard **/
            val imm = applicationContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            searchText.clearFocus()
            true
        }
        return true
    }

    private fun handleIntent(intent: Intent) {
        var query = ""
        if (Intent.ACTION_SEARCH == intent.action) {
            query = intent.getStringExtra(SearchManager.QUERY)
        }

        GlobalScope.launch(Dispatchers.Main) {
            doCountrySearch(query)
        }
    }
    
    private suspend fun doCountrySearch(query: String) {
        val countryList = country_list as RecyclerView
        countryList.layoutManager = LinearLayoutManager(this)
        val countries = withContext(Dispatchers.Main) {
            allCountries.filter {
                if (query.isNotBlank()) {
                    it.toLowerCase().contains(query.toLowerCase())
                } else {
                    true
                }
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