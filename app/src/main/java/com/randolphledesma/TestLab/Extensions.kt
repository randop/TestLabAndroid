package com.randolphledesma.TestLab

import android.app.Notification
import android.content.Context
import android.content.SharedPreferences

inline fun SharedPreferences.edit(func: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.func()
    editor.apply()
}

fun SharedPreferences.Editor.set(pair: Pair<String, String>) = putString(pair.first, pair.second)

inline fun notification(context: Context, func: Notification.Builder.() -> Unit): Notification {
    val builder = Notification.Builder(context)
    builder.func()
    return builder.build()
}