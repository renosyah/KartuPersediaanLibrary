package com.syahputrareno975.kartupersediaanlibrary.adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.syahputrareno975.kartupersediaanlibrary.R
import com.syahputrareno975.kartupersediaanmodul.model.stockCard.StockCard

class AdapterStockCard : RecyclerView.Adapter<AdapterStockCard.Holder> {

    lateinit var context: Context
    lateinit var stockCards : ArrayList<StockCard>
    lateinit var onClick : (AdapterStockCard,StockCard,Int) -> Unit

    constructor(context: Context, stockCards: ArrayList<StockCard>, onClick: (AdapterStockCard, StockCard, Int) -> Unit) : super() {
        this.context = context
        this.stockCards = stockCards
        this.onClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return AdapterStockCard.Holder((context as Activity).layoutInflater.inflate(R.layout.adapter_stock_card,parent,false))
    }

    override fun getItemCount(): Int {
        return  stockCards.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = stockCards.get(position)

        holder.name.text = "${item.ProductData.Name}"
        holder.layoutStockCard.setOnClickListener {
            onClick.invoke(this@AdapterStockCard,item.copyStockCard(),position)
        }
    }

    class Holder : RecyclerView.ViewHolder {
        lateinit var layoutStockCard : LinearLayout
        lateinit var name : TextView

        constructor(itemView: View) : super(itemView) {
            this.layoutStockCard = itemView.findViewById(R.id.layout_adapter_stock_card)
            this.name = itemView.findViewById(R.id.product_name)
        }
    }
}