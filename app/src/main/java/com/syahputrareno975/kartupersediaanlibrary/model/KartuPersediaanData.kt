package com.syahputrareno975.kartupersediaanlibrary.model

import com.syahputrareno975.kartupersediaanmodul.model.product.Product
import com.syahputrareno975.kartupersediaanmodul.model.transaction.Transaction
import java.io.Serializable

class KartuPersediaanData :Serializable {
    var Transactions : ArrayList<Transaction> = ArrayList()
    var Products : ArrayList<Product> = ArrayList()

    companion object {
        val KARTU_PERSEDIAAN_FILENAME = "KARTU_PERSEDIAAN_FILENAME.data"
    }
}