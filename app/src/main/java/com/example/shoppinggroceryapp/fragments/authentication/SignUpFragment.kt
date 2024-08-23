package com.example.shoppinggroceryapp.fragments.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.dataclass.User
import com.example.shoppinggroceryapp.fragments.topbar.TopBarFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText


class SignUpFragment : Fragment() {

    private lateinit var firstName:TextInputEditText
    private lateinit var lastName:TextInputEditText
    private lateinit var email:TextInputEditText
    private lateinit var phone:TextInputEditText
    private lateinit var password:TextInputEditText
    private lateinit var confirmedPassword:TextInputEditText
    private lateinit var signUp:MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        val appTopBar = requireActivity().findViewById<FrameLayout>(R.id.fragmentSearchView)
        appTopBar.visibility = View.VISIBLE
        firstName = view.findViewById(R.id.firstName)
        lastName = view.findViewById(R.id.signUpLastName)
        email = view.findViewById(R.id.signUpEmail)
        phone = view.findViewById(R.id.signUpPhoneNumber)
        password =view.findViewById(R.id.signUpPassword)
        confirmedPassword = view.findViewById(R.id.signUpConfirmPassword)
        signUp = view.findViewById(R.id.signUpNewUser)
        requireActivity().findViewById<FrameLayout>(R.id.fragmentBottomNavigation).visibility = View.GONE
        parentFragmentManager.beginTransaction()
            .add(R.id.fragmentSearchView, TopBarFragment())
            .commit()

        signUp.setOnClickListener {
            if((password.text?.isNotEmpty()==true)&&(confirmedPassword.text.toString() == password.text.toString())){
                val user = User(firstName = firstName.text.toString(),
                    lastName = lastName.text.toString(),
                    emailAddress = email.text.toString(),
                    password = password.text.toString(),
                    phoneNumber = phone.text.toString(),
                    profileImage = "")
                println("User: $user")
                Toast.makeText(context,"User Added Successfully",Toast.LENGTH_SHORT).show()
            }
            else{
                println("No Input ${password.text} ${confirmedPassword.text} ${(password.text?.isNotEmpty()==true)} ${(confirmedPassword.text == password.text)}")
            }
        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        parentFragmentManager.popBackStack()
    }
}