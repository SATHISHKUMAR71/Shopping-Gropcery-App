package com.example.shoppinggroceryapp.fragments.appfragments.accountfragments

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
import androidx.fragment.app.FragmentManager
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.R
import com.google.android.material.button.MaterialButton

class AccountFragment(private val searchBarTop:LinearLayout) : Fragment() {


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
        val name = MainActivity.userFirstName + " "+ MainActivity.userLastName
        userName = view.findViewById(R.id.userName)
        userName.text =name
        editProfile = view.findViewById(R.id.editProfile)
        orderHistory = view.findViewById(R.id.orderHistory)
        help = view.findViewById(R.id.help)
        faq = view.findViewById(R.id.faq)
        savedAddress = view.findViewById(R.id.savedAddress)
        logoutUser = view.findViewById(R.id.logout)
        editProfile.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentMainLayout,EditProfile(searchBarTop))
                .addToBackStack("Edit Profile")
                .commit()
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
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentMainLayout,SavedAddress(searchBarTop))
                .addToBackStack("Saved Address")
                .commit()
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
        val intent = Intent(context,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val sharedPreferences = requireActivity().getSharedPreferences("freshCart",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isSigned",false)
        editor.putString("userFirstName",null)
        editor.putString("userLastName",null)
        editor.putString("userEmail",null)
        editor.putString("userPhone",null)
        editor.putString("userId",null)
        editor.apply()
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onStop() {
        super.onStop()
        searchBarTop.visibility = View.VISIBLE
    }
    override fun onResume() {
        super.onResume()
        searchBarTop.visibility = View.GONE
    }
}