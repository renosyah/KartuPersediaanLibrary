package com.syahputrareno975.kartupersediaanlibrary.adapter

import android.app.Activity
import android.content.Context
import android.telecom.Call
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.syahputrareno975.kartupersediaanlibrary.R
import com.syahputrareno975.kartupersediaanmodul.model.transaction.TransactionDetail
import java.text.DecimalFormat

class AdapterDetailTransaction :RecyclerView.Adapter<AdapterProduct.Holder>{

    lateinit var context: Context
    lateinit var details : ArrayList<TransactionDetail>
    lateinit var onClick : (AdapterDetailTransaction,TransactionDetail,Int) -> Unit
    val formatter = DecimalFormat("##,###")

    constructor(context: Context, details: ArrayList<TransactionDetail>, onClick: (AdapterDetailTransaction, TransactionDetail, Int) -> Unit) : super() {
        this.context = context
        this.details = details
        this.onClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterProduct.Holder {
        return AdapterProduct.Holder((context as Activity).layoutInflater.inflate(R.layout.adapter_product,parent,false))
    }

    override fun getItemCount(): Int {
        return details.size
    }

    override fun onBindViewHolder(holder: AdapterProduct.Holder, position: Int) {
        val item = details.get(position)
        holder.name.text = "${item.ProductData.Name}"
        holder.price.text = "${formatter.format(item.Price)}"
        holder.qty.visibility = View.VISIBLE
        holder.qty.text = "${item.Quantity} ${item.ProductData.Unit}"
        holder.layoutAdapterProduct.setOnClickListener {
            onClick.invoke(this@AdapterDetailTransaction,item.cloneTransactionDetail(),position)
        }
    }
}