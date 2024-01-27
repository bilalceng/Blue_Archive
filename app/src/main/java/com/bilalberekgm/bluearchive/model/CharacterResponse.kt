package com.bilalberekgm.bluearchive.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable


data class CharacterResponse(
    val data: List<Data> = listOf(),
    val dataAllPage: Int = 0,
    val message: String = ""

)

data class Data(
    val _id: String = "",
    val birthday: String = "",
    val damageType: String = "",
    val image: String = "",
    val imageSchool: String = "",
    val name: String = "",
    val photoUrl: String = "",
    val school: String = ""
):Serializable