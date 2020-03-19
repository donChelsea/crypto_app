package com.katsidzira.crypto_app.network

import com.katsidzira.crypto_app.R
import com.katsidzira.crypto_app.model.Crypto
import io.reactivex.Observable
import retrofit2.http.GET

interface CryptoService {

    @GET("prices?key=5f5ce8e3b45ce8797c605b9813d20e44")
    fun getCryptoData() : Observable<List<Crypto>>
}
