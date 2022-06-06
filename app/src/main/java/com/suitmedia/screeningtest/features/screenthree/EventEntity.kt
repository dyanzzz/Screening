package com.suitmedia.screeningtest.features.screenthree

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventEntity(
    val title: String,
    val body: String,
    val date: String,
    val time: String,
    val image: String
): Parcelable