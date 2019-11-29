package com.syahputrareno975.kartupersediaanmodul.model.stockCard;

import java.io.Serializable;

public class StockCardDetail implements Serializable {
    public String Id = "";
    public int Quantity = 0;
    public Double Price = 0.0;
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
