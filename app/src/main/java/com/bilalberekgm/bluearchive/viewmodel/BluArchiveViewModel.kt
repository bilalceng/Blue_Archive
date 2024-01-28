package com.bilalberekgm.bluearchive.viewmodel


import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.liveData
import com.bilalberekgm.bluearchive.domain.repository.BlueArchiveRepositoryRepository
import com.bilalberekgm.bluearchive.model.Data
import com.bilalberekgm.bluearchive.paging.BlueArchivePagingSource
import com.bilalberekgm.bluearchive.studentModel.StudentResponse
import com.bilalberekgm.bluearchive.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class BlueArchiveViewModel @Inject constructor(
    private val repository: BlueArchiveRepositoryRepository
): ViewModel() {

    private var _liveData: MutableLiveData<Resource<StudentResponse>> = MutableLiveData()
    val liveData: LiveData<Resource<StudentResponse>> = _liveData
    val filteredCharacterList = MutableLiveData<PagingData<Data>>()

    private val _characterList = Pager(PagingConfig(1)) {
        BlueArchivePagingSource(repository)
    }.liveData
        .cachedIn(viewModelScope)

    init {
        _characterList.observeForever {
            filteredCharacterList.value = it
        }
    }
    fun onQueryChanged(query: String) {
        _characterList.value?.let { pagingData ->
            viewModelScope.launch {
                filteredCharacterList.value = pagingData.filter { it.name.lowercase().contains(query.lowercase()) }
            }
        }
    }
    fun fetchStudents() {
        viewModelScope.launch {
            try {
                val response = repository.getStudents()
                _liveData.postValue(handleStudentsResponse(response))
            } catch (e: Exception) {
                _liveData.postValue(Resource.Error(e.message ?: "An error occurred"))
            }

        }
    }

    private fun handleStudentsResponse(response: Response<StudentResponse>): Resource<StudentResponse> {
        try {
            if (response.isSuccessful) {
                response.body()?.let { result ->
                    return Resource.Success(result)
                }
            }
            return Resource.Error("Response unsuccessful or body is null")
        } catch (e: Exception) {
            return Resource.Error("An error occurred: ${e.message}")
        }
    }
    }




