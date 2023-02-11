package com.natiqhaciyef.coffeshop.ui.view.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.natiqhaciyef.coffeshop.R
import com.natiqhaciyef.coffeshop.databinding.FragmentForgotPasswordBinding
import java.io.IOException


class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth

        binding.sendResetPasswordMailButton.setOnClickListener {
            val email = binding.emailTextInputForgotPassword.text.toString()
            if (email.isNotEmpty())
                resetMail(email, it)
        }
    }

    fun resetMail(email: String, view: View) {
        auth.sendPasswordResetEmail(email).addOnSuccessListener {
            Navigation.findNavController(view).navigate(R.id.loginFragment)
        }.addOnFailureListener {
            Snackbar.make(requireView(), "Authentication failed. Fail message: ${it.message}", Snackbar.LENGTH_LONG)
                .show()
        }
    }
}