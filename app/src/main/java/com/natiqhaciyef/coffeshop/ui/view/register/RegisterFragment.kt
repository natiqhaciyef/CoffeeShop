package com.natiqhaciyef.coffeshop.ui.view.register

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
import com.natiqhaciyef.coffeshop.R
import com.natiqhaciyef.coffeshop.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

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
        binding.goToLogin.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.loginFragment)
        }
    }

    private fun setup(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.goToLogin.text =
                Html.fromHtml(getString(R.string.go_to_login_text), Html.FROM_HTML_MODE_COMPACT)
        } else {
            binding.goToLogin.text = Html.fromHtml(getString(R.string.go_to_login_text))
        }

        binding.passwordVisibile.setOnClickListener {
            binding.passwordTextInputRegister.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.passwordVisibile.visibility = View.GONE
            binding.passwordVisibileOff.visibility = View.VISIBLE
        }

        binding.passwordVisibileOff.setOnClickListener {
            binding.passwordTextInputRegister.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.passwordVisibile.visibility = View.VISIBLE
            binding.passwordVisibileOff.visibility = View.GONE
        }
    }
}