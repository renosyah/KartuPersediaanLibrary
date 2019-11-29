package com.syahputrareno975.kartupersediaanlibrary.dialog

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syahputrareno975.kartupersediaanlibrary.R
import com.syahputrareno975.kartupersediaanlibrary.adapter.AdapterProduct
import com.syahputrareno975.kartupersediaanmodul.model.product.Product

class DialogListProduct {
    lateinit var context: Context
    lateinit var products : ArrayList<Product>
    lateinit var onClick : (ArrayList<Product>) -> Unit

    constructor(context: Context, Products: ArrayList<Product>, onClick: (ArrayList<Product>) -> Unit) {
        this.context = context
        this.products = Products
        this.onClick = onClick
    }

    fun dialog(){

        val v = (context as Activity).layoutInflater.inflate(R.layout.dialog_product_list,null)
        val dialog = AlertDialog.Builder(context)
            .setNeutralButton("All Product") { dialog, which ->
                onClick.invoke(products)
                dialog.dismiss()
            }
            .setPositiveButton("Close") { dialog, which ->
                dialog.dismiss()
            }
            .create()

        val list : RecyclerView = v.findViewById(R.id.list_product)

        list.adapter = AdapterProduct(context,products) { adapterProduct, product, i ->
            val p = ArrayList<Product>()
            p.add(product)
            onClick.invoke(p)
            dialog.dismiss()
        }
        list.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        dialog.setView(v)
        dialog.setCancelable(false)
        dialog.show()
    }
}