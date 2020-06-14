package com.abrahamlay.detail.reviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abrahamlay.domain.entities.ReviewModel
import com.abrahamlay.home.R
import com.abrahamlay.home.databinding.ItemReviewsBinding

/**
 * Created by Abraham Lay on 2019-10-13.
 */
class ReviewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, pos: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding =
            ItemReviewsBinding.bind(inflater.inflate(R.layout.item_reviews, viewGroup, false))
        return ReviewItemViewHolder(binding)
    }

    var data: List<ReviewModel>? = null

    var onClickListener: OnClickListener? = null

    override fun getItemCount(): Int {
        return data?.size!!
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, pos: Int) {
        (viewHolder as ReviewItemViewHolder).itemReviewBinding.reviewModel = data!![pos]
        viewHolder.setOnClickListener(View.OnClickListener {
            onClickListener?.onItemClicked(
                data!![pos]
            )
        })
    }

    interface OnClickListener {
        fun onItemClicked(data: Any)
    }

    inner class ReviewItemViewHolder(val itemReviewBinding: ItemReviewsBinding) :
        RecyclerView.ViewHolder(itemReviewBinding.root) {

        fun setOnClickListener(listener: View.OnClickListener) {
            itemView.setOnClickListener(listener)
        }
    }
}