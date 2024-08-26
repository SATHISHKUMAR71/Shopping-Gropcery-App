package com.example.shoppinggroceryapp.fragments.appfragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.authentication.LoginFragment
import com.example.shoppinggroceryapp.fragments.authentication.SignUpFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

class AccountFragment(val searchBarTop:LinearLayout) : Fragment() {


    private lateinit var editProfile:MaterialButton
    private lateinit var orderHistory:MaterialButton
    private lateinit var help:MaterialButton
    private lateinit var faq:MaterialButton
    private lateinit var savedAddress:MaterialButton
    private lateinit var logoutUser:MaterialButton
    private lateinit var userName:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("On Create Account Frag")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_account, container, false)
        userName = view.findViewById(R.id.userName)
        userName.text = MainActivity.userName
        editProfile = view.findViewById(R.id.editProfile)
        orderHistory = view.findViewById(R.id.orderHistory)
        help = view.findViewById(R.id.help)
        faq = view.findViewById(R.id.faq)
        savedAddress = view.findViewById(R.id.savedAddress)
        logoutUser = view.findViewById(R.id.logout)
        editProfile.setOnClickListener {
            Toast.makeText(context,"Edit Profile Clicked",Toast.LENGTH_SHORT).show()
        }
        orderHistory.setOnClickListener {
            Toast.makeText(context,"Order History Clicked",Toast.LENGTH_SHORT).show()
        }

        help.setOnClickListener {
            Toast.makeText(context,"Help Clicked",Toast.LENGTH_SHORT).show()
        }
        faq.setOnClickListener {
            Toast.makeText(context,"FAQ Clicked",Toast.LENGTH_SHORT).show()
        }
        savedAddress.setOnClickListener {
            Toast.makeText(context,"Saved Address Clicked",Toast.LENGTH_SHORT).show()
        }

        logoutUser.setOnClickListener {
            showAlertDialog()
        }

        return view
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Logout Confirmation")
            .setMessage("Are you sure to Logout?")
            .setPositiveButton("Yes"){_,_ ->
                restartApp()
            }
            .setNegativeButton("No"){dialog,_ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun restartApp() {
        parentFragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val intent = Intent(context,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val sharedPreferences = requireActivity().getSharedPreferences("freshCart",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isSigned",false)
        editor.putString("userName",null)
        editor.putString("userEmail",null)
        editor.putString("userPhone",null)
        editor.putString("userId",null)
        editor.apply()
        startActivity(intent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchBarTop.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchBarTop.visibility = View.VISIBLE
    }
}