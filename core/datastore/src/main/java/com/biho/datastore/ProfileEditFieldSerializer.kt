package com.biho.datastore

import androidx.datastore.core.Serializer
import com.biho.pixify.core.model.danbooru.model.profile.ProfileEditField
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object ProfileEditFieldSerializer : Serializer<ProfileEditField> {

    override val defaultValue: ProfileEditField
        get() = ProfileEditField()

    override suspend fun readFrom(input: InputStream): ProfileEditField {
        return try {
            Json.decodeFromString(
                deserializer = ProfileEditField.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }

    }

    override suspend fun writeTo(t: ProfileEditField, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = ProfileEditField.serializer(),
                value = t
            ).toByteArray()
        )
    }

}