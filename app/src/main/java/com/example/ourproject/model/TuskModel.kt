package com.example.ourproject.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TuskModel(
    val id: Int,
    val title: String,
    val description: String,
    val is_Completed : Boolean
) : Parcelable