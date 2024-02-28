package com.biho.pixify.core.model.danbooru.model.profile

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class ProfileEditField(
    val userId: Int = 0,
    val username: String = "",
    val apiKey: String = "",
    val enabledSafeMode: Boolean = true,
    val showDeletedPosts: Boolean = false,
    val defaultImageSize: PostScreenImageType = PostScreenImageType.Large,
    @Serializable(with = MyPersistentListSerializer::class)
    val blacklistedTags: PersistentList<String> = persistentListOf()
)

@OptIn(ExperimentalSerializationApi::class)
class MyPersistentListSerializer(
    private val serializer: KSerializer<String>,
) : KSerializer<PersistentList<String>> {

    private class PersistentListDescriptor :
        SerialDescriptor by serialDescriptor<List<String>>() {
        @ExperimentalSerializationApi
        override val serialName: String = "kotlinx.serialization.immutable.persistentList"
    }

    override val descriptor: SerialDescriptor = PersistentListDescriptor()

    override fun serialize(encoder: Encoder, value: PersistentList<String>) {
        return ListSerializer(serializer).serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): PersistentList<String> {
        return ListSerializer(serializer).deserialize(decoder).toPersistentList()
    }

}

