package com.bilalberekgm.bluearchive.studentModel

data class Data(
    val _id: String = "",
    val affinity: List<Affinity> = listOf(),
    val age: String = "",
    val armorType: String = "",
    val background: String = "",
    val birthday: String = "",
    val damageType: String = "",
    val height: String = "",
    val hobbies: List<String> = listOf(),
    val image: String = "",
    val imageSchool: String = "",
    val imageSpecial: List<Any> = listOf(),
    val name: String = "",
    val names: Names = Names(),
    val photoUrl: String = "",
    val realeaseDate: String = "",
    val role: List<String> = listOf(),
    val school: String = "",
    val voice: String = "",
    val voices: String = "",
    val weapon: String = "",
    val weaponImage: String = "",
    val weaponUnique: String = ""
)