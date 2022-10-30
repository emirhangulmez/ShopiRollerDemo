package com.emirhan.shopirollerdemo.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Icon(
    val n: String,
    val t: String
) : Parcelable