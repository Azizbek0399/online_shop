package com.example.onlineshop.screen.cart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop.R
import com.example.onlineshop.model.ProductModel
import com.example.onlineshop.screen.MainViewModel
import com.example.onlineshop.screen.makeorder.MakeOrderActivity
import com.example.onlineshop.utils.Constants
import com.example.onlineshop.utils.PrefUtils
import com.example.onlineshop.view.CartAdapter
import kotlinx.android.synthetic.main.fragment_card.*
import java.io.Serializable

class CartFragment : Fragment() {
     lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.progress.observe(this, Observer {
            swipeCart.isRefreshing = it
        })
        viewModel.error.observe(this, Observer {
            Toast.makeText(requireActivity(),it,Toast.LENGTH_LONG).show()
        })
        viewModel.productsData.observe(this, Observer {
            recycler.adapter = CartAdapter(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.layoutManager = LinearLayoutManager(requireActivity())

        swipeCart.setOnRefreshListener {
            loadData()
        }
        btnMakeOrder.setOnClickListener {
            val intent = Intent(requireActivity(),MakeOrderActivity::class.java)
            intent.putExtra(Constants.EXTRA_DATA,(viewModel.productsData.value?: emptyList<ProductModel>()) as Serializable)
            startActivity(intent)

        }
        loadData()
    }
    fun loadData(){
        viewModel.getProductsByIds(PrefUtils.getCartList().map { it.product_id })
    }

    companion object {

        @JvmStatic
        fun newInstance() = CartFragment()
    }
}