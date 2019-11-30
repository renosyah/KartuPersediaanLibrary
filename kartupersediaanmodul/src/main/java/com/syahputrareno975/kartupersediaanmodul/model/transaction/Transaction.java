package com.syahputrareno975.kartupersediaanmodul.model.transaction;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Transaction implements Serializable {

    @SerializedName("id")
    public String Id = "";

    @SerializedName("transaction_date")
    public TransactionDate DateTransaction = new TransactionDate();

    @SerializedName("description")
    public String Description = "";

    @SerializedName("transaction_details")
    public ArrayList<TransactionDetail> TransactionDetails = new ArrayList<>();

    @SerializedName("flow")
    public int Flow = TRANSACTION_PRODUCT_IN;

    public static final int TRANSACTION_PRODUCT_IN = 0;
    public static final int TRANSACTION_PRODUCT_OUT = 1;
    public static final int PRODUCT_STOCK = 2;

    public Transaction() {
        super();
    }

    public Transaction(String id,String Description, TransactionDate DateTransaction, ArrayList<TransactionDetail> TransactionDetails, int flow) {
        this.Id = id;
        this.Description = Description;
        this.DateTransaction = DateTransaction;
        this.TransactionDetails = TransactionDetails;
        this.Flow = flow;
    }

    public Transaction copyTransaction() {
        ArrayList<TransactionDetail> newDetails = new ArrayList<>();
        for (TransactionDetail d : this.TransactionDetails){
            newDetails.add(d.cloneTransactionDetail());
        }
        return new Transaction(this.Id,this.Description,this.DateTransaction.copyTransactionDate(),newDetails,this.Flow);
    }

    public Double getTotal(){
        Double total = 0.0;
        for (TransactionDetail d : this.TransactionDetails){
            total += d.Quantity * d.Price;
        }
        return total;
    }


}
