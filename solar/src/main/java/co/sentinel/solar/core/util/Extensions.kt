package co.sentinel.solar.core.util

import android.content.Context
import androidx.annotation.RawRes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Context.jsonToClass(@RawRes resourceId: Int): T? =
    Gson().runCatching {
        val resource = resources.openRawResource(resourceId).bufferedReader().use { it.readText() }
        fromJson<T>(
            resource,
            object : TypeToken<T>() {}.type
        )
    }.getOrNull()