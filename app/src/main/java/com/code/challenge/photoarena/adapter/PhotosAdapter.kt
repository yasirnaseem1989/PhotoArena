package com.code.challenge.photoarena.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.code.challenge.photoarena.databinding.ItemPhotosBinding
import com.code.challenge.photoarena.view.fragments.home.model.Photos

class PhotosAdapter(
    private var photos: List<Photos>,
    private var onClicked: (Photos) -> Unit
) : RecyclerView.Adapter<PhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val itemBinding = ItemPhotosBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PhotosViewHolder(itemBinding, onClicked)
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.bindView(photos[position])
    }

    fun update(photos: List<Photos>) {
        this.photos = photos
        notifyDataSetChanged()
    }
}

class PhotosViewHolder(
    private val binding: ItemPhotosBinding,
    private val onClicked: (Photos) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindView(photos: Photos) {

        with(binding) {
            photoImageView.load(photos.thumbnailUrl)
            pixaNameTextView.text = photos.name
            pixaImageTags.text = photos.tags
            itemView.setOnClickListener { onClicked(photos) }
        }
    }
}