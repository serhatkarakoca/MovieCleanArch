package com.karakoca.moviecleanarch.presentation.movies.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.karakoca.moviecleanarch.Screen
import com.karakoca.moviecleanarch.presentation.movies.MoviesEvent
import com.karakoca.moviecleanarch.presentation.movies.MoviesViewModel
import com.karakoca.moviecleanarch.utils.Constant.DEFAULT_SEARCH


@Composable
fun MovieScreen(
    navController: NavController,
    viewModel: MoviesViewModel = hiltViewModel()
) {
    val movies = viewModel.moviesState.collectAsLazyPagingItems()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column {
            val hint =
                if (viewModel.query.value != DEFAULT_SEARCH) viewModel.query.value else "Search..."
            SearchBar(modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp), hint = hint, onSearch = {

                viewModel.query.value = it.ifEmpty { DEFAULT_SEARCH }
                viewModel.onEvent(MoviesEvent.Search)
            })

            LazyColumn {
                items(count = movies.itemCount) { index ->
                    movies[index]?.let { item ->
                        MovieRow(item = item, clickListener = {
                            navController.navigate(Screen.MovieDetailsScreen.route + "/$it")
                        })
                    }
                }

                when (val currentState = movies.loadState.append) {
                    LoadState.Loading -> {
                        item {
                            Column(
                                Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                    }

                    is LoadState.Error -> {
                        val error =
                            if (currentState.error.message == "empty") "Arama sonucuna ulaşılamadı" else currentState.error.message
                        item {
                            Text(
                                text = error.toString(),
                                modifier = Modifier.fillMaxSize(),
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                        }
                    }

                    else -> {}
                }

                when (val currentState = movies.loadState.refresh) {
                    LoadState.Loading -> {
                        item {
                            Column(
                                Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                    }

                    is LoadState.Error -> {
                        val error =
                            if (currentState.error.message == "empty") "Arama sonucuna ulaşılamadı" else currentState.error.message
                        item {
                            Text(
                                text = error.toString(),
                                modifier = Modifier.fillMaxSize(),
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                        }
                    }

                    else -> {

                    }

                }
            }
        }

    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch.invoke(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)


        )
        if (text.isEmpty()) {
            Text(
                text = hint, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp), color = Color.LightGray
            )
        }
    }

}


