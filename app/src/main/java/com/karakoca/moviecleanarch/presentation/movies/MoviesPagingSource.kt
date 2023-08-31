package com.karakoca.moviecleanarch.presentation.movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.karakoca.moviecleanarch.data.remote.MovieApi
import com.karakoca.moviecleanarch.data.remote.dto.toMovie
import com.karakoca.moviecleanarch.domain.model.Movie


class MoviesPagingSource(private val api: MovieApi, private val search: String) :
    PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = api.getMovies(page = page, search = search)
            println(response.body())

            if (response.body()?.response == "True") {
                LoadResult.Page(
                    data = response.body()?.toMovie() ?: emptyList(),
                    prevKey = if (page == 1) null else page.minus(1),
                    nextKey = if (response.body()?.toMovie()
                            .isNullOrEmpty()
                    ) null else page.plus(1),
                )
            } else {
                LoadResult.Error(Throwable(message = "empty"))
            }
        } catch (e: Exception) {
            println(e.message.toString())
            LoadResult.Error(e)
        }
    }
}