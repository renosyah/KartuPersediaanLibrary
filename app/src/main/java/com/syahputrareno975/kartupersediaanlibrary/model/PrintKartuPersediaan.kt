package com.syahputrareno975.kartupersediaanlibrary.model

import com.example.renosyahputra.invoicemakerlib.transaction_model.TransactionDataInterface
import com.syahputrareno975.kartupersediaanmodul.model.stockCard.StockCard
import com.syahputrareno975.kartupersediaanmodul.model.stockCard.StockCardDetail
import com.syahputrareno975.kartupersediaanmodul.model.transaction.Transaction.TRANSACTION_PRODUCT_IN
import java.text.DecimalFormat

class PrintKartuPersediaan(val items : ArrayList<StockCard>) : TransactionDataInterface {

    val formatter = DecimalFormat("##,###")

    override fun toHTML(): String {

        var body = ""
        for (i in items){
            body += "<br />" +
                    "<table>\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table>\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "Product Name : ${i.ProductData.Name}\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<br />" +
                    "<div style='text-align:center;font-size:8px'>" +
                    "<table border=\"1\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td rowspan=\"2\">\n" +
                    "Date\n" +
                    "</td>\n" +
                    "<td rowspan=\"2\">\n" +
                    "Description\n" +
                    "</td>\n" +
                    "<td colspan=\"3\">\n" +
                    "Product In\n" +
                    "</td>\n" +
                    "<td colspan=\"3\">\n" +
                    "Product Out\n" +
                    "</td>\n" +
                    "<td rowspan=\"2\">\n" +
                    "Quantity\n" +
                    "</td>\n" +
                    "<td rowspan=\"2\">\n" +
                    "Price\n" +
                    "</td>\n" +
                    "<td rowspan=\"2\">\n" +
                    "Total\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "Quantity\n" +
                    "</td>\n" +
                    "<td>\n" +
                    "Price\n" +
                    "</td>\n" +
                    "<td>\n" +
                    "Total\n" +
                    "</td>\n" +
                    "<td>\n" +
                    "Quantity\n" +
                    "</td>\n" +
                    "<td>\n" +
                    "Price\n" +
                    "</td>\n" +
                    "<td>\n" +
                    "Total\n" +
                    "</td>\n" +
                    "</tr>\n"

                    for (d in i.StockCardProducts){
                        body += "<tr>\n" +
                                "<td>\n" +
                                "${d.DateTransaction.toOnlyDateString()}" +
                                "</td>\n" +
                                "<td>\n" +
                                "${d.Description}" +
                                "</td>\n" +
                                "<td>\n" +
                                "${concateLoopQty(d.ProductIn)}" +
                                "</td>\n" +
                                "<td>\n" +
                                "${concateLoopPrice(d.ProductIn)}" +
                                "</td>\n" +
                                "<td>\n" +
                                "${concateLoopTotal(d.ProductIn)}" +
                                "</td>\n" +
                                "<td>\n" +
                                "${concateLoopQty(d.ProductOut)}"  +
                                "</td>\n" +
                                "<td>\n" +
                                "${concateLoopPrice(d.ProductOut)}" +
                                "</td>\n" +
                                "<td>\n" +
                                "${concateLoopTotal(d.ProductOut)}"  +
                                "</td>\n" +
                                "<td>\n" +
                                "${concateLoopQty(d.ProductStock)}" +
                                "</td>\n" +
                                "<td>\n" +
                                "${concateLoopPrice(d.ProductStock)}" +
                                "</td>\n" +
                                "<td>\n" +
                                "${concateLoopTotal(d.ProductStock)}" +
                                "</td>\n" +
                                "</tr>\n"
                    }
                    body += "</tbody>\n" +
                            "</table>\n" +
                            "</td>\n" +
                            "</tr>\n" +
                            "</tbody>\n" +
                            "</table>\n" +
                            "</div>"
        }

        return "<html>\n" +
                "<head></head>\n" +
                "<body>${body}</body>\n" +
                "</html>"
    }

    fun concateLoopQty(a : ArrayList<StockCardDetail>) : String {
        var s = ""
        for (i in a){
            s += if (i.Quantity == 0) "" else "${i.Quantity}<br>"
        }
        return s
    }

    fun concateLoopPrice(a : ArrayList<StockCardDetail>) : String {
        var s = ""
        for (i in a){
            s += if (i.Price == 0.0) "" else "${formatter.format(i.Price)}<br>"
        }
        return s
    }

    fun concateLoopTotal(a : ArrayList<StockCardDetail>) : String {
        var s = ""
        for (i in a){
            s += if (i.Total == 0.0) "" else "${formatter.format(i.Total)}<br>"
        }
        return s
    }
}