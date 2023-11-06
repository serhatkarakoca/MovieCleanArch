package com.karakoca.moviecleanarch.presentation.movie_details.view

import AlertDialogMovie
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.karakoca.moviecleanarch.R
import com.karakoca.moviecleanarch.presentation.movie_details.MovieDetailsViewModel
import com.karakoca.moviecleanarch.presentation.movies.MoviesEvent

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieDetailsScreen(
    navController: NavController,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val openAlertDialog = remember { mutableStateOf(true) }

    if (viewModel.showCustomTab.value) {
        ShowCustomTab(imdbId = viewModel.imdbId)
        viewModel.showCustomTab.value = false
    }

    BoxWithConstraints(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
    ) {
        val pageSize = constraints.minHeight

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            state.data?.let { movie ->
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((pageSize / 5).dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(movie.poster),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .padding(horizontal = 24.dp),
                            contentScale = ContentScale.Crop
                        )
                        IconButton(
                            onClick = { viewModel.handleEvent(MoviesEvent.AddOrRemoveMovie) },
                            Modifier
                                .align(Alignment.TopEnd)
                                .background(Color.White, CircleShape)
                        ) {
                            println(state.isFav)
                            if (state.isFav)
                                Image(
                                    painter = painterResource(id = R.drawable.ic_favorite),
                                    contentDescription = null
                                )
                            else
                                Image(
                                    painter = painterResource(id = R.drawable.ic_favorite_border),
                                    contentDescription = null
                                )
                        }
                    }

                    FlowRow(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalArrangement = Arrangement.Center
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

                    ElevatedButton(
                        onClick = {
                            viewModel.showCustomTab.value = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Imdb Page",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                }
            }

            if (state.loading) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .height((pageSize / 3).dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(color = Color.Red)
                }
            }

            if (state.error != null) {
                if (openAlertDialog.value) {
                    AlertDialogMovie(
                        onDismissRequest = {
                            openAlertDialog.value = false
                        },
                        onConfirmation = {
                            openAlertDialog.value = false
                            navController.popBackStack()
                        },
                        dialogTitle = "Error",
                        dialogText = state.error ?: "",
                        icon = Icons.Default.Info
                    )
                }
            }
        }
    }
}

@Composable
fun ShowCustomTab(imdbId: String) {
    val builder = CustomTabsIntent.Builder().apply {
        setUrlBarHidingEnabled(true)
        setDefaultColorSchemeParams(
            CustomTabColorSchemeParams.Builder()
                .setToolbarColor(ContextCompat.getColor(LocalContext.current, R.color.black))
                .build()
        )
        setShareState(CustomTabsIntent.SHARE_STATE_OFF)
    }
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(LocalContext.current, Uri.parse("https://imdb.com/title/$imdbId"))
}