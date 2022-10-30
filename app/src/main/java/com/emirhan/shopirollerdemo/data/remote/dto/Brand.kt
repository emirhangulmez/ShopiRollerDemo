package com.emirhan.shopirollerdemo.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Brand(
    val createDate: String,
    val icon: Icon,
    val id: String,
    val isActive: Boolean,
    val name: String,
    val updateDate: String
) : Parcelable