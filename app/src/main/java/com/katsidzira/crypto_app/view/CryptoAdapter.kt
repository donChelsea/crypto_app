package com.katsidzira.crypto_app.view

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.katsidzira.crypto_app.R
import com.katsidzira.crypto_app.model.Crypto
import kotlinx.android.synthetic.main.crypto_item_view.view.*

class CryptoAdapter (val cryptoList: ArrayList<Crypto>, val listener: Listener) :
    RecyclerView.Adapter<CryptoAdapter.ViewHolder>() {
    val colors : Array<String> = arrayOf("#7E57C2", "#42A5F5", "#26C6DA", "#66BB6A", "#FFEE58", "#FF7043" , "#EC407A" , "#d32f2f")

    interface Listener {
        fun onItemClick(crypto: Crypto)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.crypto_item_view, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.onBind(cryptoList[p1], listener, colors, p1)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun onBind(crypto: Crypto, listener: Listener, colors: Array<String>, position: Int) {
            itemView.setOnClickListener { listener.onItemClick(crypto) }
            itemView.setBackgroundColor(Color.parseColor(colors[position%8]))
            itemView.text_name.text = crypto.currency
            itemView.text_price.text = crypto.price

        }

    }

}

