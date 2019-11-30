package com.syahputrareno975.kartupersediaanmodul.model.stockCard;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StockCardDetail implements Serializable {

    @SerializedName("id")
    public String Id = "";

    @SerializedName("quantity")
    public int Quantity = 0;

    @SerializedName("price")
    public Double Price = 0.0;

    @SerializedName("total")
    public Double Total = 0.0;

    public StockCardDetail() {
        super();
    }

    public StockCardDetail(String id,int quantity, Double price, Double total) {
        this.Id = id;
        this.Quantity = quantity;
        this.Price = price;
        this.Total = total;
    }

    public StockCardDetail cloneStockCardDetail(){
        return new StockCardDetail(this.Id,this.Quantity,this.Price,this.Total);
    }
}
