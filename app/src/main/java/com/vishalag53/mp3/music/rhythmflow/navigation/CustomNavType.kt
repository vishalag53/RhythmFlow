package com.vishalag53.mp3.music.rhythmflow.navigation

import android.net.Uri
import android.os.Build
import android.os.Parcelable
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlin.reflect.KClass

class CustomNavType<T: Parcelable> (
    private val clazz: KClass<T>,
    private val serializer: KSerializer<T>
): NavType<T>(false) {
    override fun put(bundle: SavedState, key: String, value: T) {
        bundle.putParcelable(key, value)
    }

    override fun get(bundle: SavedState, key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, clazz.java)
        } else {
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): T {
        return Json.decodeFromString(serializer, value)
    }

    override fun serializeAsValue(value: T): String {
        return Uri.encode(Json.encodeToString(serializer, value))
    }
}