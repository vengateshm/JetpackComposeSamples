package dev.vengateshm.compose_samples.datastore_protobufs

import kotlinx.coroutines.flow.Flow

interface ProtoDataStoreRepo {
    suspend fun isDarkTheme(): Flow<Boolean>
    suspend fun saveTheme(isDark: Boolean)
}