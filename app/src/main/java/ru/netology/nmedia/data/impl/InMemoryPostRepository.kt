package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {

    private val posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    override val data = MutableLiveData(
        List(100) { index ->
            Post(
                id = index + 1L,
                author = "Нетология. Университет интернет-профессий",
                content = "Привет, это новая Нетология!Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов.Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb ",
                date = "21 мая в 18:36",
                likes = 999,
                shares = 19_999,
                views = 1_999_999
            )
        }
    )

    override fun likeById(postId: Long) {
        data.value = posts.map {
            if (it.id == postId && !it.likedByMe) {
                it.copy(likes = it.likes + 1, likedByMe = true)
            } else if (it.id == postId && it.likedByMe) {
                it.copy(likes = it.likes - 1, likedByMe = false)
            } else {
                it
            }
        }
    }

    override fun shareById(postId: Long) {
        data.value = posts.map {
            if (it.id == postId) {
                it.copy(
                    shares = it.shares + 1
                )
            } else {
                it
            }
        }
    }
}
