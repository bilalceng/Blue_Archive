package com.bilalberekgm.bluearchive.paging


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bilalberekgm.bluearchive.domain.repository.BlueArchiveRepositoryRepository
import com.bilalberekgm.bluearchive.model.Data
import retrofit2.HttpException

class BlueArchivePagingSource(
    private var repository: BlueArchiveRepositoryRepository,
): PagingSource<Int,Data>(){

    override fun getRefreshKey(state: PagingState<Int,Data>): Int? {
        return null
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,Data> {

        return try {
            val currentPage = params.key ?: 1
            val characterPerPage = 5
            val response = repository.getAllCharacters(currentPage, characterPerPage)
            val data = response.body()?.data
            val responseData = mutableListOf<Data>()
            responseData.addAll(data!!)

            LoadResult.Page(
                data = responseData,
                prevKey = if(currentPage == 1) null else -1,
                nextKey = currentPage.inc()
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }catch (httpE: HttpException){
            LoadResult.Error(httpE)
        }
    }
}