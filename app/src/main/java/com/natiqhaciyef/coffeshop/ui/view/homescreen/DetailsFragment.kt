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
import com.natiqhaciyef.coffeshop.databinding.FragmentDetailsBinding
import com.natiqhaciyef.coffeshop.ui.adapter.DetailsSizeAdapter
import com.natiqhaciyef.coffeshop.ui.adapter.behavior.SizeClickListener
import com.natiqhaciyef.coffeshop.ui.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var sizeAdapter: DetailsSizeAdapter
    private var price = ""
    private var countedPrice = 0.0
    private var count = 1
    private var isLiked = false
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
                binding.addToFavourites.setImageResource(R.drawable.filled_like_icon)
                viewModel.insertCoffee(coffee)
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
        price = "%.2f".format(countedPrice)
        binding.detailsCoffeePriceText.text = "Total price $price $"
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
                        priceCalculation(price.toDouble())
                        price = "${"%.2f".format(coffeePrice)}"
                        binding.detailsCoffeePriceText.text =
                            "Total price $price $"
                    } else if (sizeModel.name == "Medium" && sizeModel.isChecked) {
                        priceCalculation(price.toDouble())
                        price = "${"%.2f".format(coffeePrice * 1.4)}"
                        binding.detailsCoffeePriceText.text =
                            "Total price $price $"
                    } else if (sizeModel.name == "Large" && sizeModel.isChecked) {
                        priceCalculation(price.toDouble())
                        price = "${"%.2f".format(coffeePrice * 1.8)}"
                        binding.detailsCoffeePriceText.text =
                            "Total price $price $"
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
