package com.karakoca.moviecleanarch.domain.use_case.movie_details_usecase

import com.google.common.truth.Truth.assertThat
import com.karakoca.moviecleanarch.data.repository.FakeMovieRepositoryTest
import com.karakoca.moviecleanarch.domain.model.MovieDetail
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class MovieDetailsUseCaseTest() {
    private lateinit var useCase: MovieDetailsUseCase
    private lateinit var fakeRepository: FakeMovieRepositoryTest
    private lateinit var movie: MovieDetail

    @Before
    fun setUp() {
        movie = MovieDetail(
            id = 4651,
            director = "Anna Boden, Ryan Fleck",
            genre = "Action",
            imdbRating = "6.8",
            imdbVotes = "591,962",
            plot = "Carol Danvers becomes one of the universe's most powerful heroes when Earth is caught in the middle of a galactic war between two alien races.",
            poster = "https://m.media-amazon.com/images/M/MV5BZjNiN2NhYzQtYmI1NC00NGRmLWE2MWYtNjAxNjMzZmYxNDJhXkEyXkFqcGdeQXVyODQ4MjU1MDk@._V1_SX300.jpg",
            released = "08 Mar 2019",
            title = "Marvel Studios: Assembling a Universe",
            type = "movie",
            runtime = "123 min",
            year = "2014",
            imdbID = "tt4154664"

        )
        fakeRepository = FakeMovieRepositoryTest()
        useCase = MovieDetailsUseCase(fakeRepository)
    }

    @Test
    fun `find existing movie`() {
        runBlocking {
            useCase.addMovie(movie)
            useCase.findMovieById("tt4154664").collectLatest {
                assertThat(it).isEqualTo(movie)
            }
        }
    }

    @Test
    fun `nonexistent movie returns null`() {
        runBlocking {
            useCase.findMovieById("tt4154664").collectLatest {
                assertThat(it).isEqualTo(null)
            }
        }
    }

    @Test
    fun `add movie correctly`() {
        runBlocking {
            useCase.addMovie(movie)
            useCase.getAllMovies().collect {
                assertThat(movie).isIn(it)
            }
        }
    }

    @Test
    fun `if movie nonexistent addOrRemoveMovie func add movie`() {
        runBlocking {
            useCase.addOrRemoveMovie(movie)
            useCase.getAllMovies().collect {
                assertThat(movie).isIn(it)
            }
        }
    }

    @Test
    fun `if movie exist addOrRemoveMovie func remove movie`() {
        runBlocking {
            useCase.addMovie(movie)
            useCase.addOrRemoveMovie(movie)
            useCase.getAllMovies().collect {
                assertThat(movie).isNotIn(it)
            }
        }
    }
}