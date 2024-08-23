package com.example.shoppinggroceryapp

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinggroceryapp.fragments.authentication.LoginFragment

class MainActivity : AppCompatActivity() {

    private lateinit var fragmentTopBarView:FrameLayout
    private lateinit var fragmentBottomBarView:FrameLayout
    private lateinit var loginFragment: LoginFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginFragment = LoginFragment()
        fragmentTopBarView = findViewById(R.id.fragmentSearchView)
        fragmentBottomBarView = findViewById(R.id.fragmentBottomNavigation)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentBody,loginFragment)
            .commit()
    }
}