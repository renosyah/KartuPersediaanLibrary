package com.syahputrareno975.kartupersediaanmodul.model.transaction;

import com.google.gson.annotations.SerializedName;
import com.syahputrareno975.kartupersediaanmodul.model.product.Product;

import java.io.Serializable;

public class TransactionDetail implements Serializable {

    @SerializedName("id")
    public String Id = "";

    @SerializedName("product_data")
    public Product ProductData = new Product();

    @SerializedName("price")
    public Double Price = 0.0;

    @SerializedName("quantity")
    public int Quantity = 0;

    @SerializedName("is_valid")
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
