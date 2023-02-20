package com.natiqhaciyef.coffeshop.ui.view.homescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.natiqhaciyef.coffeshop.R
import com.natiqhaciyef.coffeshop.databinding.FragmentPaymentDetailsBinding


class PaymentDetailsFragment : Fragment() {
    private lateinit var binding: FragmentPaymentDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data: PaymentDetailsFragmentArgs by navArgs()
        val paymentMethod = data.paymentMethod
        // ^4[0-9]{12}(?:[0-9]{3})?$  - visa
        // ^(5[1-5][0-9]{14}|2(22[1-9][0-9]{12}|2[3-9][0-9]{13}|[3-6][0-9]{14}|7[0-1][0-9]{13}|720[0-9]{12}))$  - mastercard

        binding.paymentMethodImage.setImageResource(requireContext().resources
            .getIdentifier(paymentMethod.lowercase(),"drawable",requireContext().packageName))

        binding.backIcon.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
    }
}