package com.natiqhaciyef.coffeshop.ui.view.homescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.natiqhaciyef.coffeshop.R
import com.natiqhaciyef.coffeshop.data.model.CoffeeModel
import com.natiqhaciyef.coffeshop.data.model.SizeModel
import com.natiqhaciyef.coffeshop.data.based_datas.Sizes
import com.natiqhaciyef.coffeshop.data.model.CartCoffeeModel
import com.natiqhaciyef.coffeshop.databinding.FragmentDetailsBinding
import com.natiqhaciyef.coffeshop.ui.adapter.DetailsSizeAdapter
import com.natiqhaciyef.coffeshop.ui.adapter.behavior.SizeClickListener
import com.natiqhaciyef.coffeshop.ui.viewmodel.CartViewModel
import com.natiqhaciyef.coffeshop.ui.viewmodel.DetailsViewModel
import com.natiqhaciyef.coffeshop.util.calendarFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var sizeAdapter: DetailsSizeAdapter
    private var countedPrice = 0.0
    private var count = 1
    private var selectedSize = "Small"
    private var isLiked = true
    private var observedCartList = mutableListOf<CartCoffeeModel>()
    private val cartViewModel: CartViewModel by viewModels()
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data: DetailsFragmentArgs by navArgs()
        val coffee = data.data

        setup(coffee)
        ratingSetup(coffee.rating)
        setupSizes(countedPrice)
        requireActivity().bottomNavigationView.visibility = View.GONE

        binding.goBack.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.homeFragment)
        }

        binding.addToFavourites.setOnClickListener {
            if (isLiked) {
                viewModel.getAllDataFromDB()
                viewModel.liveData.observe(viewLifecycleOwner) {
                    binding.addToFavourites.setImageResource(R.drawable.filled_like_icon)
                    var counter = 0
                    for (element in it) {
                        if (element.name == coffee.name) {
                            counter ++
                        }
                    }

                    if (counter == 0)
                        viewModel.insertCoffee(coffee)

                }
            } else {
                binding.addToFavourites.setImageResource(R.drawable.unfilled_like_icon)
                viewModel.deleteCoffee(coffee)
            }
            isLiked = !isLiked
        }

        binding.countOfOrders.text = "$count"

        binding.plusButton.setOnClickListener {
            count += 1
            binding.countOfOrders.text = "$count"
            priceCalculation(coffee.price)
            viewModel.refleshSize()
        }

        binding.minusButton.setOnClickListener {
            if (count > 1)
                count -= 1
            binding.countOfOrders.text = "$count"
            priceCalculation(coffee.price)
            viewModel.refleshSize()
        }

        cartViewModel.getAllCartCoffee()
        cartViewModel.cartModelLiveData.observe(viewLifecycleOwner) {
            it.data?.let {
                observedCartList = it.toMutableList()
                binding.addToCartButton.setOnClickListener {
                    val coffeeModel = CartCoffeeModel(
                        id = 0,
                        name = coffee.name,
                        detail = coffee.detail,
                        image = coffee.image,
                        price = coffee.price,
                        size = selectedSize,
                        rating = coffee.rating,
                        category = coffee.category,
                        totalPrice = countedPrice,
                        count = count,
                        date = calendarFormatter(Calendar.getInstance())
                    )

                    val deletedElements = mutableListOf<CartCoffeeModel>()
                    for (element in observedCartList) {
                        if (element.name == coffeeModel.name) {
                            coffeeModel.totalPrice += element.totalPrice
                            coffeeModel.count += element.count
                            deletedElements.add(element)
                        }
                    }

                    viewModel.insertCartCoffee(coffeeModel)

                    for (element in deletedElements) {
                        cartViewModel.deleteCartCoffee(element)
                    }

                    Navigation.findNavController(it).navigate(R.id.cartFragment)
                }
            }
        }
    }

    private fun setup(coffee: CoffeeModel) {
        binding.detailsCoffeeNameText.text = coffee.name
        binding.detailsCoffeeDetailsText.text = coffee.detail
        Glide.with(requireContext()).load(coffee.image).into(binding.detailsCoffeeImageView)
        priceCalculation(coffee.price)
    }

    private fun priceCalculation(coffeePrice: Double) {
        countedPrice = coffeePrice * count
        setupSizes(countedPrice)
        binding.detailsCoffeePriceText.text = "Total price ${"%.2f".format(countedPrice)} $"
    }

    fun setupSizes(coffeePrice: Double) {
        sizeAdapter = DetailsSizeAdapter(requireContext(), Sizes.list)
        binding.sizeRecyclerView.adapter = sizeAdapter
        binding.sizeRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        sizeAdapter.onClick(object : SizeClickListener {
            override fun setOnSizeClickListener(sizeModel: SizeModel) {
                for (element in Sizes.list) {
                    element.isChecked = element.name == sizeModel.name
                    if (sizeModel.name == "Small" && sizeModel.isChecked) {
                        selectedSize = "Small"
                        priceCalculation(countedPrice)
                        countedPrice = coffeePrice
                        binding.detailsCoffeePriceText.text =
                            "Total price ${"%.2f".format(coffeePrice)} $"
                    } else if (sizeModel.name == "Medium" && sizeModel.isChecked) {
                        selectedSize = "Medium"
                        priceCalculation(countedPrice)
                        countedPrice = coffeePrice * 1.4
                        binding.detailsCoffeePriceText.text =
                            "Total price ${"%.2f".format(coffeePrice * 1.4)} $"
                    } else if (sizeModel.name == "Large" && sizeModel.isChecked) {
                        selectedSize = "Large"
                        priceCalculation(countedPrice)
                        countedPrice = coffeePrice * 1.8
                        binding.detailsCoffeePriceText.text =
                            "Total price ${"%.2f".format(coffeePrice * 1.8)} $"
                    }
                }
                setupSizes(coffeePrice)
            }
        })
    }

    private fun ratingSetup(rating: Double) {
        val i = rating.toInt()
        val h = 5 - rating

        when (i) {
            1 -> {
                binding.star1.setImageResource(R.drawable.filled_star_icon)
                if (h > h.toInt())
                    binding.star2.setImageResource(R.drawable.half_filled_star_icon)
                else
                    binding.star2.setImageResource(R.drawable.unfilled_star_icon)
                binding.star3.setImageResource(R.drawable.unfilled_star_icon)
                binding.star4.setImageResource(R.drawable.unfilled_star_icon)
                binding.star5.setImageResource(R.drawable.unfilled_star_icon)
            }
            2 -> {
                binding.star1.setImageResource(R.drawable.filled_star_icon)
                binding.star2.setImageResource(R.drawable.filled_star_icon)
                if (h > h.toInt())
                    binding.star3.setImageResource(R.drawable.half_filled_star_icon)
                else
                    binding.star3.setImageResource(R.drawable.unfilled_star_icon)
                binding.star4.setImageResource(R.drawable.unfilled_star_icon)
                binding.star5.setImageResource(R.drawable.unfilled_star_icon)
            }
            3 -> {
                binding.star1.setImageResource(R.drawable.filled_star_icon)
                binding.star2.setImageResource(R.drawable.filled_star_icon)
                binding.star3.setImageResource(R.drawable.filled_star_icon)
                if (h > h.toInt())
                    binding.star4.setImageResource(R.drawable.half_filled_star_icon)
                else
                    binding.star4.setImageResource(R.drawable.unfilled_star_icon)
                binding.star5.setImageResource(R.drawable.unfilled_star_icon)
            }
            4 -> {
                binding.star1.setImageResource(R.drawable.filled_star_icon)
                binding.star2.setImageResource(R.drawable.filled_star_icon)
                binding.star3.setImageResource(R.drawable.filled_star_icon)
                binding.star4.setImageResource(R.drawable.filled_star_icon)
                if (h > h.toInt())
                    binding.star5.setImageResource(R.drawable.half_filled_star_icon)
                else
                    binding.star5.setImageResource(R.drawable.unfilled_star_icon)
            }
            5 -> {
                binding.star1.setImageResource(R.drawable.filled_star_icon)
                binding.star2.setImageResource(R.drawable.filled_star_icon)
                binding.star3.setImageResource(R.drawable.filled_star_icon)
                binding.star4.setImageResource(R.drawable.filled_star_icon)
                binding.star5.setImageResource(R.drawable.filled_star_icon)
            }
            else -> 0
        }
    }
}
