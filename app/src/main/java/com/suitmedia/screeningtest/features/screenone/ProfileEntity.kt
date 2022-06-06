package com.suitmedia.screeningtest.features.screenone

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileEntity(
    val id: Int,
    val name: String,
    val first_name: String,
    val last_name: String,
    val email: String,
    val avatar: String,
    val file_name: String,
    val file_path: String
): Parcelable