package com.randolphledesma.TestLab

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_countries.*
import java.util.*

class CountriesActivity : AppCompatActivity(), ToolbarManager {
    override val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries)
        val countries = Locale.getAvailableLocales().map { it.displayCountry }.filter { it.trim().length > 0 }.distinct().sorted()
        val countryList = country_list as RecyclerView
        countryList.layoutManager = LinearLayoutManager(this)
        countryList.adapter = CountryListAdapter(countries) {
            applicationContext.toast(it)
        }

        initToolbar()
        enableHomeAsUp { onBackPressed() }
        attachToScroll(countryList)
    }

    fun enableHomeAsUp(up: () -> Unit) {
        toolbar.navigationIcon = createUpDrawable()
        toolbar.setNavigationOnClickListener { up() }
    }

    private fun createUpDrawable() = with(DrawerArrowDrawable(toolbar.context)) {
        progress = 1f
        this
    }

    fun initToolbar() {

    }

    fun attachToScroll(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) toolbar.slideExit() else toolbar.slideEnter()
            }
        })
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