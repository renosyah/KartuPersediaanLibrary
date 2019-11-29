package com.syahputrareno975.kartupersediaanmodul.model.stockCard;

import com.syahputrareno975.kartupersediaanmodul.model.transaction.TransactionDate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import static com.syahputrareno975.kartupersediaanmodul.model.MethodStockCard.METHOD_AVG;
import static com.syahputrareno975.kartupersediaanmodul.model.MethodStockCard.METHOD_FIFO;
import static com.syahputrareno975.kartupersediaanmodul.model.MethodStockCard.METHOD_LIFO;
import static com.syahputrareno975.kartupersediaanmodul.model.transaction.Transaction.TRANSACTION_PRODUCT_IN;

public class StockCardProduct implements Serializable {
    public String Id = "";
    public int Flow = TRANSACTION_PRODUCT_IN;
    public String Description = "";
    public TransactionDate DateTransaction = new TransactionDate();
    public ArrayList<StockCardDetail> ProductIn = new ArrayList<>();
    public ArrayList<StockCardDetail> ProductOut = new ArrayList<>();
    public ArrayList<StockCardDetail> ProductStock = new ArrayList<>();

    public StockCardProduct() {
        super();
    }

    public StockCardProduct(String id, String description, TransactionDate dateTransaction, int flow) {
        this.Id = id;
        this.Flow = flow;
        this.Description = description;
        this.DateTransaction = dateTransaction;
    }

    public StockCardProduct(String id, String Description, TransactionDate DateTransaction, int flow, ArrayList<StockCardDetail> productIn, ArrayList<StockCardDetail> productOut, ArrayList<StockCardDetail> productStock) {
        this.Id = id;
        this.Flow = flow;
        this.Description = Description;
        this.DateTransaction = DateTransaction;
        this.ProductIn = productIn;
        this.ProductOut = productOut;
        this.ProductStock = productStock;
    }

    public StockCardProduct copyStockCardProduct(){
        ArrayList<StockCardDetail> newProductIn = new ArrayList<>();
        for (StockCardDetail i : ProductIn){
            newProductIn.add(i.cloneStockCardDetail());
        }
        ArrayList<StockCardDetail> newProductOut = new ArrayList<>();
        for (StockCardDetail i : ProductOut){
            newProductOut.add(i.cloneStockCardDetail());
        }
        ArrayList<StockCardDetail> newProductStock = new ArrayList<>();
        for (StockCardDetail i : ProductStock){
            newProductStock.add(i.cloneStockCardDetail());
        }
        return new StockCardProduct(this.Id,this.Description,this.DateTransaction.copyTransactionDate(),this.Flow,newProductIn,newProductOut,newProductStock);
    }


    // only run this funtion
    // when flow product is out
    // because this function
    // recreate value for array of product out
    private static void reverseStock(ArrayList<StockCardDetail> currentStock){
        ArrayList<StockCardDetail> newData = new ArrayList<>();
        for (int i=currentStock.size()-1;i>=0;i--){
            newData.add(currentStock.get(i).cloneStockCardDetail());
        }

        currentStock.clear();

        for (StockCardDetail d : newData){
            currentStock.add(d.cloneStockCardDetail());
        }
    }

