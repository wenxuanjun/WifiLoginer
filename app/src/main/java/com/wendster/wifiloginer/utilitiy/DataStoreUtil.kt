package lantian.nolitter.utilitiy

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

val Context.dataStore by preferencesDataStore(name = "settings")

object DataStoreUtil {

    private lateinit var dataStore: DataStore<Preferences>
    fun initialize(context: Context) { dataStore = context.dataStore }

    fun <T> getPreference(key: String, defaultValue: T): T = runBlocking { asyncGetPreference(key, defaultValue).first() }
    fun <T> setPreference(key: String, value: T) { runBlocking { asyncSetPreference(key, value) } }

    @Suppress("UNCHECKED_CAST")
    private fun <T> asyncGetPreference(key: String, defaultValue: T): Flow<T> {
        return when (defaultValue) {
            is String -> dataStore.data.map { it[stringPreferencesKey(key)] ?: defaultValue }
            is Boolean -> dataStore.data.map { it[booleanPreferencesKey(key)] ?: defaultValue }
            else -> throw IllegalArgumentException("Wrong value provided with invalid value type")
        } as Flow<T>
    }

    private suspend fun <T> asyncSetPreference(key: String, value: T) {
        when (value) {
            is String -> dataStore.edit { it[stringPreferencesKey(key)] = value }
            is Boolean -> dataStore.edit { it[booleanPreferencesKey(key)] = value }
            else -> throw IllegalArgumentException("Wrong value provided with invalid value type")
        }
    }
}