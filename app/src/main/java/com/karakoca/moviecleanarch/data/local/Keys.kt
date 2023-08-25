package com.karakoca.moviecleanarch.data.local

object Keys {
    init {
        System.loadLibrary("native-lib")
    }

    external fun apiKey(): String
}