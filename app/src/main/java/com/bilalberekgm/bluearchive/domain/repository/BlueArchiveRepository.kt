package com.bilalberekgm.bluearchive.domain.repository



import android.util.Log
import com.bilalberekgm.bluearchive.api.ApiService
import com.bilalberekgm.bluearchive.model.CharacterResponse
import com.bilalberekgm.bluearchive.studentModel.StudentResponse
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Response
import javax.inject.Inject

@ViewModelScoped
class BlueArchiveRepositoryRepository @Inject constructor(
    private val apiService:ApiService
) {

    suspend fun getAllCharacters(page: Int, perPage: Int): Response<CharacterResponse> {
        return apiService.getAllCharacters(page, perPage)
    }

    suspend fun getStudents(): Response<StudentResponse>{
        return apiService.getStudents()
    }
}