package com.app.mypicsapp.ui.main.login.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.app.mypicsapp.databinding.LoginFragmentBinding
import com.app.mypicsapp.ui.main.home.HomeActivity
import com.app.mypicsapp.ui.main.viewModelFactory.ViewModelFactory
import com.app.mypicsapp.util.Status
import com.app.mypicsapp.util.Validator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.login_fragment, container, false
        )

        binding.signUp.setOnClickListener {
            findNavController()
                .navigate(LoginFragmentDirections.actionLoginToRegister())
        }

        binding.signInBtn.setOnClickListener {
            var errorFlag = false

            if (!passwordValidator(binding.passwordText.text)) {
                errorFlag = true
            }
            if (!emailValidator(binding.emailText.text)) {
                errorFlag = true
            }


            if (errorFlag) {
                return@setOnClickListener
            }

            viewModel.loginUser(binding.emailText.text.toString(), binding.passwordText.text.toString())

        }

        viewModel.getLoginResponse().observe(viewLifecycleOwner, Observer {
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

        binding.emailText.addTextChangedListener(emailWatcher())

        return binding.root
    }

    private fun emailWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                emailValidator(s)
            }

        }
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


}