    public void calculateStockProductOut(int method,ArrayList<StockCardDetail> currentStock) {
        int countProductOut = 0;
        for (StockCardDetail out : this.ProductOut){
            countProductOut += out.Quantity;
        }
        this.ProductOut.clear();

        switch (method) {
            case METHOD_FIFO:

                for (StockCardDetail s : currentStock){
                    if (countProductOut == 0){
                        break;
                    } else if (s.Quantity > 0 && countProductOut - s.Quantity >= 0){

                        this.ProductOut.add(new StockCardDetail(
                                UUID.randomUUID().toString(),
                                s.Quantity,
                                s.Price,
                                s.Quantity * s.Price));

                        // set all to 0
                        // beacause his value
                        // hass been take for
                        // product out
                        countProductOut -= s.Quantity;
                        s.Quantity = 0;s.Price = 0.0;s.Total = 0.0;

                    } else if (s.Quantity > 0 && countProductOut - s.Quantity < 0 ){
                        this.ProductOut.add(new StockCardDetail(
                                UUID.randomUUID().toString(),
                                countProductOut,
                                s.Price,
                                s.Quantity * s.Price));

                        s.Quantity = (s.Quantity - countProductOut);
                        s.Total = s.Quantity * s.Price;
                        countProductOut = 0;
                    }
                }

                for (StockCardDetail s : currentStock){
                    this.ProductStock.add(s.cloneStockCardDetail());
                }

                break;
            case METHOD_LIFO:

                // reverse global stock
                reverseStock(currentStock);

                for (StockCardDetail s : currentStock){
                    if (countProductOut == 0){
                        break;
                    } else if (s.Quantity > 0 && countProductOut - s.Quantity >= 0){

                        this.ProductOut.add(new StockCardDetail(
                                UUID.randomUUID().toString(),
                                s.Quantity,
                                s.Price,
                                s.Quantity * s.Price));

                        countProductOut -= s.Quantity;
                        s.Quantity = 0;s.Price = 0.0;s.Total = 0.0;

                    } else if (s.Quantity > 0 && countProductOut - s.Quantity < 0 ){
                        this.ProductOut.add(new StockCardDetail(
                                UUID.randomUUID().toString(),
                                countProductOut,
                                s.Price,
                                s.Quantity * s.Price));

                        s.Quantity = (s.Quantity - countProductOut);
                        s.Total = s.Quantity * s.Price;
                        countProductOut = 0;
                    }
                }

                // reverse back to normal global stock
                reverseStock(currentStock);

                for (StockCardDetail s : currentStock){
                    this.ProductStock.add(s.cloneStockCardDetail());
                }

                break;
            case METHOD_AVG:

                StockCardDetail productStock = new StockCardDetail();
                productStock.Id = UUID.randomUUID().toString();

                for (StockCardDetail s : currentStock){
                    productStock.Quantity = s.Quantity;
                    productStock.Price = s.Price;
                    productStock.Total = s.Total;
                }

                StockCardDetail productOut = new StockCardDetail(
                        UUID.randomUUID().toString(),
                        countProductOut,
                        productStock.Price,
                        countProductOut * productStock.Price);

                productStock.Quantity -= productOut.Quantity;
                productStock.Total -= productOut.Total;

                this.ProductOut.add(productOut);
                this.ProductStock.add(productStock);

                currentStock.clear();
                currentStock.add(productStock);

                break;
            default:
                break;
        }

    }
    // only run this funtion
    // when flow product is in
    // because this function
    // add value to global stock
    public void calculateStockProductIn(int method,ArrayList<StockCardDetail> currentStock) {
        switch (method) {
            case METHOD_FIFO: case METHOD_LIFO:

                // for fifo and lifo
                // just add to global stock
                for (StockCardDetail in : this.ProductIn){
                    currentStock.add(in.cloneStockCardDetail());
                }

                for (StockCardDetail cs : currentStock){
                    this.ProductStock.add(cs.cloneStockCardDetail());
                }

                break;
            case METHOD_AVG:

                StockCardDetail productStock = new StockCardDetail();
                productStock.Id = UUID.randomUUID().toString();

                for (StockCardDetail s : currentStock){
                    productStock.Quantity += s.Quantity;
                    productStock.Total += (s.Price * s.Quantity);
                }

                for (StockCardDetail in : this.ProductIn){
                    productStock.Quantity += in.Quantity;
                    productStock.Total += (in.Price * in.Quantity);
                }

                productStock.Price = (productStock.Total / productStock.Quantity);

                currentStock.clear();
                currentStock.add(productStock);

                this.ProductStock.add(productStock);

                break;
            default:
                break;
        }

    }
}
