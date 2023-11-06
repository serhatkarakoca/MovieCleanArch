package com.karakoca.moviecleanarch.presentation.saved

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.karakoca.moviecleanarch.domain.model.MovieDetail


@Composable
fun SavedMovieRow(item: MovieDetail, clickListener: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp)
                .clickable {
                    clickListener.invoke(item.imdbID ?: "")
                }
        ) {
            AsyncImage(
                model = item.poster,
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .clip(RectangleShape),
                alignment = Alignment.Center

            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = item.title ?: "",
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
                Text(text = item.year ?: "", color = Color.Yellow)
            }
        }
    }
}