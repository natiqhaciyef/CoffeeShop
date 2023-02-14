package com.natiqhaciyef.coffeshop.ui.view.homescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.natiqhaciyef.coffeshop.R
import com.natiqhaciyef.coffeshop.data.model.CoffeeModel
import com.natiqhaciyef.coffeshop.databinding.FragmentFavouritesBinding
import com.natiqhaciyef.coffeshop.ui.adapter.FavouriteAdapter
import com.natiqhaciyef.coffeshop.ui.viewmodel.FavouritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment() {
    private lateinit var binding: FragmentFavouritesBinding
    private var list = mutableListOf<CoffeeModel>()
    private lateinit var adapter: FavouriteAdapter
    private val viewModel: FavouritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.favouritesRecyclerView)
    }

    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val coffeeModel = adapter.list[position]
            viewModel.deleteFavourite(coffeeModel)
            findNavController().navigate(R.id.favouritesFragment)

            if (list.isEmpty()) {
                binding.emptyText.visibility = View.VISIBLE
            }
        }
    }

    private fun observeLiveData() {
        viewModel.liveFavouritesData.observe(viewLifecycleOwner) {
            it.data?.let {
                list = it.toMutableList()
                if (list.isNotEmpty()) {
                    binding.emptyText.visibility = View.GONE
                    adapter = FavouriteAdapter(list, requireContext())
                    binding.favouritesRecyclerView.adapter = adapter
                    binding.favouritesRecyclerView.layoutManager =
                        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                }
            }
        }
    }
}