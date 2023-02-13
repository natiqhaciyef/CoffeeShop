package com.natiqhaciyef.coffeshop.ui.view.homescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.natiqhaciyef.coffeshop.R
import com.natiqhaciyef.coffeshop.data.model.CoffeeModel
import com.natiqhaciyef.coffeshop.data.model.SizeModel
import com.natiqhaciyef.coffeshop.data.model.Sizes
import com.natiqhaciyef.coffeshop.databinding.FragmentDetailsBinding
import com.natiqhaciyef.coffeshop.ui.adapter.DetailsSizeAdapter
import com.natiqhaciyef.coffeshop.ui.adapter.behavior.SizeClickListener


class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var sizeAdapter: DetailsSizeAdapter
    private var price = ""
    private var countedPrice = 0.0
    private var isLiked = false

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

        binding.goBack.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.homeFragment)
        }

        binding.addToFavourites.setOnClickListener {
            if (isLiked)
                binding.addToFavourites.setImageResource(R.drawable.filled_like_icon)
            else
                binding.addToFavourites.setImageResource(R.drawable.unfilled_like_icon)

            isLiked = !isLiked
        }
    }

    private fun setup(coffee: CoffeeModel) {
        binding.detailsCoffeeNameText.text = coffee.name
        binding.detailsCoffeeDetailsText.text = coffee.detail
        price = "%.2f".format(coffee.price)
        binding.detailsCoffeePriceText.text = "Total price $price $"
        Glide.with(requireContext()).load(coffee.image).into(binding.detailsCoffeeImageView)

        countedPrice = coffee.price
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
                        price = "${"%.2f".format(coffeePrice)}"
                        binding.detailsCoffeePriceText.text =
                            "Total price $price $"
                    } else if (sizeModel.name == "Medium" && sizeModel.isChecked) {
                        price = "${"%.2f".format(coffeePrice * 1.4)}"
                        binding.detailsCoffeePriceText.text =
                            "Total price $price $"
                    } else if (sizeModel.name == "Large" && sizeModel.isChecked) {
                        price = "${"%.2f".format(coffeePrice * 1.8)}"
                        binding.detailsCoffeePriceText.text =
                            "Total price $price $"
                    }
                }
                setupSizes(coffeePrice)
            }
        })
    }
}
