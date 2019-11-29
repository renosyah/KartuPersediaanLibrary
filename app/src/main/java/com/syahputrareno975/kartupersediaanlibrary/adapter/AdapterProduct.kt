package com.syahputrareno975.kartupersediaanlibrary.adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.syahputrareno975.kartupersediaanlibrary.R
import com.syahputrareno975.kartupersediaanmodul.model.product.Product
import kotlinx.android.synthetic.main.adapter_product.view.*
import java.text.DecimalFormat
import java.text.Format
import java.util.*
import kotlin.collections.ArrayList

class AdapterProduct : RecyclerView.Adapter<AdapterProduct.Holder>{

    lateinit var context: Context
    lateinit var products: ArrayList<Product>
    lateinit var onClick : (AdapterProduct,Product,Int) -> Unit
    val formatter = DecimalFormat("##,###")

    constructor(context: Context, products: ArrayList<Product>, onClick: (AdapterProduct,Product,Int) -> Unit) : super() {
        this.context = context
        this.products = products
        this.onClick = onClick
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder((context as Activity).layoutInflater.inflate(R.layout.adapter_product,parent,false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = products.get(position)
        holder.name.text = "${item.Name}"
        holder.price.text = "${formatter.format(item.Price)}"
        holder.qty.visibility = View.GONE
        holder.layoutAdapterProduct.setOnClickListener {
            onClick.invoke(this@AdapterProduct,item.copyProduct(),position)
        }
    }


    class Holder : RecyclerView.ViewHolder {
        lateinit var layoutAdapterProduct : LinearLayout
        lateinit var name : TextView
        lateinit var price : TextView
        lateinit var qty : TextView

        constructor(itemView: View) : super(itemView) {
            this.layoutAdapterProduct = itemView.findViewById(R.id.layout_adapter_product)
            this.name = itemView.findViewById(R.id.product_name)
            this.price =  itemView.findViewById(R.id.product_price)
            this.qty =  itemView.findViewById(R.id.product_qty)
        }
    }
}