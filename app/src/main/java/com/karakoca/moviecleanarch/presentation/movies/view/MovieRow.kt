package com.karakoca.moviecleanarch.presentation.movies.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.karakoca.moviecleanarch.domain.model.Movie
import com.karakoca.moviecleanarch.presentation.ui.theme.MovieCleanArchTheme


@Composable
fun MovieRow(item: Movie, clickListener: (String) -> Unit) {
    Box {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .clickable {
                    clickListener.invoke(item.imdbID)
                },
        ) {
            Image(
                painter = rememberImagePainter(item.poster),
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp)
                    .clip(RectangleShape),

                )

            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(
                    text = item.title,
                    color = Color.White
                )
                Text(text = item.year, color = Color.Yellow)
            }

        }
    }
}

@Preview
@Composable
fun MovieRowPreview() {
    MovieCleanArchTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            MovieRow(
                Movie(
                    imdbID = "gloriatur",
                    poster = "legimus",
                    title = "Eternal Sunshine of the spotless mind",
                    type = "turpis",
                    year = "2017"
                ), clickListener = {}
            )
        }
    }
}
