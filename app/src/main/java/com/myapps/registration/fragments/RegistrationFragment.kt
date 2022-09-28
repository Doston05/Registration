package com.myapps.registration.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.myapps.registration.AppDatabase
import com.myapps.registration.R
import com.myapps.registration.model.User
import com.myapps.registration.Validation
import com.myapps.registration.databinding.FragmentRegistrationBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val validation= Validation()
        val db= AppDatabase
        val userDao = db.getInstance(requireContext())
        binding.signUp.setOnClickListener {
            if (
                validation.validiatePassword(requireContext(), binding.inputPassword.text.toString(),binding.inputPasswordConfirm.text.toString())
                && validation.validateUsername(requireContext(), binding.inputUsername.text.toString().trim())
            ){


                CoroutineScope(Dispatchers.Main).launch {
                    userDao.userDao().insert(
                        user = User(
                            binding.inputUsername.text.toString(),
                            binding.inputPassword.text.toString()
                        )
                    )

                }
                findNavController().navigate(R.id.action_registrationFragment_to_signInFragment)


            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}