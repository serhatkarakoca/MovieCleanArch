package com.karakoca.moviecleanarch.presentation.movie_details.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.karakoca.moviecleanarch.R
import com.karakoca.moviecleanarch.presentation.movie_details.MovieDetailsViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        state.data?.let { movie ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberImagePainter(data = movie.poster),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RectangleShape)
                            .padding(24.dp),
                        contentScale = ContentScale.FillWidth
                    )
                }

                FlowRow(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = movie.title.toString(),
                        color = Color.White,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                    Text(
                        text = "(${movie.year.toString()})",
                        color = Color.White
                    )
                }
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = movie.runtime.toString(),
                        color = Color.White,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.round_star_24),
                        contentDescription = null
                    )
                    Text(text = movie.imdbRating.toString(), color = Color.Yellow)
                }

                Text(text = movie.genre.toString(), color = Color.LightGray)
                Text(
                    text = movie.plot.toString(),
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 24.dp)
                )
            }
        }
    }
}