package com.example.onlineshop.screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import androidx.core.view.GravityCompat

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.onlineshop.R
import com.example.onlineshop.screen.cart.CartFragment
import com.example.onlineshop.screen.changejanguage.ChangeLanguageFragment
import com.example.onlineshop.screen.favorite.FavoriteFragment
import com.example.onlineshop.screen.home.HomeFragment
import com.example.onlineshop.screen.profile.ProfileFragment
import com.example.onlineshop.utils.LocaleManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val homeFragment = HomeFragment.newInstance()
    val favoriteFragment = FavoriteFragment.newInstance()
    val cartFragment = CartFragment.newInstance()
    val profileFragment = ProfileFragment.newInstance()
    var activeFragment: Fragment = homeFragment
//comment qo'shildi
    lateinit var viewModel: MainViewModel
    //commentt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = MainViewModel()

        viewModel.productsData.observe(this, Observer {
            viewModel.insertAllProducts2DB(it)
        })
        viewModel.categoriesData.observe(this, Observer {
            viewModel.insertAllCategories2DB(it)

        })
        viewModel.error.observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_LONG).show()
        })

        imgMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        nav_view.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.settings -> {
                    val fragment = ChangeLanguageFragment.newInstance()
                    fragment.show(supportFragmentManager,fragment.tag)
                }
                R.id.nav_home -> {
                    Toast.makeText(this,"Cicked",Toast.LENGTH_SHORT).show()
                } R.id.nav_sync -> {
                    Toast.makeText(this,"Cicked",Toast.LENGTH_SHORT).show()
                } R.id.nav_delete -> {
                    Toast.makeText(this,"Cicked",Toast.LENGTH_SHORT).show()
                }R.id.nav_share -> {
                    Toast.makeText(this,"Cicked",Toast.LENGTH_SHORT).show()
                } R.id.nav_call -> {
                    val number = "+998993267008"
                   val dialIntent = Intent(Intent.ACTION_DIAL)
                   dialIntent.data = Uri.parse("tel: ${number}")
                   startActivity(dialIntent)
                }R.id.nav_logout -> {
                    finish()
                }
            }
            return@setNavigationItemSelectedListener true
        }
        // comment qo'shildi

        supportFragmentManager.beginTransaction().add(R.id.flContainer,homeFragment, homeFragment.tag).hide(homeFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.flContainer,favoriteFragment, favoriteFragment.tag).hide(favoriteFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.flContainer,cartFragment, cartFragment.tag).hide(cartFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.flContainer,profileFragment, profileFragment.tag).hide(profileFragment).commit()

        supportFragmentManager.beginTransaction().show(activeFragment).commit()

        bottomNavigationView.setOnNavigationItemSelectedListener {
            if (it.itemId == R.id.actionHome){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit()
                activeFragment = homeFragment
            }else if (it.itemId == R.id.actionFavorite){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(favoriteFragment).commit()
                activeFragment = favoriteFragment
            }else if (it.itemId == R.id.actionCart){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(cartFragment).commit()
                activeFragment = cartFragment
            }else if (it.itemId == R.id.actionProfile){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(profileFragment).commit()
                activeFragment = profileFragment
            }

            return@setOnNavigationItemSelectedListener true
        }
//        imgMenu.setOnClickListener {
//            val fragment = ChangeLanguageFragment.newInstance()
//            fragment.show(supportFragmentManager,fragment.tag)
//        }
        loadData()
    }
    fun loadData(){
        viewModel.getTopProducts()
        viewModel.getCategories()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }
}