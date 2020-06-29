package com.app.mypicsapp.ui.main.login.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.mypicsapp.R
import com.app.mypicsapp.di.ApiModule
import com.app.mypicsapp.data.repository.Repository
import com.app.mypicsapp.databinding.RegisterFragmentBinding
import com.app.mypicsapp.ui.main.home.HomeActivity
import com.app.mypicsapp.ui.main.viewModelFactory.ViewModelFactory
import com.app.mypicsapp.util.Status
import com.app.mypicsapp.util.Validator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var binding: RegisterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.register_fragment, container, false)

        binding.signInBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.signUpBtn.setOnClickListener {
            var errorFlag = false
            val password = binding.passwordText.text.toString()
            val passwordConfirm = binding.passwordConfirmText.text.toString()
            val email = binding.emailText.text.toString()
            val age = if (binding.ageText.text.isNullOrEmpty()) {
                0
            } else {
                binding.ageText.text.toString().toInt()
            }

            if (!passwordValidator(password)) {
                errorFlag = true
            } else if (!passwordMatcher(password, passwordConfirm)) {
                errorFlag = true
            }
            if (!emailValidator(email)) {
                errorFlag = true
            }
            if (!ageValidator(age)) {
                errorFlag = true
            }

            if (errorFlag) return@setOnClickListener

            viewModel.registerUser(email, password, age)
        }

        viewModel.getRegisterResponse().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        startActivity(Intent(requireContext(), HomeActivity::class.java))
                        requireActivity().finish()
                    }
                    Status.ERROR -> {

                    }
                    Status.LOADING -> {

                    }

                }
            }
        })

        return binding.root
    }

    private fun emailValidator(s: CharSequence?): Boolean {
        return if (Validator.isEmailValid(s.toString())) {
            binding.emailTextField.error = null
            true
        } else {
            binding.emailTextField.error = resources.getString(R.string.email_error)
            false
        }
    }

    private fun passwordValidator(s: CharSequence?): Boolean {
        return if (Validator.isPasswordValid(s.toString())) {
            binding.passwordTextField.error = null
            true
        } else {
            binding.passwordTextField.error = resources.getString(R.string.password_error)
            false
        }
    }

    private fun passwordMatcher(password: CharSequence?, confirmPassword: CharSequence?): Boolean {
        return if (Validator.isPasswordMatching(password.toString(), confirmPassword.toString())) {
            binding.passwordConfirmTextField.error = null
            true
        } else {
            binding.passwordConfirmTextField.error =
                resources.getString(R.string.password_match_error)
            false
        }
    }

    private fun ageValidator(age: Int): Boolean {
        return if (Validator.isAgeinRange(age)) {
            binding.ageTextField.error = null
            true
        } else {
            binding.ageTextField.error = resources.getString(R.string.age_error)
            false
        }
    }


}