package ru.netology.nmedia

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val date: String,
    val likes: Int = 0,
    val likedByMe: Boolean = false,
    val shares: Int = 0,
    val views: Int = 0
)
