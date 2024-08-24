package com.example.shoppinggroceryapp.fragments.authentication

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.setPadding
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.database.User
import com.example.shoppinggroceryapp.fragments.topbar.TopBarFragment
import com.example.shoppinggroceryapp.viewmodel.AppViewModel
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
    private lateinit var addProfileImage:ImageView
    private lateinit var addProfileBtn:MaterialButton
    private lateinit var launchCameraForResult: ActivityResultLauncher<Intent>
    private lateinit var launchImageForResult: ActivityResultLauncher<Intent>
    private var profileUri:String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchCameraForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == Activity.RESULT_OK){
                val image = result.data?.extras?.get("data") as Bitmap
                val bitmapData = image.toString()
                addProfileImage.setPadding(0)
//                DiskCache.saveBitMap(requireContext(),bitmapData,image)
                addProfileImage.setImageBitmap(image)
            }
        }

        launchImageForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == Activity.RESULT_OK){
                val image = result.data?.data
                addProfileImage.setPadding(0)
                profileUri = image.toString()
                addProfileImage.setImageURI(image)
            }
        }

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
        addProfileBtn = view.findViewById(R.id.addPictureBtn)
        addProfileImage = view.findViewById(R.id.addPictureImage)

        signUp = view.findViewById(R.id.signUpNewUser)
        requireActivity().findViewById<FrameLayout>(R.id.fragmentBottomNavigation).visibility = View.GONE
        parentFragmentManager.beginTransaction()
            .add(R.id.fragmentSearchView, TopBarFragment())
            .commit()

        addProfileBtn.setOnClickListener{
            showAlertDialog()
        }
        addProfileImage.setOnClickListener{
            showAlertDialog()
        }
        signUp.setOnClickListener {
            if((password.text?.isNotEmpty()==true)&&(confirmedPassword.text.toString() == password.text.toString())){
                val user = User(firstName = firstName.text.toString(),
                    lastName = lastName.text.toString(),
                    emailAddress = email.text.toString(),
                    password = password.text.toString(),
                    phoneNumber = phone.text.toString(),
                    profileImage = profileUri,
                    isAdmin = false)
                AppViewModel.usersList.add(user)
                println("User: $user")
                println("UserList : ${AppViewModel.usersList}")
                Toast.makeText(context,"User Added Successfully",Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }
            else{
                println("No Input ${password.text} ${confirmedPassword.text} ${(password.text?.isNotEmpty()==true)} ${(confirmedPassword.text == password.text)}")
            }
        }
        return view
    }


    private fun showAlertDialog(){
        AlertDialog.Builder(requireContext())
            .setTitle("Choose Image Source")
            .setItems(arrayOf("Camera","Gallery")){_,which ->
                when(which){
                    0 -> {
                        val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        launchCameraForResult.launch(i)
                    }
                    1 -> {
                        val i = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        launchImageForResult.launch(i)
                    }
                }
            }
            .show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }
    override fun onDestroy() {
        super.onDestroy()
        parentFragmentManager.popBackStack()
    }
}