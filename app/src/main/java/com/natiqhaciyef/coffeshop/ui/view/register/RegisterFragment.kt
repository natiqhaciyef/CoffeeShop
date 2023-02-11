package com.natiqhaciyef.coffeshop.ui.view.register

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
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
import com.natiqhaciyef.coffeshop.databinding.FragmentRegisterBinding
import com.natiqhaciyef.coffeshop.ui.MainActivity
import java.io.IOException


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        auth = Firebase.auth
        if (auth.currentUser != null)
            goToHome()

        binding.goToLogin.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.loginFragment)
        }

        binding.registerButton.setOnClickListener {
            val username = binding.usernameTextInputRegister.text.toString()
            val email = binding.emailTextInputRegister.text.toString()
            val password = binding.passwordTextInputRegister.text.toString()
            Log.e("NUSALFDNASOD","Email : $email --- Password : $password")
            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty())
                register(username, email, password)
        }
    }

    fun register(username: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            goToHome()
            // username added to room
        }.addOnFailureListener {
            Snackbar.make(requireView(), "Authentication failed. Fail message: ${it.message}", Snackbar.LENGTH_LONG)
                .show()
        }
    }

    private fun goToHome() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        requireActivity().startActivity(intent)
        requireActivity().finish()
    }

    private fun setup() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.goToLogin.text =
                Html.fromHtml(getString(R.string.go_to_login_text), Html.FROM_HTML_MODE_COMPACT)
        } else {
            binding.goToLogin.text = Html.fromHtml(getString(R.string.go_to_login_text))
        }

        binding.passwordVisibile.setOnClickListener {
            binding.passwordTextInputRegister.transformationMethod =
                PasswordTransformationMethod.getInstance()
            binding.passwordVisibile.visibility = View.GONE
            binding.passwordVisibileOff.visibility = View.VISIBLE
        }

        binding.passwordVisibileOff.setOnClickListener {
            binding.passwordTextInputRegister.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
            binding.passwordVisibile.visibility = View.VISIBLE
            binding.passwordVisibileOff.visibility = View.GONE
        }
    }
}