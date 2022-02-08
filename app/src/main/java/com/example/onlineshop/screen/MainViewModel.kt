package com.example.onlineshop.screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlineshop.db.AppDatabase
import com.example.onlineshop.model.CategoryModel
import com.example.onlineshop.model.OfferModel
import com.example.onlineshop.model.ProductModel
import com.example.onlineshop.repository.ShopRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    val repository = ShopRepository()

    val error = MutableLiveData<String>()

    var progress = MutableLiveData<Boolean>()

    val offersData = MutableLiveData<List<OfferModel>>()

    val categoriesData = MutableLiveData<List<CategoryModel>>()

    val productsData = MutableLiveData<List<ProductModel>>()

    fun getOffers(){
        repository.getOffer(error,progress, offersData)
    }
    fun getCategories(){
        repository.getCategories(error,categoriesData)
    }
    fun getTopProducts()
    {
        repository.getTopProducts(error,productsData)
    }
    fun getProductsByCategory(id:Int){
        repository.getProductsByCategory(id,error,productsData)
    }
    fun getProductsByIds(ids:List<Int>){
        repository.getProductsByIds(ids,error, progress, productsData)
    }
    fun insertAllProducts2DB(items:List<ProductModel>){
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getDatabase().getProductDao().deleteAll()
            AppDatabase.getDatabase().getProductDao().insertAll(items)

        }

    }
    fun insertAllCategories2DB(items:List<CategoryModel>){
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getDatabase().getCategoryDao().deleteAll()
            AppDatabase.getDatabase().getCategoryDao().insertAll(items)

        }

    }
    fun getAllDBProducts(){
        CoroutineScope(Dispatchers.Main).launch{
            productsData.value = withContext(Dispatchers.IO) {
                AppDatabase.getDatabase().getProductDao().getAllProducts()
            }
        }

    }
    fun getAllDBCategories(){
        CoroutineScope(Dispatchers.Main).launch{
            categoriesData.value = withContext(Dispatchers.IO){
                AppDatabase.getDatabase().getCategoryDao().getAllCategories()
            }
        }

    }

}



