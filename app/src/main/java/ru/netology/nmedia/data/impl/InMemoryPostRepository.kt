package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {
    override val data = MutableLiveData<Post>(
        Post(
            id = 0L,
            author = "Нетология. Университет интернет-профессий",
            content = "Привет, это новая Нетология!Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов.Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb ",
            date = "21 мая в 18:36",
            likes = 999,
            shares = 19_999,
            views = 1_999_999
        )
    )

    override fun like() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }

        if (!currentPost.likedByMe) {
            val likedPost = currentPost.copy(
                likes = currentPost.likes + 1,
                likedByMe = true
            )
            data.value = likedPost
        } else {
            val unLikedPost = currentPost.copy(
                likes = currentPost.likes - 1,
                likedByMe = false
            )
            data.value = unLikedPost
        }
    }

    override fun share() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }

        val sharedPost = currentPost.copy(
            shares = currentPost.shares + 1
        )

        data.value = sharedPost
    }
}