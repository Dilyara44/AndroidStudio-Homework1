package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import ru.netology.nmedia.databinding.MainActivityBinding
import ru.netology.nmedia.viewModel.PostViewModel
import java.math.RoundingMode
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val binding = MainActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel.data.observe(this) { post ->
            binding.render(post)
        }

        binding.likes.setOnClickListener {
            viewModel.onLikeClicked()
        }

        binding.share.setOnClickListener {
            viewModel.onShareClicked()
        }
    }

    private fun MainActivityBinding.render(post: Post) {
        authorName.text = post.author
        text.text = post.content
        date.text = post.date
        likes.setImageResource(getLikeIcon(post.likedByMe))
        countLikes.text = getBeautifulDisplay(post.likes)
        countShares.text = getBeautifulDisplay(post.shares)
        countViews.text = getBeautifulDisplay(post.views)
    }

    @DrawableRes
    private fun getLikeIcon(liked: Boolean) =
        if (liked) R.drawable.ic_liked_24 else {
            R.drawable.ic_like_24dp
        }

    private fun getBeautifulDisplay(counts: Int): String {
        if (counts in 1000..1099) {
            val result = counts / 1000
            return "$result K"
        } else if (counts in 10000..999_999) {
            val count = counts / 1000.toFloat()
            val dec = DecimalFormat("#")
            dec.setRoundingMode(RoundingMode.DOWN)
            val result = dec.format(count)
            return "$result K"
        } else if (counts in 1100..9_999) {
            val count = counts / 1000.toFloat()
            val dec = DecimalFormat("#.#")
            dec.setRoundingMode(RoundingMode.DOWN)
            val result = dec.format(count)
            return "$result K"
        } else if (counts >= 1_000_000) {
            val count = counts / 1_000_000.toFloat()
            val dec = DecimalFormat("#.#")
            dec.setRoundingMode(RoundingMode.DOWN)
            val result = dec.format(count)
            return "$result M"
        }
        return "$counts"
    }
}