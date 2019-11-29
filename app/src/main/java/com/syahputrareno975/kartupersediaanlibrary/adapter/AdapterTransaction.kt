package com.syahputrareno975.kartupersediaanlibrary.adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.syahputrareno975.kartupersediaanlibrary.R
import com.syahputrareno975.kartupersediaanmodul.model.transaction.Transaction
import java.text.DecimalFormat

class AdapterTransaction : RecyclerView.Adapter<AdapterTransaction.Holder> {

    lateinit var context: Context
    lateinit var transactions : ArrayList<Transaction>
    lateinit var onClick : (AdapterTransaction,Transaction,Int) -> Unit
    val formatter = DecimalFormat("##,###")

    constructor(context: Context, transactions: ArrayList<Transaction>, onClick: (AdapterTransaction,Transaction,Int) -> Unit) : super() {
        this.context = context
        this.transactions = transactions
        this.onClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return AdapterTransaction.Holder((context as Activity).layoutInflater.inflate(R.layout.adapter_transaction,parent,false))
    }

    override fun getItemCount(): Int {
        return  transactions.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = transactions.get(position)

        holder.des.text = item.Description
        holder.date.text = item.DateTransaction.toDateString()
        holder.total.text = "${formatter.format(item.total)}"
        holder.layout.setOnClickListener {
            onClick.invoke(this@AdapterTransaction,item.copyTransaction(),position)
        }
    }


    class Holder : RecyclerView.ViewHolder {
        lateinit var layout : LinearLayout
        lateinit var des : TextView
        lateinit var total : TextView
        lateinit var date : TextView

        constructor(itemView: View) : super(itemView) {
            this.layout = itemView.findViewById(R.id.layout_adapter_transaction)
            this.des = itemView.findViewById(R.id.description)
            this.total = itemView.findViewById(R.id.total)
            this.date = itemView.findViewById(R.id.date)
        }
    }
}