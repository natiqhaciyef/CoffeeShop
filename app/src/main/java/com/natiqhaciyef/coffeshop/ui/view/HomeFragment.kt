package com.natiqhaciyef.coffeshop.ui.view

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.natiqhaciyef.coffeshop.R
import com.natiqhaciyef.coffeshop.data.model.Categories
import com.natiqhaciyef.coffeshop.data.model.CategoryModel
import com.natiqhaciyef.coffeshop.data.model.CoffeeModel
import com.natiqhaciyef.coffeshop.databinding.FragmentHomeBinding
import com.natiqhaciyef.coffeshop.ui.adapter.CategoryAdapter
import com.natiqhaciyef.coffeshop.ui.adapter.CoffeeAdapter
import com.natiqhaciyef.coffeshop.ui.adapter.behavior.CategoryClickListener


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private val categories = Categories.list
    private lateinit var coffeeAdapter: CoffeeAdapter
    private var coffeeList = mutableListOf(
        CoffeeModel(
            id = 0,
            name = "Cappuccino",
            image = "https://github.com/natiqhaciyef/CoffeeShop/blob/master/Coffee%20Images/cappuccino.jpg?raw=true",
            price = 4.50,
            size = "Medium",
            rating = 4.8
        ),
        CoffeeModel(
            id = 0,
            name = "Matcha Espresso",
            image = "https://github.com/natiqhaciyef/CoffeeShop/blob/master/Coffee%20Images/matcha.jpg?raw=true",
            price = 7.65,
            size = "Small",
            rating = 4.3
        ),
        CoffeeModel(
            id = 0,
            name = "Americano",
            image = "https://github.com/natiqhaciyef/CoffeeShop/blob/master/Coffee%20Images/americano.jpg?raw=true",
            price = 4.35,
            size = "Small",
            rating = 4.1
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()

        coffeeAdapter = CoffeeAdapter(coffeeList, requireContext())
        binding.coffeesRecyclerView.adapter = coffeeAdapter
        binding.coffeesRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { }
                return false
            }
        })
    }

    private fun setup() {
        categoryAdapter = CategoryAdapter(requireContext(), categories)
        binding.categoriesRecyclerView.adapter = categoryAdapter
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.searchBar.queryHint =
                Html.fromHtml(getString(R.string.search_view_hint), Html.FROM_HTML_MODE_COMPACT)
        } else {
            binding.searchBar.queryHint = Html.fromHtml(getString(R.string.search_view_hint))
        }
    }
}