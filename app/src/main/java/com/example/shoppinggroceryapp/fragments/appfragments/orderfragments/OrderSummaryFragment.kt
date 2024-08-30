package com.example.shoppinggroceryapp.fragments.appfragments.orderfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.appfragments.CartFragment
import com.example.shoppinggroceryapp.fragments.appfragments.accountfragments.SavedAddress
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton


class OrderSummaryFragment(var searchBarTop:LinearLayout,var bottomNav:BottomNavigationView) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_order_summary, container, false)
        val addressOwnerName = view.findViewById<TextView>(R.id.addressOwnerNameOrderSummary)
        val addressValue = view.findViewById<TextView>(R.id.addressOrderSummary)
        val addressNumber = view.findViewById<TextView>(R.id.addressPhoneOrderSummary)
        val changeAddressButton = view.findViewById<MaterialButton>(R.id.changeAddressButtonOrderSummary)
        val noOfItems = view.findViewById<TextView>(R.id.priceDetailsMrpTotalItemsOrderSummary)
        val mrpPrice = view.findViewById<TextView>(R.id.priceDetailsMrpPriceOrderSummary)
        val totalAmount = view.findViewById<TextView>(R.id.priceDetailsTotalAmountOrderSummary)
        val continueToPayment = view.findViewById<MaterialButton>(R.id.continueButtonOrderSummary)
        val viewProductDetails = view.findViewById<MaterialButton>(R.id.viewPriceDetailsButtonOrderSummary)
        val orderSummaryToolBar = view.findViewById<MaterialToolbar>(R.id.orderSummaryToolbar)

        val addressVal = "${CartFragment.selectedAddress?.buildingName}, ${CartFragment.selectedAddress?.streetName}, ${CartFragment.selectedAddress?.city}, ${CartFragment.selectedAddress?.state}, ${CartFragment.selectedAddress?.postalCode}"
        addressOwnerName.text = CartFragment.selectedAddress?.addressContactName
        addressValue.text = addressVal
        addressNumber.text = CartFragment.selectedAddress?.addressContactNumber

        val items = "MRP (${CartFragment.cartItemsSize} Items)"
        noOfItems.text = items
        val price = "₹${CartFragment.viewPriceDetailData.value}"
        mrpPrice.text =price
        val viewPriceDetailsText ="$price\nView Price Details"
        viewProductDetails.text = viewPriceDetailsText
        totalAmount.text = price
        changeAddressButton.setOnClickListener {
            val savedAddress = SavedAddress(searchBarTop)
            savedAddress.arguments = Bundle().apply {
                putBoolean("clickable",true)
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentMainLayout,savedAddress)
                .addToBackStack("Get the address from saved address")
                .commit()
        }



        orderSummaryToolBar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        continueToPayment.setOnClickListener {

        }
        return view
    }

    override fun onResume() {
        super.onResume()
        searchBarTop.visibility =View.GONE
        bottomNav.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        searchBarTop.visibility = View.VISIBLE
        bottomNav.visibility = View.VISIBLE
    }
}