package com.example.mobilliumcase.ui.list.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mobilliumcase.model.MoviesDataModel

private const val PAGE_START_INDEX = 1

class MovieListPagingSource(val useCase: MovieListUseCase): PagingSource<Int, MoviesDataModel>() {

    override fun getRefreshKey(state: PagingState<Int, MoviesDataModel>): Int? {
        return state.anchorPosition
    }


    private fun toLoadResult(data: List<MoviesDataModel>, position: Int): LoadResult<Int, MoviesDataModel> {
        return LoadResult.Page(
            data = data,
            prevKey = if (position == PAGE_START_INDEX) null else position - 1,
            nextKey = if (data.isEmpty()) null else position + 1
        )
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesDataModel> {
        val position = params.key ?: PAGE_START_INDEX
        return try {
            val useCaseResponse = useCase.getUpComingMovies(position, params.loadSize)
            if(useCaseResponse.isNotEmpty()) {
                toLoadResult(useCaseResponse,position)
            }else{
                LoadResult.Error(Throwable("Unexpected Error"))
            }
        }catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}