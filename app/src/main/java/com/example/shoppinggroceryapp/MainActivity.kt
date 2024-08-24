package com.example.shoppinggroceryapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.fragments.authentication.LoginFragment
import com.example.shoppinggroceryapp.viewmodel.AppViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appViewModel: AppViewModel
    private lateinit var fragmentTopBarView:FrameLayout
    private lateinit var fragmentBottomBarView:FrameLayout
    private lateinit var loginFragment: LoginFragment
    companion object{
        private const val REQUEST_CAMERA_PERMISSION = 200
        private val permissions = arrayOf(Manifest.permission.CAMERA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginFragment = LoginFragment()

        ActivityCompat.requestPermissions(this, permissions, REQUEST_CAMERA_PERMISSION)
        fragmentTopBarView = findViewById(R.id.fragmentSearchView)
        fragmentBottomBarView = findViewById(R.id.fragmentBottomNavigation)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentBody,loginFragment)
            .commit()
    }
}