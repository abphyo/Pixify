package com.biho.pixify.core.host.danbooru.dtos.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileSettingsField(
    @SerialName("user")
    val data: ProfileSettingsFieldData
)