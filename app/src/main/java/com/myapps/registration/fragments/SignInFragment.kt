package com.myapps.registration.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.myapps.registration.AppDatabase
import com.myapps.registration.R
import com.myapps.registration.databinding.FragmentSignInBinding
import com.myapps.registration.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private lateinit var userDao:AppDatabase
    private lateinit var userList:List<User>
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AppDatabase
         userDao = AppDatabase.getInstance(requireContext())

       getAllUsers()
       openSignUpScreen()
        logInClick()
    }

    private fun openSignUpScreen() {
        binding.signUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_registrationFragment)
        }
    }

    private fun logInClick(){
            binding.login.setOnClickListener {
             if (isUserRegistered()){
                 Toast.makeText(requireContext(), "You successfully logged in your account", Toast.LENGTH_SHORT).show()
                 findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
             }
                else{
                 Toast.makeText(requireContext(), "Email or Password is incorrect", Toast.LENGTH_SHORT).show()

             }
            }

    }
    private fun isUserRegistered():Boolean{
        for (i in userList.indices){
            if (userList[i].username==binding.inputUsername.text.toString().trim() && userList[i].password==binding.inputPassword.text.toString()){
                return true

            }


        }
        return false
    }
  private fun getAllUsers(){
      CoroutineScope(Dispatchers.IO).launch {
          userList=userDao.userDao().getAll()
      }
  }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}