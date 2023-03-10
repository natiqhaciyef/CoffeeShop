package com.natiqhaciyef.coffeshop.ui.view.homescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.natiqhaciyef.coffeshop.R
import com.natiqhaciyef.coffeshop.databinding.FragmentPaymentBinding
import kotlinx.android.synthetic.main.fragment_payment.*


class PaymentFragment : Fragment() {
    private lateinit var binding: FragmentPaymentBinding
    private var selectedPaymentMethod = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.continueButton.isEnabled = false

        binding.masterCardRadioButton.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) {
                selectedPaymentMethod = "MasterCard"
                binding.visaCardRadioButton.isChecked = false
                binding.paypalCardRadioButton.isChecked = false
                binding.continueButton.isEnabled = true
            }
        }

        binding.visaCardRadioButton.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) {
                selectedPaymentMethod = "Visa"
                binding.masterCardRadioButton.isChecked = false
                binding.paypalCardRadioButton.isChecked = false
                binding.continueButton.isEnabled = true
            }
        }

        binding.paypalCardRadioButton.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) {
                selectedPaymentMethod = "Paypal"
                binding.visaCardRadioButton.isChecked = false
                binding.masterCardRadioButton.isChecked = false
                binding.continueButton.isEnabled = true
            }
        }

        binding.backIcon.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.cartFragment)
        }

        binding.continueButton.setOnClickListener {
            val action = PaymentFragmentDirections.actionPaymentFragmentToPaymentDetailsFragment(selectedPaymentMethod)
            Navigation.findNavController(it).navigate(action)
        }
    }
}