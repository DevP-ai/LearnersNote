package com.developer.android.dev.softcoderhub.androidapp.learnersnote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.databinding.FragmentLoginBinding
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.models.UserRequest
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.utils.NetworkResult
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.utils.TokenManager
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.viewmodel.AuthViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding:FragmentLoginBinding?=null
    private val binding get() = _binding!!

    private val authViewmodel by viewModels<AuthViewmodel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val validation = validateUserInput()
            if(validation.first){
//                val userRequest = getUserRequest()
                lifecycleScope.launch {
                    authViewmodel.loginUser(getUserRequest())
                }
            }else{
                binding.txtError.text = validation.second
            }

        }

        binding.txtSignUp.setOnClickListener {
           findNavController().popBackStack()
        }

        bindObservers()
    }

    private fun validateUserInput():Pair<Boolean,String>{
        val userRequest = getUserRequest()
        return authViewmodel.validateCredential(userRequest.email,userRequest.username,userRequest.password,true)
    }

    private fun getUserRequest(): UserRequest {
        return binding.run {
            UserRequest(
                txtEmail.text.toString(),
                txtPassword.text.toString(),
                ""
            )
        }

    }
    private fun bindObservers() {
        authViewmodel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when(it){
                is NetworkResult.Success->{
                    tokenManager.saveToken(it.data!!.token)
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }
                is NetworkResult.Error ->{
                    binding.txtError.text = it.message
                }

                is NetworkResult.Loading ->{
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