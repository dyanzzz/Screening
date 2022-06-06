package com.suitmedia.screeningtest.features.screenfour

import com.suitmedia.screeningtest.features.screenone.ProfileEntity

interface GuestCallback {
    fun onItemClicked(data: ProfileEntity)
}