package com.karakoca.moviecleanarch.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.karakoca.moviecleanarch.R
import com.karakoca.moviecleanarch.Screen
import com.karakoca.moviecleanarch.presentation.movie_details.view.MovieDetailsScreen
import com.karakoca.moviecleanarch.presentation.movies.view.MovieScreen
import com.karakoca.moviecleanarch.presentation.ui.theme.MovieCleanArchTheme
import com.karakoca.moviecleanarch.utils.Constant.IMDB_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieCleanArchTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry = navController.currentBackStackEntryAsState()
                    val isOnTop =
                        navBackStackEntry.value?.destination?.route == Screen.MovieScreen.route

                    Scaffold(
                        containerColor = Color.DarkGray,
                        topBar = {
                            TopAppBar(
                                title = {
                                    if (isOnTop) Text(
                                        text = LocalContext.current.getString(R.string.app_name),
                                        color = Color.White
                                    )
                                },
                                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Black),
                                navigationIcon = {
                                    if (!isOnTop)
                                        IconButton(onClick = {
                                            navController.popBackStack()
                                        }) {
                                            Icon(
                                                Icons.Default.ArrowBack,
                                                tint = Color.White,
                                                contentDescription = "back"
                                            )
                                        }
                                },
                                scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
                                    rememberTopAppBarState()
                                )
                            )
                        },
                        content = {
                            NavHost(
                                navController = navController,
                                startDestination = Screen.MovieScreen.route,
                                Modifier.padding(top = it.calculateTopPadding())
                            ) {

                                composable(route = Screen.MovieScreen.route) {
                                    MovieScreen(navController = navController)
                                }

                                composable(route = Screen.MovieDetailsScreen.route + "/{${IMDB_ID}}") {
                                    // val imdbId = remember {it.arguments?.getString(IMDB_ID) }
                                    MovieDetailsScreen(navController)
                                }
                            }
                        })

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieCleanArchTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Greeting("Android")
        }
    }
}