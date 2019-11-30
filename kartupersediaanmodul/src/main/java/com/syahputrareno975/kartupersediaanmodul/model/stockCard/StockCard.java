package com.syahputrareno975.kartupersediaanmodul.model.stockCard;

import com.google.gson.annotations.SerializedName;
import com.syahputrareno975.kartupersediaanmodul.model.product.Product;
import com.syahputrareno975.kartupersediaanmodul.model.transaction.TransactionDate;

import java.io.Serializable;
import java.util.ArrayList;

public class StockCard implements Serializable {

    @SerializedName("id")
    public String Id = "";

    @SerializedName("product_data")
    public Product ProductData = new Product();

    @SerializedName("stock_card_product")
    public ArrayList<StockCardProduct> StockCardProducts = new ArrayList<>();

    // global stock
    // each stock card is set from this variabel
    public ArrayList<StockCardDetail> CurrentGlobalStock = new ArrayList<>();

    public StockCard() {
        super();
    }

    public StockCard(String id, Product productData, ArrayList<StockCardProduct> stockCardProducts) {
        this.Id = id;
        this.ProductData = productData;
        this.StockCardProducts = stockCardProducts;
    }


    public StockCard copyStockCard(){
        ArrayList<StockCardProduct> s = new ArrayList<>();
        for (StockCardProduct i : this.StockCardProducts){
            s.add(i.copyStockCardProduct());
        }
        return new StockCard(this.Id,this.ProductData.copyProduct(),s);
    }
}
