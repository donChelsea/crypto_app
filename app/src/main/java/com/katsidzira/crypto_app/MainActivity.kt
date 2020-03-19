package com.katsidzira.crypto_app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.katsidzira.crypto_app.model.Crypto
import com.katsidzira.crypto_app.network.CryptoService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    val BASE_URL: String = "https://api.nomics.com/v1/"
    var compositeDisposable: CompositeDisposable? = null
    var cryptoList: List<Crypto>? = null
    val TAG: String = "main activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        compositeDisposable = CompositeDisposable()
        loadData()

    }

    fun loadData() {
        val cryptoService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoService::class.java)

        compositeDisposable?.add(cryptoService.getCryptoData()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({response -> onResponse(response)}, {t -> onFailure(t)}))

    }

    fun onFailure(t: Throwable): String? {
        return t.message
    }

    fun onResponse(cryptoDisposable: List<Crypto>) {
        cryptoList = ArrayList(cryptoDisposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}
