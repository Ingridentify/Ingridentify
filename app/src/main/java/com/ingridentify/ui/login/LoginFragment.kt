package com.ingridentify.ui.login

import android.content.Intent
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
import com.ingridentify.databinding.FragmentLoginBinding
import com.ingridentify.ui.ViewModelFactory
import com.ingridentify.ui.main.MainActivity

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<LoginViewModel> { ViewModelFactory.getInstance(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.etEmail.error = getString(R.string.email_cannot_be_empty)
                binding.etEmail.requestFocus()
            }

            if (password.isEmpty()) {
                binding.etPassword.error = getString(R.string.password_cannot_be_empty)
                binding.etPassword.requestFocus()
            }

            if (email.isEmpty() || password.isEmpty()) {
                return@setOnClickListener
            }

            doLogin(email, password)
        }

        binding.btnRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            requireView().findNavController().navigate(action)
        }
    }

    private fun doLogin(email: String, password: String) {
        viewModel.login(email, password).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnLogin.text = ""
                    binding.btnLogin.isEnabled = false
                }

                is Result.Success -> {
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                }

                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnLogin.text = getString(R.string.login)
                    binding.btnLogin.isEnabled = true
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}