package com.natiqhaciyef.coffeshop.ui.view.register

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.natiqhaciyef.coffeshop.R
import com.natiqhaciyef.coffeshop.databinding.FragmentLoginBinding
import com.natiqhaciyef.coffeshop.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        auth = Firebase.auth

        binding.loginButton.setOnClickListener {
            val email = binding.emailTextInputLogin.text.toString()
            val password = binding.passwordTextInputLogin.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty())
                login(email, password)
        }

        binding.goToRegister.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.registerFragment)
        }

        binding.goToForgotPassword.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.forgotPasswordFragment)
        }
    }


    fun login(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            goToHome()
        }.addOnFailureListener {
            Snackbar.make(requireView(), "Authentication failed. Fail message: ${it.message}", Snackbar.LENGTH_LONG)
                .show()
        }
    }

    private fun goToHome(){
        val intent = Intent(requireActivity(), MainActivity::class.java)
        requireActivity().startActivity(intent)
        requireActivity().finish()
    }

    fun setup(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.goToRegister.text =
                Html.fromHtml(getString(R.string.go_to_register_text), Html.FROM_HTML_MODE_COMPACT)
        } else {
            binding.goToRegister.text = Html.fromHtml(getString(R.string.go_to_register_text))
        }

        binding.passwordVisibile.setOnClickListener {
            binding.passwordTextInputLogin.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.passwordVisibile.visibility = View.GONE
            binding.passwordVisibileOff.visibility = View.VISIBLE
        }

        binding.passwordVisibileOff.setOnClickListener {
            binding.passwordTextInputLogin.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.passwordVisibile.visibility = View.VISIBLE
            binding.passwordVisibileOff.visibility = View.GONE
        }
    }
}