package com.example.ourproject.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ourproject.R
import com.example.ourproject.databinding.FragmentLogInBinding

class LogInFragment : Fragment() {
    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupValidation()
        setupLoginButton()
    }

    private fun setupValidation() {
        fun validate() {
            val email = binding.editEmail.text.toString().trim()
            val pass = binding.editPassword.text.toString().trim()

            val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
            val isPasswordValid = isPasswordStrong(pass)

            binding.buttonSubmit.isEnabled = isEmailValid && isPasswordValid

            binding.editEmail.error = if (email.isNotEmpty() && !isEmailValid) {
                "Некорректный email"
            } else null

            binding.editPassword.error = if (pass.isNotEmpty() && !isPasswordValid) {
                "Пароль должен содержать: 8+ символов, цифру, буквы верхнего и нижнего регистра"
            } else null
        }

        binding.editEmail.addTextChangedListener { validate() }
        binding.editPassword.addTextChangedListener { validate() }
        validate()
    }

    private fun isPasswordStrong(password: String): Boolean {
        return password.length >= 8 &&
                password.any { it.isDigit() } &&
                password.any { it.isLowerCase() } &&
                password.any { it.isUpperCase() }
    }

    private fun setupLoginButton() {
        binding.buttonSubmit.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_taskListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}