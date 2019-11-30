package com.syahputrareno975.kartupersediaanlibrary.model

import com.example.renosyahputra.invoicemakerlib.transaction_model.TransactionDataInterface
import com.syahputrareno975.kartupersediaanmodul.model.stockCard.StockCard
import com.syahputrareno975.kartupersediaanmodul.model.stockCard.StockCardDetail
import com.syahputrareno975.kartupersediaanmodul.model.transaction.Transaction.TRANSACTION_PRODUCT_IN
import com.syahputrareno975.kartupersediaanmodul.util.ToHtml
import java.text.DecimalFormat

class PrintKartuPersediaan(val items : ArrayList<StockCard>) : TransactionDataInterface {
    override fun toHTML(): String {
        return ToHtml.toHtml(items)
    }
}