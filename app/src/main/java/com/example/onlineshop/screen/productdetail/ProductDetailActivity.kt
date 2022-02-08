package com.example.onlineshop.screen.productdetail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.onlineshop.R
import com.example.onlineshop.model.ProductModel
import com.example.onlineshop.utils.Constants
import com.example.onlineshop.utils.LocaleManager
import com.example.onlineshop.utils.PrefUtils
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {
    lateinit var item: ProductModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

       cardviewBack.setOnClickListener {
           finish()
       }
        cardviewFavorite.setOnClickListener {
            PrefUtils.setFavorite(item)

            if (PrefUtils.checkFavorite(item)){
                imgFavorite.setImageResource(R.drawable.ic_baseline_favorite)
            }else{
                imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_border)
            }

        }

        item = intent.getSerializableExtra(Constants.EXTRA_DATA) as ProductModel

        tvTitle.text = item.name
        tvProductName.text = item.name
        tvProductPrice.text = item.price

        if (PrefUtils.getCartCount(item) > 0){
            btnAdd2cart.visibility = View.GONE
        }

        if (PrefUtils.checkFavorite(item)){
            imgFavorite.setImageResource(R.drawable.ic_baseline_favorite)
        }else{
            imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_border)
        }
        Glide.with(this).load(Constants.HOST_IMAGE + item.image).into(imgProduct)

        btnAdd2cart.setOnClickListener {
            item.cartCount = 1
            PrefUtils.setCart(item)
            Toast.makeText(this,"Product added to cart",Toast.LENGTH_LONG).show()
            finish()
        }
    }
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }
}