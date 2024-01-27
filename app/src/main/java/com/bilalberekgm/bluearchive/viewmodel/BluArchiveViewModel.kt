package com.bilalberekgm.bluearchive.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.bilalberekgm.bluearchive.domain.repository.BlueArchiveRepositoryRepository
import com.bilalberekgm.bluearchive.model.CharacterResponse
import com.bilalberekgm.bluearchive.model.Data
import com.bilalberekgm.bluearchive.paging.BlueArchivePagingSource
import com.bilalberekgm.bluearchive.studentModel.StudentResponse
import com.bilalberekgm.bluearchive.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class BlueArchiveViewModel @Inject constructor(
    private val repository: BlueArchiveRepositoryRepository
): ViewModel() {
    val loading = MutableLiveData<Boolean>()

    private var _liveData: MutableLiveData<Resource<StudentResponse>> = MutableLiveData()
    val liveData: LiveData<Resource<StudentResponse>> = _liveData

    val characterList = Pager(PagingConfig(1)){
        BlueArchivePagingSource(repository)
    }.flow.cachedIn(viewModelScope)


    fun fetchStudents(){
        viewModelScope.launch {
            _liveData.postValue(Resource.Loading())
            val response = repository.getStudents()
            _liveData.postValue(handleStudentsResponse(response))

           }
        }


    private fun handleStudentsResponse(response: Response<StudentResponse>): Resource<StudentResponse> {
        if(response.isSuccessful){
            response.body()?.let{ result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())

    }

    }


