package com.app.mypicsapp.ui.main.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.mypicsapp.R
import com.app.mypicsapp.di.ApiModule
import com.app.mypicsapp.data.model.ListOfPhotos
import com.app.mypicsapp.data.repository.Repository
import com.app.mypicsapp.databinding.HomeFragmentBinding
import com.app.mypicsapp.ui.main.home.adapter.PhotoAdapter
import com.app.mypicsapp.ui.main.viewModelFactory.ViewModelFactory
import com.app.mypicsapp.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), PhotoAdapter.OnItemClickListener {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var adapter: PhotoAdapter
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: HomeFragmentBinding
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)

        setUpListAdapter()
        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.getPhotos().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        isLoading = false
                        binding.loaderBar.visibility = View.GONE
                        resource.data?.let { photos -> populateData(photos) }
                    }
                    Status.ERROR -> {
                        isLoading = false
                        binding.loaderBar.visibility = View.GONE
                    }
                    Status.LOADING -> {

                    }
                }
            }
        })
    }

    private fun populateData(photos: ArrayList<ListOfPhotos>) {
            adapter.apply {
                clearPhotos()
                addPhotos(photos)
                notifyDataSetChanged()
            }
    }

    private fun setUpListAdapter() {

        adapter = PhotoAdapter(arrayListOf(), this)

        binding.photoList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.photoList.adapter = adapter
        binding.photoList.isNestedScrollingEnabled = false
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY


        binding.photoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount: Int? =
                    layoutManager?.findLastCompletelyVisibleItemPosition() + 1
                val totalItemCount: Int = layoutManager.itemCount

                if (!isLoading && visibleItemCount == totalItemCount && totalItemCount > 0) {
                    //Load more data
                    isLoading = true
                    viewModel.setPage()
                    binding.photoList.scrollToPosition(totalItemCount - 1)
                    binding.loaderBar.visibility = View.VISIBLE
                    viewModel.getPhotos(viewModel.page.value?:1)
                }
            }
        })

    }

    override fun onItemClick(item: ListOfPhotos) {
        val action = HomeFragmentDirections.actionHomeToDetails()
        action.photo = item
        NavHostFragment.findNavController(this).navigate(action)
    }


}