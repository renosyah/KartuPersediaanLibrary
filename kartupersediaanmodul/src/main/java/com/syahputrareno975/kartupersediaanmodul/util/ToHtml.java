package com.syahputrareno975.kartupersediaanmodul.util;

import com.syahputrareno975.kartupersediaanmodul.model.stockCard.StockCard;
import com.syahputrareno975.kartupersediaanmodul.model.stockCard.StockCardDetail;
import com.syahputrareno975.kartupersediaanmodul.model.stockCard.StockCardProduct;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ToHtml {
    public static String toHtml(ArrayList<StockCard> s){
        String body = "";
        for (StockCard i : s){
            body += "<html>\n" +
                    "    <head>\n" +
                    "    </head>\n" +
                    "    <body>\n" +
                    "\n" +
                    "            <div style='text-align:left;font-size:12px'>\n" +
                    "                    <table>\n" +
                    "                        <tr><td>Product Name : "+ i.ProductData.Name +"</td></tr>\n" +
                    "                        <tr><td>Standard Price : "+ i.ProductData.Price +"</td></tr>\n" +
                    "                        <tr><td>Unit : "+ i.ProductData.Unit +"</td></tr>\n" +
                    "                    </table>\n" +
                    "                </div>\n" +
                    "\n" +
                    "                <br />\n" +
                    "\n" +
                    "        <div style='text-align:center;font-size:8px'>\n" +
                    "                <table border='1'>\n" +
                    "                        <tr>\n" +
                    "                            <td rowspan='2'>Date</td><td rowspan='2'>Description</td>\n" +
                    "                            <td colspan='3'>Product In</td><td colspan='3'>Product Out</td>\n" +
                    "                            <td rowspan='2'>Quantity</td><td rowspan='2'>Price</td><td rowspan='2'>Total</td>\n" +
                    "                        </tr>\n" +
                    "                        <tr>\n" +
                    "                            <td>Quantity</td><td>Price</td><td>Total</td>\n" +
                    "                            <td>Quantity</td><td>Price</td><td>Total</td>\n" +
                    "                        </tr>\n" +
                    "</div>";
                    for (StockCardProduct p : i.StockCardProducts){

                        body += "<div style='text-align:center;vertical-align:bottom;font-size:8px'>";
                        body += "<tr>\n" +
                                "    <td> "+ p.DateTransaction.toOnlyDateString() +" </td>" +
                                "<td> "+ p.Description +" </td>" +
                                "<td> " + detailQtyToString(p.ProductIn) + " </td><td> " + detailPriceToString(p.ProductIn) + " </td><td> " + detailTotalToString(p.ProductIn) + " </td>" +
                                "<td> " + detailQtyToString(p.ProductOut) + " </td><td> " + detailPriceToString(p.ProductOut) + " </td><td> " + detailTotalToString(p.ProductOut) + " </td>" +
                                "<td> " + detailQtyToString(p.ProductStock) + " </td><td> " + detailPriceToString(p.ProductStock) + " </td><td> " + detailTotalToString(p.ProductStock) + " </td>\n" +
                                "</tr>";
                        body += "</div>";
                    }
                    body += "</table>" +
                            "<br /><br />";
            }
        body += "\n" +
                "    </body>\n" +
                "</html>";

        return body;
    }


    private static final DecimalFormat format = new DecimalFormat("##,###");

    private static String detailQtyToString(ArrayList<StockCardDetail> d){
        String s = "<table border='0'>";
        for (StockCardDetail i : d){
            s += i.Quantity == 0 ? "" : "<tr><td>" + format.format(i.Quantity) + "</td></tr>";
        }
        s += "</table>";
        return s;
    }
    private static String detailPriceToString(ArrayList<StockCardDetail> d){
        String s = "<table border='0'>";
        for (StockCardDetail i : d){
            s += i.Price == 0.0 ? "" : "<tr><td>" + format.format(i.Price) + "</td></tr>";
        }
        s += "</table>";
        return s;
    }
    private static String detailTotalToString(ArrayList<StockCardDetail> d){
        String s = "<table border='0'>";
        for (StockCardDetail i : d){
            s += i.Total == 0.0 ? "" : "<tr><td>" + format.format(i.Total) + "</td></tr>";
        }
        s += "</table>";
        return s;
    }
}
