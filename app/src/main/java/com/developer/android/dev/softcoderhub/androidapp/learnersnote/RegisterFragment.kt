package com.developer.android.dev.softcoderhub.androidapp.learnersnote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.databinding.FragmentRegisterBinding
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.models.UserRequest
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.utils.NetworkResult
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.viewmodel.AuthViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val authViewmodel by viewModels<AuthViewmodel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_register, container, false)
//        val textRedirect = view.findViewById<TextView>(R.id.textRedirect);
//        textRedirect.setOnClickListener {
//            findNavController().navigate(R.id.action_registerFragment_to_loginFragment);
//        }
//        return view


        binding.btnSignUp.setOnClickListener {
            lifecycleScope.launch {
                try {
                    authViewmodel.registerUser(UserRequest("dev@gmail.com", "123456", "dev"))
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Registration Failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        binding.txtLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewmodel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                }

                is NetworkResult.Error -> {
                    binding.txtError.text = it.message
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}