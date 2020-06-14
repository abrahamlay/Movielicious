package com.abrahamlay.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abrahamlay.base.constant.Constants
import com.abrahamlay.base.util.GlideHelper
import com.abrahamlay.domain.entities.MovieModel
import com.abrahamlay.home.databinding.ItemMovieHeaderBinding
import com.abrahamlay.home.databinding.ItemMovieSectionBinding

/**
 * Created by Abraham Lay on 2019-10-13.
 */
class MovieAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, pos: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)


        return if (isHeader) MovieItemHeaderViewHolder(
            ItemMovieHeaderBinding.bind(
                inflater.inflate(
                    R.layout.item_movie_header,
                    viewGroup,
                    false
                )
            )
        )
        else
            MovieItemSectionViewHolder(
                ItemMovieSectionBinding.bind(
                    inflater.inflate(
                        R.layout.item_movie_section,
                        viewGroup,
                        false
                    )
                )
            )

    }

    var isHeader: Boolean = false

    var data: List<MovieModel>? = arrayListOf()

    var onClickListener: OnClickListener? = null

    override fun getItemCount(): Int {
        return data?.size!!
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, pos: Int) {
        when (viewHolder) {
            is MovieItemHeaderViewHolder -> {
                (viewHolder as MovieItemHeaderViewHolder).itemMovieBinding.movieModel = data!![pos]
                viewHolder.setOnClickListener(View.OnClickListener {
                    onClickListener?.onItemClicked(
                        data!![pos]
                    )
                })
            }
            is MovieItemSectionViewHolder -> {
                (viewHolder as MovieItemSectionViewHolder).itemMovieBinding.movieModel = data!![pos]
                viewHolder.setOnClickListener(View.OnClickListener {
                    onClickListener?.onItemClicked(
                        data!![pos]
                    )
                })
            }
        }
    }

    interface OnClickListener {
        fun onItemClicked(data: Any)
    }

    inner class MovieItemSectionViewHolder(val itemMovieBinding: ItemMovieSectionBinding) :
        RecyclerView.ViewHolder(itemMovieBinding.root) {

        fun setOnClickListener(listener: View.OnClickListener) {
            itemView.setOnClickListener(listener)
        }
    }

    inner class MovieItemHeaderViewHolder(val itemMovieBinding: ItemMovieHeaderBinding) :
        RecyclerView.ViewHolder(itemMovieBinding.root) {

        fun setOnClickListener(listener: View.OnClickListener) {
            itemView.setOnClickListener(listener)
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                val addedUrl =
                    if (!url.contains("https://") || !url.contains("http://")) String.format(
                        Constants.MOVIE_THUMBNAIL_BASE_URL_LARGE,
                        url
                    ) else url
                GlideHelper.showThumbnail(addedUrl, view, view.context)
            }
        }
    }
}