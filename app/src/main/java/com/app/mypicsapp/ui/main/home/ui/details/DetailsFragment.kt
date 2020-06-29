package com.app.mypicsapp.ui.main.home.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.mypicsapp.R
import com.app.mypicsapp.data.model.ListOfPhotos
import com.app.mypicsapp.databinding.DetailsFragmentBinding
import com.bumptech.glide.Glide

class DetailsFragment : Fragment() {

    companion object {
        fun newInstance() = DetailsFragment()
    }

    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: DetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false)
        val photos: ListOfPhotos? = DetailsFragmentArgs.fromBundle(requireArguments()).photo
        if (photos != null) {
            setupViewModel(photos)
        }

        viewModel.photoDetail.observe(viewLifecycleOwner, Observer {
            val url = if (photos?.webformatURL.isNullOrEmpty()) {
                it.previewURL
            } else {
                it.webformatURL
            }

            Glide.with(this)
                .load(url)
                .thumbnail(Glide.with(this).load(it.previewURL))
                .placeholder(R.drawable.ic_loader)
                .error(R.drawable.ic_error)
                .into(binding.photoView)

            binding.imageDimens.text =
                "Size: " + it.imageHeight.toString() + " h x " + it.imageWidth.toString() + " h"
            binding.imageType.text = "Type: " + it.type
            binding.imageTags.text = "Tags: " + it.tags

            binding.userTxt.text = it.user
            binding.viewsTxt.text = it.views
            binding.likesTxt.text = it.likes
            binding.commentsTxt.text = it.comments
            binding.favTxt.text = it.favorites
            binding.downloadsTxt.text = it.downloads
        })



        return binding.root
    }

    private fun setupViewModel(photos: ListOfPhotos) {
        val viewModelFactory =
            DetailsViewModelFactory(photos)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailsViewModel::class.java)
    }

}