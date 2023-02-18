package com.natiqhaciyef.coffeshop.ui.view.homescreen

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.natiqhaciyef.coffeshop.R
import com.natiqhaciyef.coffeshop.data.based_datas.Categories
import com.natiqhaciyef.coffeshop.data.model.CategoryModel
import com.natiqhaciyef.coffeshop.data.model.CoffeeModel
import com.natiqhaciyef.coffeshop.databinding.FragmentHomeBinding
import com.natiqhaciyef.coffeshop.databinding.FragmentUserBinding
import com.natiqhaciyef.coffeshop.ui.RegisterActivity
import com.natiqhaciyef.coffeshop.ui.adapter.CategoryAdapter
import com.natiqhaciyef.coffeshop.ui.adapter.CoffeeAdapter
import com.natiqhaciyef.coffeshop.ui.adapter.behavior.CategoryClickListener
import com.natiqhaciyef.coffeshop.ui.adapter.behavior.CoffeeAdapterClickListener
import com.natiqhaciyef.coffeshop.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.sign

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var coffeeAdapter: CoffeeAdapter
    private val categories = Categories.list
    private var coffeeList = mutableListOf<CoffeeModel>()
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        setup()
        setupCategories()
        observeLiveData()
        requireActivity().bottomNavigationView.visibility = View.VISIBLE

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    filterByName(it)
                }
                return false
            }
        })

        binding.userProfile.setOnClickListener {
            userProfile()
        }
    }

    private fun setupCategories() {
        categoryAdapter = CategoryAdapter(requireContext(), categories)
        binding.categoriesRecyclerView.adapter = categoryAdapter
        binding.categoriesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        categoryAdapter.onClick(object : CategoryClickListener {
            override fun setOnCategorySelected(category: CategoryModel) {
                for (element in Categories.list) {
                    element.isChecked = element.name == category.name
                }
                filterByCategory(category.name)
                setupCategories()
            }
        })
    }

    private fun setup() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.searchBar.queryHint =
                Html.fromHtml(getString(R.string.search_view_hint), Html.FROM_HTML_MODE_COMPACT)
        } else {
            binding.searchBar.queryHint = Html.fromHtml(getString(R.string.search_view_hint))
        }
        binding.emptyText.visibility = View.GONE
    }

    private fun observeLiveData() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it == true)
                binding.loadingView.visibility = View.VISIBLE
            else
                binding.loadingView.visibility = View.GONE
        }

        viewModel.coffeeLiveData.observe(viewLifecycleOwner) {
            it.data?.let {
                coffeeList = it.toMutableList()
                coffeeAdapter = CoffeeAdapter(coffeeList, requireContext())
                binding.coffeesRecyclerView.adapter = coffeeAdapter
                binding.coffeesRecyclerView.layoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                goToDetails()
            }
        }
    }

    private fun goToDetails() {
        coffeeAdapter.onClick(object : CoffeeAdapterClickListener {
            override fun setOnClickListener(coffeeModel: CoffeeModel) {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(coffeeModel)
                findNavController().navigate(action)
            }
        })
    }

    fun filterByCategory(category: String) {
        val list = viewModel.categoryFilter(category, coffeeList)
        if (list.isNotEmpty()) {
            coffeeAdapter.filter(list)
            binding.emptyText.visibility = View.GONE
        } else {
            coffeeAdapter.filter(list)
            binding.emptyText.visibility = View.VISIBLE
        }
    }

    fun filterByName(input: String) {
        val list = viewModel.filterByName(input, coffeeList)
        if (list.isNotEmpty()) {
            coffeeAdapter.filter(list)
        } else
            Toast.makeText(requireContext(), "Searched drink not found", Toast.LENGTH_SHORT).show()
    }

    private fun userProfile() {
        val view = FragmentUserBinding.inflate(layoutInflater)
        val signOut = view.signOutButton
        val resetPassword = view.resetPasswordButton

        val customView = AlertDialog.Builder(requireContext())
            .setView(view.root)
            .create()

        signOut.setOnClickListener {
            auth.signOut()
            val intent = Intent(requireActivity(), RegisterActivity::class.java)
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }

        resetPassword.setOnClickListener {
            auth.sendPasswordResetEmail(auth.currentUser!!.email!!).addOnSuccessListener {
                Snackbar.make(requireView(), "Please check your email", Snackbar.LENGTH_SHORT)
                    .show()
            }.addOnFailureListener {
                Snackbar.make(requireView(), "Something went wrong", Snackbar.LENGTH_SHORT).show()
            }
        }

        auth.currentUser?.let {
            view.userEmailText.text = it.email
        }

        customView.show()
    }
}