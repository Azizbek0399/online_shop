package com.example.onlineshop.screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlineshop.db.AppDatabase
import com.example.onlineshop.model.*
import com.example.onlineshop.api.repository.ShopRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    val checkPhoneData = MutableLiveData<CheckPhoneResponse>()
    val repository = ShopRepository()

    val registrationData = MutableLiveData<Boolean>()
    val confirmData = MutableLiveData<LoginResponse>()
    val loginData = MutableLiveData<LoginResponse>()
    val error = MutableLiveData<String>()

    var progress = MutableLiveData<Boolean>()

    val offersData = MutableLiveData<List<OfferModel>>()

    val categoriesData = MutableLiveData<List<CategoryModel>>()

    val productsData = MutableLiveData<List<ProductModel>>()

    fun checkPhone(phone: String){
        repository.checkPhone(phone, error, progress, checkPhoneData)
    }

    fun registrationData(fullname: String, phone: String, password: String){
        repository.registration(fullname, phone, password, error, progress, registrationData)
    }

    fun login(phone: String, password: String){
        repository.login(phone, password, error, progress, loginData)
    }

    fun confirmUser(phone: String, code: String){
        repository.confirmUser(phone, code, error, progress, confirmData)
    }


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



