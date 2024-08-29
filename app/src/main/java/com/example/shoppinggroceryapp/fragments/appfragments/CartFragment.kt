package com.example.shoppinggroceryapp.fragments.appfragments

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.appfragments.accountfragments.SavedAddress
import com.example.shoppinggroceryapp.fragments.appfragments.recyclerview.ProductListAdapter
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.entities.user.Address
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import java.io.File

class CartFragment(var searchBarTop:LinearLayout,var bottomNav:BottomNavigationView) : Fragment() {

    companion object{
        var viewPriceDetailData = MutableLiveData(0f)
        var cartItemsSize = 0
        var selectedAddress:Address? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("On Create Cart Frag")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_cart, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.cartList)
        var fileDir = File(requireContext().filesDir,"AppImages")
        val db = AppDatabase.getAppDatabase(requireContext()).getUserDao()
        val deliveryAddressNotFound = view.findViewById<LinearLayout>(R.id.deliveryAddressLayoutNotFound)
        val deliveryAddressFound = view.findViewById<LinearLayout>(R.id.deliveryAddressLayout)
        val addressOwnerName = view.findViewById<TextView>(R.id.addressOwnerName)
        val address = view.findViewById<TextView>(R.id.address)
        val addNewAddress = view.findViewById<MaterialButton>(R.id.addNewAddressButton)
        val changeAddress = view.findViewById<MaterialButton>(R.id.changeAddressButton)
        val addMoreGrocery = view.findViewById<MaterialButton>(R.id.addMoreGroceryButton)
        val addressContactNumber = view.findViewById<TextView>(R.id.addressPhone)
        val bottomLayout = view.findViewById<LinearLayout>(R.id.linearLayout11)
        val price = view.findViewById<MaterialButton>(R.id.viewPriceDetailsButton)
        val priceDetails = view.findViewById<LinearLayout>(R.id.linearLayout12)
        val noOfItems = view.findViewById<TextView>(R.id.priceDetailsMrpTotalItems)
        val emptyCart = view.findViewById<ImageView>(R.id.emptyCartImage)
        val totalAmount =view.findViewById<TextView>(R.id.priceDetailsMrpPrice)
        val discountedAmount = view.findViewById<TextView>(R.id.priceDetailsDiscountedAmount)
        val grandTotalAmount = view.findViewById<TextView>(R.id.priceDetailsTotalAmount)
        addMoreGrocery.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentMainLayout,CategoryFragment(searchBarTop,bottomNav))
                .addToBackStack("Added More Groceries")
                .commit()
        }
        viewPriceDetailData.observe(viewLifecycleOwner){
            val str = "₹$it\nView Price Details"
            price.text = str
        }
        viewPriceDetailData.value = 0f
        Thread{
            val list = db.getCartItems(MainActivity.cartId)
            for(cart in list){
                MainActivity.handler.post {
                    viewPriceDetailData.value = viewPriceDetailData.value!!+((cart.totalItems) * (cart.unitPrice))
                }
            }
        }.start()

        Thread {
            val addressList = db.getAddressListForUser(MainActivity.userId.toInt())
            println("Address: $addressList")
            MainActivity.handler.post {
                if (addressList.isEmpty()) {
                    deliveryAddressNotFound.visibility = View.VISIBLE
                    deliveryAddressFound.visibility = View.GONE
                } else {
                    deliveryAddressFound.visibility = View.VISIBLE
                    deliveryAddressNotFound.visibility = View.GONE
                    if(selectedAddress==null){
                        selectedAddress = addressList[0]
                    }
                    addressOwnerName.text = selectedAddress?.addressContactName
                    val addressVal = "${selectedAddress?.buildingName}, ${selectedAddress?.streetName}, ${selectedAddress?.city}, ${selectedAddress?.state}\n${selectedAddress?.postalCode}"
                    address.text =addressVal
                    addressContactNumber.text = selectedAddress?.addressContactNumber
                }
            }
        }.start()

        addNewAddress.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentMainLayout,SavedAddress(searchBarTop))
                .addToBackStack("Add New Address")
                .commit()
        }

        changeAddress.setOnClickListener {
            val savedAddressFragment = SavedAddress(searchBarTop)
            savedAddressFragment.arguments = Bundle().apply {
                putBoolean("clickable",true)
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentMainLayout,savedAddressFragment)
                .addToBackStack("Add New Address")
                .commit()
        }
        MainActivity.handler.post{
            val adapter = ProductListAdapter(this,fileDir,searchBarTop,bottomNav,"C")
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            db.getProductsByCartIdLiveData(MainActivity.cartId).observe(viewLifecycleOwner){
                if(it.size==0){
                    recyclerView.visibility = View.GONE
                    priceDetails.visibility =View.GONE
                    bottomLayout.visibility =View.GONE
                    emptyCart.visibility = View.VISIBLE
                }
                else{
                    recyclerView.visibility = View.VISIBLE
                    priceDetails.visibility =View.VISIBLE
                    bottomLayout.visibility =View.VISIBLE
                    emptyCart.visibility = View.GONE
                    println("Adapter Called: $it")
                    adapter.setProducts(it)
                    val str = "MRP (${it.size}) Items"
                    var amt = 0f
                    for(i in it){
                        amt+=(i.price.toFloat())
                    }
                    var str1 = "₹$amt"
                    noOfItems.text =str
                    var totalPrice = "₹${viewPriceDetailData.value}"
                    totalAmount.text = totalPrice
                }
            }
        }
        return view
    }
}