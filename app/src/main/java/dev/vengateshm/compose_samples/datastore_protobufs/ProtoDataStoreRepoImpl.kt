package dev.vengateshm.compose_samples.datastore_protobufs

import androidx.datastore.core.DataStore
import dev.vengateshm.compose_samples.ThemeStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class ProtoDataStoreRepoImpl(private val protoDatastore: DataStore<ThemeStore>) :
    ProtoDataStoreRepo {
    override suspend fun isDarkTheme(): Flow<Boolean> {
        return protoDatastore.data
            .catch { e ->
                if (e is IOException) {
                    emit(ThemeStore.getDefaultInstance())
                } else {
                    throw e
                }
            }
            .map {
                it.isDark
            }
    }

    override suspend fun saveTheme(isDark: Boolean) {
        protoDatastore.updateData {
            it.toBuilder()
                .setIsDark(isDark)
                .build()
        }
    }
}