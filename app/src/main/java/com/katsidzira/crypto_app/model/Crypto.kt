package com.katsidzira.crypto_app.model

import com.google.gson.annotations.SerializedName

data class Crypto(@SerializedName("currency") val currency: String,
                  val price: String
)
