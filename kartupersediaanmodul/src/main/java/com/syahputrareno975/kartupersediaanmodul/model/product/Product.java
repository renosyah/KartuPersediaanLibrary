package com.syahputrareno975.kartupersediaanmodul.model.product;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.UUID;

public class Product implements Serializable {

    @SerializedName("id")
    public String Id = UUID.randomUUID().toString();

    @SerializedName("name")
    public String Name = "";

    @SerializedName("price")
    public Double Price = 0.0;

    @SerializedName("unit")
    public String Unit = "";

    public Product() {
        super();
    }

    public Product(String id, String name, Double price,  String unit) {
        this.Id = id;
        this.Name = name;
        this.Price = price;
        this.Unit = unit;
    }

    public Product copyProduct() {
        return new Product(this.Id,this.Name,this.Price,this.Unit);
    }
}
