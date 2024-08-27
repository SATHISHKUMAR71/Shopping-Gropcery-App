package com.example.shoppinggroceryapp.fragments.appfragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ext.SdkExtensions
import android.provider.ContactsContract.CommonDataKinds.Im
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.appfragments.recyclerview.ProductListAdapter
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.entities.products.Images
import com.example.shoppinggroceryapp.model.entities.products.Product
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class ProductListFragment : Fragment() {

    companion object{
        var clickObserver = MutableLiveData<Product>()
        var position = 0
    }
    private lateinit var selectedProduct: Product
    private var productList:MutableList<Product> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_product_list, container, false)
        val productRV = view.findViewById<RecyclerView>(R.id.productListRecyclerView)
        val handler = Handler(Looper.getMainLooper())
        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && SdkExtensions.getExtensionVersion(
                Build.VERSION_CODES.R) >= 2) {
            Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        } else {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        }
        clickObserver.observe(viewLifecycleOwner){
            if(it!=null){
                selectedProduct = it
            }
        }

        val launchImageForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == Activity.RESULT_OK){
                val image = result.data?.data
                val hash = image.hashCode().toString()
                val bitMap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver,image)

                val url = storeImageInApp(requireContext(),bitMap,hash)
                Thread{
                    val p  = selectedProduct.copy(mainImage =url)
                    AppDatabase.getAppDatabase(context = requireContext()).getRetailerDao().updateProduct(
                        p
                    )
                    handler.post {
                        println("$position ${productRV.adapter}")
                        productList.removeAt(position)
                        productList.add(position,p)
                        productRV.adapter?.notifyItemChanged(position)
                    }
                    println("PRODUCT UPDATED: $p")
                }.start()
                println(image)
            }
        }
        Thread{
            productList = AppDatabase.getAppDatabase(requireContext()).getUserDao().getOnlyProducts()
            handler.post {
                productRV.adapter = ProductListAdapter(this,productList,launchImageForResult,intent)
                productRV.layoutManager = LinearLayoutManager(requireContext())
            }
        }.start()


        return view
    }

    private fun storeImageInApp(context: Context,bitMap:Bitmap,fileName:String): String {
        val fileDir = File(context.filesDir,"AppImages")
        if(!fileDir.exists()){
            fileDir.mkdirs()
        }
        val bitmapFile = File(fileDir,fileName)
        val fileOutputStream = FileOutputStream(bitmapFile)
        bitMap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream)
        return bitmapFile.absolutePath
    }
}