package com.example.shoppinggroceryapp.fragments.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.shoppinggroceryapp.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class LoginFragment : Fragment() {

    private lateinit var emailPhoneTextLayout:TextInputLayout
    private lateinit var loginButton:MaterialButton
    private lateinit var passwordLayout:TextInputLayout
    private lateinit var emailPhoneText:TextInputEditText
    private lateinit var password:TextInputEditText
    private lateinit var forgotPassword:MaterialButton
    private lateinit var signUp:MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_login, container, false)
        emailPhoneText = view.findViewById(R.id.emailOrMobile)
        password = view.findViewById(R.id.password)
        emailPhoneTextLayout = view.findViewById(R.id.emailLayout)
        passwordLayout = view.findViewById(R.id.passwordLayout)
        signUp = view.findViewById(R.id.signUpBtn)
        requireActivity().findViewById<FrameLayout>(R.id.fragmentSearchView).visibility = View.GONE
        requireActivity().findViewById<FrameLayout>(R.id.fragmentBottomNavigation).visibility = View.GONE
        forgotPassword = view.findViewById(R.id.forgotPassword)
        loginButton = view.findViewById(R.id.loginButton)
        signUp.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentBody, SignUpFragment())
                .addToBackStack("Sign Up Fragment")
                .commit()
        }
        return view
    }


}