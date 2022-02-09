package com.example.onlineshop.api.repository

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.example.onlineshop.api.NetworkManager
import com.example.onlineshop.model.*
import com.example.onlineshop.model.request.GetProductsByIdsRequest
import com.example.onlineshop.model.request.RegisterRequest
import com.example.onlineshop.utils.PrefUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


class ShopRepository {
   private val compositeDisposable = CompositeDisposable()

    fun checkPhone(phone: String, error: MutableLiveData<String>, progress: MutableLiveData<Boolean>, success: MutableLiveData<CheckPhoneResponse>){
        progress.value = true
        compositeDisposable.add(
            NetworkManager.getApiService().checkPhone(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<CheckPhoneResponse>>(){
                    override fun onComplete() {

                    }

                    override fun onNext(t: BaseResponse<CheckPhoneResponse>) {
                        progress.value = false
                        if (t.success){
                            success.value = t.data
                        }else{
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }
                })
        )
    }

    fun registration(fullname: String, phone: String, password: String, error: MutableLiveData<String>, progress: MutableLiveData<Boolean>, success: MutableLiveData<Boolean>){
        progress.value = true
        compositeDisposable.add(
            NetworkManager.getApiService().register(RegisterRequest(fullname, phone, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<Any>>(){
                    override fun onComplete() {

                    }

                    override fun onNext(t: BaseResponse<Any>) {
                        progress.value = false
                        if (t.success){
                            success.value = true
                        }else{
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }
                })
        )
    }

    fun confirmUser(phone: String, sms_code: String, error: MutableLiveData<String>, progress: MutableLiveData<Boolean>, success: MutableLiveData<LoginResponse>){
        progress.value = true
        compositeDisposable.add(
            NetworkManager.getApiService().confirm(phone, sms_code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<LoginResponse>>(){
                    override fun onComplete() {

                    }

                    override fun onNext(t: BaseResponse<LoginResponse>) {
                        progress.value = false
                        if (t.success){
                            success.value = t.data
                        }else{
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }
                })
        )
    }

    fun login(phone: String, password: String, error: MutableLiveData<String>, progress: MutableLiveData<Boolean>, success: MutableLiveData<LoginResponse>){
        progress.value = true
        compositeDisposable.add(
            NetworkManager.getApiService().login(phone, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<LoginResponse>>(){
                    override fun onComplete() {

                    }

                    override fun onNext(t: BaseResponse<LoginResponse>) {
                        progress.value = false
                        if (t.success){
                            success.value = t.data
                        }else{
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }
                })
        )
    }

    fun getOffer(
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<List<OfferModel>>
    ) {
        progress.value = true
        compositeDisposable.add(
            NetworkManager.getApiService().getOffers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<List<OfferModel>>>() {
                    override fun onNext(t: BaseResponse<List<OfferModel>>) {
                        progress.value = false
                        if (t.success){
                              success.value = t.data
                        }else{
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )

    }

    fun getCategories(
        error: MutableLiveData<String>,
        success: MutableLiveData<List<CategoryModel>>
    ) {
        compositeDisposable.add(
            NetworkManager.getApiService().getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<List<CategoryModel>>>() {
                    override fun onNext(t: BaseResponse<List<CategoryModel>>) {
                        if (t.success){
                            success.value = t.data
                        }else{
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )
    }

    @SuppressLint("CheckResult")
    fun getTopProducts(
        error: MutableLiveData<String>,
        success: MutableLiveData<List<ProductModel>>
    ) {
        NetworkManager.getApiService().getTopProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<BaseResponse<List<ProductModel>>>() {
                override fun onNext(t: BaseResponse<List<ProductModel>>) {
                    if (t.success) {
                        success.value = t.data
                    } else {
                        error.value = t.message
                    }
                }

                override fun onError(e: Throwable) {
                    error.value = e.localizedMessage
                }

                override fun onComplete() {

                }
            })


    }
    fun getProductsByCategory(id:Int,
        error: MutableLiveData<String>,
        success: MutableLiveData<List<ProductModel>>
    ) {
        compositeDisposable.add(
            NetworkManager.getApiService().getCategoryProducts(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<List<ProductModel>>>() {
                    override fun onNext(t: BaseResponse<List<ProductModel>>) {
                        if (t.success){
                            success.value = t.data
                        }else{
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )
    }
    fun getProductsByIds(ids:List<Int>,
                              error: MutableLiveData<String>,
                              progress: MutableLiveData<Boolean>,
                              success: MutableLiveData<List<ProductModel>>
    ) {
        progress.value = true
        compositeDisposable.add(
            NetworkManager.getApiService().getProductsByIds(GetProductsByIdsRequest(ids))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<List<ProductModel>>>() {
                    override fun onNext(t: BaseResponse<List<ProductModel>>) {
                        progress.value = false
                        if (t.success){
                            PrefUtils.getCartList().forEach { cartProduct ->
                           t.data.forEach {
                                    if (cartProduct.product_id == it.id) {
                                        it.cartCount = cartProduct.count
                                    }
                                }
                            }
                            success.value = t.data
                        }else{
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )
    }
}
