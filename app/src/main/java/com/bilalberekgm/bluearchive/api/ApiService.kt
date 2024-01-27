package com.bilalberekgm.bluearchive.api


import com.bilalberekgm.bluearchive.model.CharacterResponse
import com.bilalberekgm.bluearchive.studentModel.StudentResponse

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("/api/characters")
    suspend fun getAllCharacters(@Query("page") page: Int, @Query("perPage") perPage: Int): Response<CharacterResponse>

    @GET("/api/characters/students")
    suspend fun getStudents():Response<StudentResponse>
}

