package com.example.shoppinggroceryapp.fragments.authentication

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.appfragments.HomeFragment
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.entities.products.Category
import com.example.shoppinggroceryapp.model.entities.products.ParentCategory
import com.example.shoppinggroceryapp.model.entities.products.Product
import com.example.shoppinggroceryapp.model.entities.user.Address
import com.example.shoppinggroceryapp.model.entities.user.User
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
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
    private var login = false
    private lateinit var handler: Handler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_login, container, false)
        emailPhoneText = view.findViewById(R.id.emailOrMobile)
        handler = Handler(Looper.getMainLooper())
        password = view.findViewById(R.id.password)
        emailPhoneTextLayout = view.findViewById(R.id.emailLayout)
        passwordLayout = view.findViewById(R.id.passwordLayout)
        signUp = view.findViewById(R.id.signUpBtn)
        forgotPassword = view.findViewById(R.id.forgotPassword)

        loginButton = view.findViewById(R.id.loginButton)
        val db = AppDatabase.getAppDatabase(requireContext())

        loginButton.setOnClickListener {
            Thread{
                val user = db.getUserDao().getUser(emailPhoneText.text.toString(),password.text.toString())
                if(user==null){
                    handler.post {
                        Snackbar.make(view,"Login Failed",Snackbar.LENGTH_SHORT).apply {
                            setBackgroundTint(Color.argb(255,200,20,20))
                            show()
                        }
                    }
                }
                else{
                    val sharedPreferences = requireActivity().getSharedPreferences("freshCart",Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isSigned",true)
                    editor.apply()
                    val pref1 = requireActivity().getSharedPreferences("freshCart",Context.MODE_PRIVATE)
                    val boo1 = pref1.getBoolean("isSigned",false)
                    println("data saved $boo1")
                    handler.post{
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragmentBody,HomeFragment())
                            .commit()
                    }
                }
            }.start()

        }

        signUp.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentBody, SignUpFragment())
                .addToBackStack("Sign Up Fragment")
                .commit()
        }
        return view
    }


}