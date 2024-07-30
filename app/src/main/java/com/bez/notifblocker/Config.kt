package com.bez.notifblocker

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Config(
    @SerializedName("api_endpoint") val apiEndpoint: String,
    @SerializedName("secret_key") val secretKey: String,
    @SerializedName("iv_parameter") val ivParameter: String,
    @SerializedName("algorithm") val algorithm: String
) : Parcelable
