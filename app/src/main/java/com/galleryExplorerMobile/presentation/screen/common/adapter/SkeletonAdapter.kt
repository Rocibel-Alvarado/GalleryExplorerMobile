package com.galleryExplorerMobile.presentation.screen.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.galleryExplorerMobile.R

class SkeletonAdapter(
    private val itemCount: Int = 6
) : RecyclerView.Adapter<SkeletonAdapter.SkeletonViewHolder>() {

    inner class SkeletonViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkeletonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_skeleton, parent, false)
        return SkeletonViewHolder(view)
    }

    override fun onBindViewHolder(holder: SkeletonViewHolder, position: Int) = Unit

    override fun getItemCount(): Int = itemCount
}