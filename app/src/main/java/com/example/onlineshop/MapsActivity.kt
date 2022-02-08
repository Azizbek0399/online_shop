package com.example.onlineshop

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.onlineshop.databinding.ActivityMapsBinding
import com.example.onlineshop.model.request.AddressModel
import com.example.onlineshop.utils.LocaleManager
import kotlinx.android.synthetic.main.activity_maps.*
import org.greenrobot.eventbus.EventBus

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        btnConfirm.setOnClickListener {
            val addressModel = AddressModel("",mMap.cameraPosition.target.latitude,mMap.cameraPosition.target.longitude)
            EventBus.getDefault().post(addressModel)
            finish()
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

    }
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }

}