package com.katsidzira.crypto_app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.katsidzira.crypto_app.model.Crypto
import com.katsidzira.crypto_app.network.CryptoService
import com.katsidzira.crypto_app.view.CryptoAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), CryptoAdapter.Listener {
    val BASE_URL: String = "https://api.nomics.com/v1/"
    var compositeDisposable: CompositeDisposable? = null
    var cryptoList: List<Crypto>? = null
    val TAG: String = "main activity"
    var adapter: CryptoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        compositeDisposable = CompositeDisposable()
        loadData()
        initRecyclerView()

    }

    fun initRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager
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
        adapter = CryptoAdapter(cryptoList!!, this)
        recycler_view.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }

    override fun onItemClick(crypto: Crypto) {
        Toast.makeText(this, "You clicked: ${crypto.currency}", Toast.LENGTH_LONG).show()    }
}
