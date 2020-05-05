package com.randolphledesma.testlab.ui

import android.os.Build
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.randolphledesma.testlab.R

class CountryAdapter(val items: List<String>, val itemClick: (String) -> Unit) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = TextView(parent.context)
        if (Build.VERSION.SDK_INT < 23) {
            view.setTextAppearance(parent.context, R.style.TextAppearance_AppCompat_Medium)
        } else {
            view.setTextAppearance(R.style.TextAppearance_AppCompat_Medium)
        }
        view.width = parent.measuredWidth
        view.setPadding(8,12,8,12)
        view.isClickable = true
        view.isFocusable = true
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