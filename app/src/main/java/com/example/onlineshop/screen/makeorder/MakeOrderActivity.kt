package com.example.onlineshop.screen.makeorder

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.onlineshop.MapsActivity
import com.example.onlineshop.R
import com.example.onlineshop.model.ProductModel
import com.example.onlineshop.model.request.AddressModel
import com.example.onlineshop.screen.cart.CartFragment
import com.example.onlineshop.utils.Constants
import com.example.onlineshop.utils.LocaleManager
import kotlinx.android.synthetic.main.activity_make_order.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MakeOrderActivity : AppCompatActivity() {
    var address: AddressModel? = null
    lateinit var items:List<ProductModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_order)
        items = intent.getSerializableExtra(Constants.EXTRA_DATA) as List<ProductModel>
        cardviewBack.setOnClickListener {
            finish()
        }

        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
        tvTotalAmount.setText(items.sumByDouble { it.cartCount.toDouble() * (it.price.replace(" ","").toDoubleOrNull()?:0.0) }.toString())
        edAddress.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }
    @Subscribe
    fun onEvent(address: AddressModel){
        this.address = address
        edAddress.setText("${address.latitude}, ${address.longitude}")
    }
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }
}