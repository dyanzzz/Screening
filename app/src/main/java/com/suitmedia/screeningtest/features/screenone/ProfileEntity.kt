package com.suitmedia.screeningtest.features.screenone

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileEntity(
    val name: String,
    val file_name: String,
    val file_path: String
): Parcelable