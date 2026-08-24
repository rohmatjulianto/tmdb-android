package com.rohmat.tmdb_android.domain.model

data class Review(
    val id: String,
    val author: String,
    val content: String,
    val avatarUrl: String?
)
