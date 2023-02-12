package com.natiqhaciyef.coffeshop.ui.view.homescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.natiqhaciyef.coffeshop.R
import com.natiqhaciyef.coffeshop.data.model.Sizes
import com.natiqhaciyef.coffeshop.databinding.FragmentDetailsBinding
import com.natiqhaciyef.coffeshop.ui.adapter.DetailsSizeAdapter


class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
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

        binding.detailsCoffeeNameText.text = coffee.name
        binding.detailsCoffeeDetailsText.text = coffee.detail
        binding.detailsCoffeePriceText.text = "Total price ${coffee.price} $"
        Glide.with(requireContext()).load(coffee.image).into(binding.detailsCoffeeImageView)
        ratingSetup(coffee.rating)
        binding.sizeRecyclerView.adapter = DetailsSizeAdapter(requireContext(), Sizes.list)
        binding.sizeRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    fun ratingSetup(rating: Double) {
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