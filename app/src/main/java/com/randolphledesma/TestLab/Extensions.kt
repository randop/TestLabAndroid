package com.randolphledesma.TestLab

import android.app.Notification
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.widget.Toast

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

public interface Command<out T> {
    fun execute(): T
}

inline fun supportsLollipop(code: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        code()
    }
}

inline fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}