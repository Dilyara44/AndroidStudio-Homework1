package ru.netology.nmedia.data.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding
import java.math.RoundingMode
import java.text.DecimalFormat

typealias onPostLikeShareClicked = (Post) -> Unit

internal class PostsAdapter(
    private val onLikeClicked: onPostLikeShareClicked,
    private val onShareClicked: onPostLikeShareClicked
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, onLikeClicked, onShareClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: PostBinding,
        onLikeClicked: onPostLikeShareClicked,
        onShareClicked: onPostLikeShareClicked
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        init {
            binding.likes.setOnClickListener { onLikeClicked(post) }
            binding.share.setOnClickListener { onShareClicked(post) }
        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                authorName.text = post.author
                text.text = post.content
                date.text = post.date
                likes.setImageResource(getLikeIcon(post.likedByMe))
                countLikes.text = getBeautifulDisplay(post.likes)
                countShares.text = getBeautifulDisplay(post.shares)
                countViews.text = getBeautifulDisplay(post.views)
            }
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

    private object DiffCallBack : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem

    }


}