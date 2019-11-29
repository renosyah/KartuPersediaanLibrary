package com.syahputrareno975.kartupersediaanlibrary

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.renosyahputra.invoicemakerlib.invoice_maker.InvoiceMakerInit
import com.example.renosyahputra.pdfviewerlibrary.PdfViewer
import com.syahputrareno975.kartupersediaanlibrary.adapter.AdapterProduct
import com.syahputrareno975.kartupersediaanlibrary.adapter.AdapterStockCard
import com.syahputrareno975.kartupersediaanlibrary.adapter.AdapterTransaction
import com.syahputrareno975.kartupersediaanlibrary.dialog.DialogAddAndEditProduct
import com.syahputrareno975.kartupersediaanlibrary.dialog.DialogAddAndEditTransaction
import com.syahputrareno975.kartupersediaanlibrary.model.KartuPersediaanData
import com.syahputrareno975.kartupersediaanlibrary.model.KartuPersediaanData.Companion.KARTU_PERSEDIAAN_FILENAME
import com.syahputrareno975.kartupersediaanlibrary.model.PrintKartuPersediaan
import com.syahputrareno975.kartupersediaanmodul.KartuPersediaanInit
import com.syahputrareno975.kartupersediaanmodul.function.SortTransaction
import com.syahputrareno975.kartupersediaanmodul.interfaces.OnKartuPersediaanInitCallback
import com.syahputrareno975.kartupersediaanmodul.model.MethodStockCard.*
import com.syahputrareno975.kartupersediaanmodul.model.product.Product
import com.syahputrareno975.kartupersediaanmodul.model.stockCard.StockCard
import com.syahputrareno975.kartupersediaanmodul.util.SerializableSave
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {

    lateinit var context : Context
    var stockCards = ArrayList<StockCard>()
    var kartuPersediaanData  : KartuPersediaanData = KartuPersediaanData()
    var productFilter = ArrayList<Product>()
    var currentLayout = LAYOUT_STOCK_CARD
    var currentMethod = METHOD_FIFO

    companion object {
        val LAYOUT_STOCK_CARD  = 0
        val LAYOUT_TRANSACTION  = 1
        val LAYOUT_PRODUCT  = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWidget()
    }

    fun initWidget(){
        context = this@MainActivity

        refresh_main_list.setOnRefreshListener(onListRefresh)
        if (SerializableSave(context,KARTU_PERSEDIAAN_FILENAME).load() != null){
            this.kartuPersediaanData = SerializableSave(context,KARTU_PERSEDIAAN_FILENAME).load() as KartuPersediaanData
        }

        productFilter.addAll(kartuPersediaanData.Products)

        text_filter_product.setOnClickListener(onStockCardFilterClick)
        text_filter_date.setOnClickListener(onStockCardFilterClick)
        text_method.setOnClickListener(onStockCardFilterClick)
        text_print.setOnClickListener(onStockCardFilterClick)

        stock_card_report.setOnClickListener(onBottomMenuClick)
        transaction_report.setOnClickListener(onBottomMenuClick)
        product_report.setOnClickListener(onBottomMenuClick)


        add_button.setOnClickListener(onAddButtonClick)

        setListAdapterStockCard()

    }

    val onListRefresh = object : SwipeRefreshLayout.OnRefreshListener {
        override fun onRefresh() {
            refresh_main_list.isRefreshing = !refresh_main_list.isRefreshing
            // refresh layout
            when (currentLayout){
                LAYOUT_STOCK_CARD -> {
                    setListAdapterStockCard()
                }
                LAYOUT_TRANSACTION -> {
                    kartuPersediaanData.Transactions = SortTransaction.sortByDateASC(kartuPersediaanData.Transactions)
                    setListAdapterTransaction()
                }
                LAYOUT_PRODUCT -> {
                    setListAdapterProduct()
                }
            }
        }
    }

    val onBottomMenuClick = object : View.OnClickListener {
        override fun onClick(v: View?) {

            filter_layout.visibility = View.GONE
            setListAdapterEmpty()

            when (v){
                stock_card_report -> {
                    filter_layout.visibility = View.VISIBLE

                    setListAdapterStockCard()
                    title_text.text = "Stock Card"
                    currentLayout = LAYOUT_STOCK_CARD
                }
                transaction_report -> {


                    setListAdapterTransaction()
                    title_text.text = "Transaction"
                    currentLayout = LAYOUT_TRANSACTION
                }
                product_report -> {

                    setListAdapterProduct()
                    title_text.text = "Product"
                    currentLayout = LAYOUT_PRODUCT
                }
            }
        }
    }

    val onStockCardFilterClick = object : View.OnClickListener {
        override fun onClick(v: View?) {

            // filter stock card
            when (v) {
                text_filter_product -> {

                }
                text_filter_date -> {

                }
                text_method -> {

                    AlertDialog.Builder(context)
                        .setTitle("Set Method")
                        .setItems(arrayOf<CharSequence>("FIFO","LIFO","AVERAGE")) { dialog, which ->
                            when (which){
                                METHOD_FIFO -> {
                                    currentMethod = METHOD_FIFO
                                    text_method.text = "FIFO"
                                }
                                METHOD_LIFO -> {
                                    currentMethod = METHOD_LIFO
                                    text_method.text = "LIFO"
                                }
                                METHOD_AVG -> {
                                    currentMethod = METHOD_AVG
                                    text_method.text = "AVERAGE"
                                }
                            }
                            setListAdapterStockCard()
                            dialog.dismiss()
                        }.setNegativeButton("Close") { dialog, which ->
                            dialog.dismiss()
                        }
                        .setCancelable(false)
                        .create()
                        .show()

                }
                text_print -> {

                    InvoiceMakerInit.newInstance()
                        .setContext(context)
                        .setFolderTarget("kartu_persediaan_2")
                        .setTransactionModel(PrintKartuPersediaan(stockCards))
                        .setOnInvoiceMakerInitListener(object : InvoiceMakerInit.OnInvoiceMakerInitListener{
                            override fun onInvoiceCreated(file: File) {

                                AlertDialog.Builder(context)
                                    .setTitle("Report Created")
                                    .setMessage("Stock Card Report hass been created, would you like to see it?")
                                    .setPositiveButton("Open"){dialog, which ->

                                        PdfViewer.newInstance()
                                            .setContext(context)
                                            .setPdfFile(file)
                                            .setOnPdfVewerListener(object : PdfViewer.OnPdfVewerListener {
                                                override fun onFinishView() {
                                                    // pdf viewer telah selesai
                                                }
                                            }).viewPDF()

                                    }
                                    .setNegativeButton("No"){dialog, which ->

                                    }.create()
                                    .show()
                            }
                            override fun onException(e: java.lang.Exception) {
                                // exception yg didapat pada saat membuat file
                            }
                        })
                        .setOnInvoiceMakerRequestPermissionListener(object : InvoiceMakerInit.OnInvoiceMakerRequestPermissionListener{
                            override fun onPermissionResult(isGranted: Boolean) {
                                // check apakan permission di setujui
                            }
                        }).makePDF("Laporan.pdf")
                }
            }
        }
    }

    val onAddButtonClick = object : View.OnClickListener {
        override fun onClick(v: View?) {

            AlertDialog.Builder(context)
                .setTitle("Add New Data")
                .setItems(arrayOf<CharSequence>("Add Transaction","Add Product")) { dialog, which ->
                    when (which){
                        0 -> {

                            DialogAddAndEditTransaction(context,kartuPersediaanData.Products) {

                                kartuPersediaanData.Transactions.add(it)
                                setListAdapterTransaction()
                                filter_layout.visibility = View.GONE
                                title_text.text = "Transaction"
                                currentLayout = LAYOUT_TRANSACTION

                                SerializableSave(context,KartuPersediaanData.KARTU_PERSEDIAAN_FILENAME).save(kartuPersediaanData)

                            }.dialog()

                        }
                        1 -> {

                            DialogAddAndEditProduct(context) {

                                kartuPersediaanData.Products.add(it)
                                setListAdapterProduct()
                                filter_layout.visibility = View.GONE
                                title_text.text = "Product"
                                currentLayout = LAYOUT_PRODUCT

                                SerializableSave(context,KartuPersediaanData.KARTU_PERSEDIAAN_FILENAME).save(kartuPersediaanData)

                            }.dialog()

                        }
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .create()
                .show()
        }
    }

    fun setListAdapterEmpty(){
        val adapter = AdapterProduct(context,ArrayList()) {adapter,product,pos ->

        }
        main_list.adapter = adapter
        main_list.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
    }

    fun setListAdapterProduct(){
        val adapter = AdapterProduct(context,kartuPersediaanData.Products) {adapter,product,pos ->

            DialogAddAndEditProduct(context,product) {
                kartuPersediaanData.Products[pos] = it
                adapter.notifyDataSetChanged()
                SerializableSave(context,KartuPersediaanData.KARTU_PERSEDIAAN_FILENAME).save(kartuPersediaanData)
            }.dialog()

        }
        main_list.adapter = adapter
        main_list.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

    }

    fun setListAdapterTransaction(){
        val adapter = AdapterTransaction(context,kartuPersediaanData.Transactions) {adapter, transaction ,pos ->
            DialogAddAndEditTransaction(context,kartuPersediaanData.Products,transaction) {
                kartuPersediaanData.Transactions[pos] = it
                adapter.notifyDataSetChanged()
                SerializableSave(context,KartuPersediaanData.KARTU_PERSEDIAAN_FILENAME).save(kartuPersediaanData)
            }.dialog()
        }
        main_list.adapter = adapter
        main_list.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

    }

    fun setListAdapterStockCard(){

        KartuPersediaanInit.newInstance()
            .setMethod(currentMethod)
            .setProducts(productFilter)
            .setTransactions(kartuPersediaanData.Transactions)
            .setOnKartuPersediaanInitCallback(object : OnKartuPersediaanInitCallback {
                override fun onTransactionValidateResult(isValid: Boolean, flag: Int) {
                    Toast.makeText(context,if (isValid) "Valid" else "Invalid with Flag : $flag",Toast.LENGTH_SHORT).show()
                }

                override fun onStockCardResult(s: ArrayList<StockCard>) {
                    stockCards = s
                    val adapter = AdapterStockCard(context,stockCards) { adapter, stockCard, pos ->

                    }
                    main_list.adapter = adapter
                    main_list.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

                }

            }).makeStockCard()
    }
}
