package com.ingridentify.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.ingridentify.R
import com.ingridentify.data.Result
import com.ingridentify.databinding.FragmentRegisterBinding
import com.ingridentify.ui.ViewModelFactory

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel> { ViewModelFactory.getInstance(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (name.isEmpty()) {
                binding.etName.error = getString(R.string.name_cannot_be_empty)
                binding.etName.requestFocus()
            }

            if (email.isEmpty()) {
                binding.etEmail.error = getString(R.string.email_cannot_be_empty)
                binding.etEmail.requestFocus()
            }

            if (password.isEmpty()) {
                binding.etPassword.error = getString(R.string.password_cannot_be_empty)
                binding.etPassword.requestFocus()
            }

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                return@setOnClickListener
            }

            doRegister(name, email, password)
        }

        binding.btnLogin.setOnClickListener {
            requireView().findNavController().navigateUp()
        }
    }

    private fun doRegister(name: String, email: String, password: String) {
        viewModel.register(name, email, password).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnRegister.text = ""
                    binding.btnRegister.isEnabled = false
                }

                is Result.Success -> {
                    //TODO: replace with snackbar
                    Toast.makeText(requireContext(), result.data, Toast.LENGTH_SHORT).show()
                    requireView().findNavController().navigateUp()
                }

                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnRegister.text = getString(R.string.register)
                    binding.btnRegister.isEnabled = true
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}