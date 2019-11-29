package com.syahputrareno975.kartupersediaanmodul.model.transaction;

import com.syahputrareno975.kartupersediaanmodul.model.product.Product;

import java.io.Serializable;

public class TransactionDetail implements Serializable {
    public String Id = "";
    public Product ProductData = new Product();
    public Double Price = 0.0;
    public int Quantity = 0;
    public Boolean IsValid = false;

    public TransactionDetail() {
        super();
    }

    public TransactionDetail(String id, Product productData, Double price, int quantity, Boolean isValid) {
        this.Id = id;
        this.ProductData = productData;
        this.Price = price;
        this.Quantity = quantity;
        this.IsValid = isValid;
    }

    public TransactionDetail cloneTransactionDetail(){
        return new TransactionDetail(this.Id,this.ProductData.copyProduct(),this.Price,this.Quantity,this.IsValid);
    }
}
