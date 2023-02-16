package com.natiqhaciyef.coffeshop.ui.view.homescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.natiqhaciyef.coffeshop.R
import com.natiqhaciyef.coffeshop.data.model.CartCoffeeModel
import com.natiqhaciyef.coffeshop.databinding.FragmentCartBinding
import com.natiqhaciyef.coffeshop.ui.adapter.CartAdapter
import com.natiqhaciyef.coffeshop.ui.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var adapter: CartAdapter
    private var totalPrice = 0.0
    private var list = mutableListOf<CartCoffeeModel>()
    private val viewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.emptyText.visibility = View.GONE
        observerLiveData()
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerCartView)

        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.homeFragment)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun observerLiveData() {
        viewModel.cartModelLiveData.observe(viewLifecycleOwner) {
            it.data?.let {
                if (it.isNotEmpty())
                    list = it.toMutableList()
                else
                    binding.emptyText.visibility = View.VISIBLE
                calculateTotalPrice()
                setup()
            }
        }
    }

    private fun setup() {
        adapter = CartAdapter(list.toSet().toList(), requireContext())
        binding.recyclerCartView.adapter = adapter
        binding.recyclerCartView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun calculateTotalPrice() {
        for (element in list) {
            totalPrice += element.totalPrice
        }
        val price = "%.2f".format(totalPrice)
        binding.totalPriceText.text = "Total price: $price"
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
            viewModel.deleteCartCoffee(coffeeModel)
            findNavController().navigate(R.id.cartFragment)

            if (list.isEmpty()) {
                binding.emptyText.visibility = View.VISIBLE
            }
        }
    }
}
