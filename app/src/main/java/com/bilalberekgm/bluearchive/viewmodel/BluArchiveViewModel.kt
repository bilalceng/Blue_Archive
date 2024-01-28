package com.bilalberekgm.bluearchive.viewmodel


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
            _liveData.postValue(Resource.Loading())
            val response = repository.getStudents()
            _liveData.postValue(handleStudentsResponse(response))

        }
    }

    private fun handleStudentsResponse(response: Response<StudentResponse>): Resource<StudentResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())

    }

}


