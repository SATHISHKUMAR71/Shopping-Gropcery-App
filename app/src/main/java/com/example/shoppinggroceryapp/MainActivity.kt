package com.example.shoppinggroceryapp

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.shoppinggroceryapp.fragments.appfragments.InitialFragment
import com.example.shoppinggroceryapp.fragments.authentication.LoginFragment


class MainActivity : AppCompatActivity() {

    private lateinit var fragmentTopBarView:FrameLayout
    private lateinit var fragmentBottomBarView:FrameLayout
    private lateinit var loginFragment: LoginFragment
    companion object{
        private const val REQUEST_CAMERA_PERMISSION = 200
        private val permissions = arrayOf(Manifest.permission.CAMERA)
        var userName = ""
        var userId = ""
        var userEmail = ""
        var userPhone = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginFragment = LoginFragment()
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CAMERA_PERMISSION)

        val pref = getSharedPreferences("freshCart", Context.MODE_PRIVATE)
        val boo = pref.getBoolean("isSigned",false)
        userName = pref.getString("userName","User").toString()
        userId = pref.getString("userId","userId").toString()
        userEmail = pref.getString("userEmail","userEmail").toString()
        userPhone = pref.getString("userPhone","userPhone").toString()
        if(boo){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentBody,InitialFragment())
                .commit()
        }
        else{
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentBody,loginFragment)
                .commit()
        }

    }
}