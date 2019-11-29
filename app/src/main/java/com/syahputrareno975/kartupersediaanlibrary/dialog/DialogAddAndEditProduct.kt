package com.syahputrareno975.kartupersediaanlibrary.dialog

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.syahputrareno975.kartupersediaanlibrary.R
import com.syahputrareno975.kartupersediaanmodul.model.product.Product
import java.util.*

class DialogAddAndEditProduct {
    lateinit var context: Context
    lateinit var onAdd : (Product) -> Unit
    lateinit var editProduct : Product
    var isEdit : Boolean = false

    constructor(context: Context, onAdd: (Product) -> Unit) {
        this.context = context
        this.onAdd = onAdd
        this.editProduct = Product()
        this.editProduct.Id = UUID.randomUUID().toString();
    }

    constructor(context: Context, editProduct: Product, onAdd: (Product) -> Unit) {
        this.context = context
        this.onAdd = onAdd
        this.editProduct = editProduct
        this.isEdit = true
    }


    fun dialog(){
        val v = (context as Activity).layoutInflater.inflate(R.layout.dialog_add_product,null)

        val dialog = AlertDialog.Builder(context)
            .create()

        val name :EditText= v.findViewById(R.id.product_name)
        name.setText(if (isEdit) editProduct.Name else "")
        val price :EditText = v.findViewById(R.id.product_price)
        price.setText(if (isEdit) editProduct.Price.toString() else "")
        val unit :EditText = v.findViewById(R.id.product_unit)
        unit.setText(if (isEdit) editProduct.Unit else "")

        val add :Button = v.findViewById(R.id.add_product_button)
        add.setText(if (isEdit) "Edit" else "Add")
        add.setOnClickListener {
            if (name.text.toString().isEmpty() || price.text.toString().isEmpty() || unit.text.toString().isEmpty()){
                Toast.makeText(context,"please fill all form",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            editProduct.Name = name.text.toString()
            editProduct.Price = price.text.toString().toDouble()
            editProduct.Unit = unit.text.toString()
            onAdd.invoke(editProduct)

            dialog.dismiss()
        }

        val cancel :Button = v.findViewById(R.id.cancel_add_product_button)
        cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setView(v)
        dialog.setCancelable(false)
        dialog.show()
    }
}