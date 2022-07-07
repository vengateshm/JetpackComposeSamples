package dev.vengateshm.compose_samples.datastore_protobufs

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import dev.vengateshm.compose_samples.ThemeStore
import java.io.InputStream
import java.io.OutputStream

object ThemeStoreSerializer : Serializer<ThemeStore> {
    override val defaultValue: ThemeStore
        get() = ThemeStore.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ThemeStore {
        try {
            return ThemeStore.parseFrom(input)
        } catch (exp: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read protobuf", exp)
        }
    }

    override suspend fun writeTo(t: ThemeStore, output: OutputStream) {
        t.writeTo(output)
    }
}