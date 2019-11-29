package com.syahputrareno975.kartupersediaanlibrary.dialog

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syahputrareno975.kartupersediaanlibrary.R
import com.syahputrareno975.kartupersediaanlibrary.adapter.AdapterDetailTransaction
import com.syahputrareno975.kartupersediaanmodul.model.product.Product
import com.syahputrareno975.kartupersediaanmodul.model.transaction.Transaction
import com.syahputrareno975.kartupersediaanmodul.model.transaction.Transaction.TRANSACTION_PRODUCT_IN
import com.syahputrareno975.kartupersediaanmodul.model.transaction.TransactionDetail
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import java.util.*
import kotlin.collections.ArrayList

class DialogAddAndEditTransaction {
    lateinit var context: Context
    lateinit var products : ArrayList<Product>
    lateinit var onAdd : (Transaction) -> Unit
    lateinit var transaction : Transaction
    var isEdit : Boolean = false

    constructor(context: Context, products : ArrayList<Product>, onAdd: (Transaction) -> Unit) {
        this.context = context
        this.products = products
        this.onAdd = onAdd
        this.transaction  = Transaction()
        this.transaction.Id = UUID.randomUUID().toString()
    }

    constructor(context: Context,products : ArrayList<Product>, transaction: Transaction, onAdd: (Transaction) -> Unit) {
        this.context = context
        this.products = products
        this.onAdd = onAdd
        this.transaction = transaction
        this.isEdit = true
    }

    fun dialog(){

        val v = (context as Activity).layoutInflater.inflate(R.layout.dialog_add_transaction,null)

        val dialog = AlertDialog.Builder(context)
            .create()

        val time : TextView = v.findViewById(R.id.choose_time)
        time.text = if (isEdit) transaction.DateTransaction.toOnlyTimeString() else "Choose Time"
        time.setOnClickListener {

            val now = Calendar.getInstance()
            val dpl = TimePickerDialog.newInstance(TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute, second ->

                transaction.DateTransaction.Hour = hourOfDay
                transaction.DateTransaction.Minute = minute
                transaction.DateTransaction.Second = 0

                time.text = transaction.DateTransaction.toOnlyTimeString()
            },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
            )

            dpl.setVersion(TimePickerDialog.Version.VERSION_1)
            dpl.setAccentColor(context.resources.getColor(R.color.colorPrimary))
            dpl.show((context as Activity).fragmentManager,"Datepickerdialog")

        }

        val date : TextView = v.findViewById(R.id.choose_date)
        date.text = if (isEdit) transaction.DateTransaction.toOnlyDateString() else "Choose Date"
        date.setOnClickListener {

            val now = Calendar.getInstance()
            val dpl = DatePickerDialog.newInstance(
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    transaction.DateTransaction.Day = dayOfMonth
                    transaction.DateTransaction.Month = (monthOfYear + 1)
                    transaction.DateTransaction.Year = year

                    date.text = transaction.DateTransaction.toOnlyDateString()

                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpl.setVersion(DatePickerDialog.Version.VERSION_2)
            dpl.setAccentColor(context.resources.getColor(R.color.colorPrimary))
            dpl.show((context as Activity).fragmentManager,"Datepickerdialog")

        }


        val description : EditText = v.findViewById(R.id.description_form)
        description.setText(if (isEdit) transaction.Description else "")
        description.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                transaction.Description = if (description.text.toString().isEmpty()) "" else description.text.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        val flow : Button = v.findViewById(R.id.choose_flow)
        flow.text = if (isEdit) (if (transaction.Flow == TRANSACTION_PRODUCT_IN) "Product In" else "Product Out") else "Transaction Type"
        flow.setOnClickListener {

            AlertDialog.Builder(context)
                .setTitle("Transaction Type")
                .setItems(arrayOf<CharSequence>("Product In","Product Out")) { dialog, which ->
                    transaction.Flow = which
                    flow.text = (if (transaction.Flow == TRANSACTION_PRODUCT_IN) "Product In" else "Product Out")
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .create()
                .show()

        }

        val layoutDetail : LinearLayout = v.findViewById(R.id.layout_detail_transaction)
        layoutDetail.visibility = if (isEdit) View.VISIBLE else View.GONE

        val adapter = AdapterDetailTransaction(context,transaction.TransactionDetails) { adapter, detail, pos ->

            AlertDialog.Builder(context)
                .setTitle("Edit ${detail.ProductData.Name}")
                .setItems(arrayOf<CharSequence>("Edit Quantity","Edit Price")) { dialog, which ->
                    when (which){
                        0 -> {
                            DialogEditValue(context,"Edit Quantity",detail.Quantity.toString()){
                                transaction.TransactionDetails[pos].Quantity = it.toInt()
                                adapter.notifyDataSetChanged()
                            }.dialog()
                        }
                        1 -> {
                            DialogEditValue(context,"Edit Price",detail.Price.toString()){
                                transaction.TransactionDetails[pos].Price = it.toDouble()
                                adapter.notifyDataSetChanged()
                            }.dialog()
                        }
                    }
                    dialog.dismiss()
                }
                .setNeutralButton("Remove") { dialog, which ->
                    transaction.TransactionDetails.removeAt(pos)
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
                .setPositiveButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .create()
                .show()


        }
        val list : RecyclerView = v.findViewById(R.id.detail_list)
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        val addProduct : Button = v.findViewById(R.id.choose_add_product)
        addProduct.setOnClickListener {

            DialogListProduct(context,products) {
                for (i in it){
                    if (!isAlreadyAdded(transaction.TransactionDetails,i)) {
                        transaction.TransactionDetails.add(
                            TransactionDetail(
                                UUID.randomUUID().toString(),
                                i.copyProduct(),
                                i.Price,
                                1,
                                true
                            )
                        )
                    }
                }
                adapter.notifyDataSetChanged()
                layoutDetail.visibility =  View.VISIBLE

            }.dialog()

        }

        val add : Button = v.findViewById(R.id.add_transaction)
        add.text = if (isEdit) "Edit" else "Add"
        add.setOnClickListener {
            onAdd.invoke(transaction)
            dialog.dismiss()
        }

        val cancel : Button = v.findViewById(R.id.cancel_add_transaction)
        cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setView(v)
        dialog.setCancelable(false)
        dialog.show()
    }

    fun isAlreadyAdded(ps : ArrayList<TransactionDetail>,p : Product) : Boolean {
        var pos = 0
        var isAdded = false
        for (i in 0 until ps.size){
            if (ps.get(i).ProductData.Id == p.Id){
                isAdded = true
                pos = i
                break
            }
        }
        if (isAdded){
            ps.get(pos).Quantity += 1
        }

        return isAdded
    }
}