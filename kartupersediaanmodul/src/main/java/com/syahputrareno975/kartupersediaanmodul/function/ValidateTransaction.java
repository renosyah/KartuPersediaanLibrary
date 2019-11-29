package com.syahputrareno975.kartupersediaanmodul.function;

import com.syahputrareno975.kartupersediaanmodul.interfaces.OnValidateTransaction;
import com.syahputrareno975.kartupersediaanmodul.model.product.Product;
import com.syahputrareno975.kartupersediaanmodul.model.transaction.Transaction;
import com.syahputrareno975.kartupersediaanmodul.model.transaction.TransactionDetail;

import java.util.ArrayList;

import static com.syahputrareno975.kartupersediaanmodul.function.SortTransaction.sortByDateASC;
import static com.syahputrareno975.kartupersediaanmodul.model.transaction.Transaction.TRANSACTION_PRODUCT_IN;
import static com.syahputrareno975.kartupersediaanmodul.model.transaction.Transaction.TRANSACTION_PRODUCT_OUT;

public class ValidateTransaction {

    private static class ProductAndStock {
        public Product product = new Product();
        public int Stock = 0;

        public ProductAndStock(Product product, int stock) {
            this.product = product;
            Stock = stock;
        }
    }
    private static class FindProductResult {
        public Boolean Found = false;
        public int Pos = 0;

        public FindProductResult(Boolean found, int pos) {
            Found = found;
            Pos = pos;
        }
    }

    public static void isTransactionForProductStockIsValid(ArrayList<Product> products,ArrayList<Transaction> oldTransactions, OnValidateTransaction onValidateTransaction){

        // sort by date asc order
        ArrayList<Transaction> transactions = sortByDateASC(oldTransactions);

        // make group of product
        ArrayList<ProductAndStock> productAndStocks = new ArrayList<>();
        for (Product p : products){
            productAndStocks.add(new ProductAndStock(p.copyProduct(),0));
        }

        boolean breakAll = false;
        boolean isValid = true;
        for (Transaction t : transactions){
            for (TransactionDetail d : t.TransactionDetails){
                FindProductResult location = findProductPos(d.ProductData,productAndStocks);
                if (location.Found){
                    switch (t.Flow){
                        case TRANSACTION_PRODUCT_IN:
                            productAndStocks.get(location.Pos).Stock += d.Quantity;
                            break;
                        case TRANSACTION_PRODUCT_OUT:
                            productAndStocks.get(location.Pos).Stock -= d.Quantity;
                            break;
                        default:
                            break;
                    }

                    // if stock is below zero
                    // break all loop
                    // return is invalid
                    if (productAndStocks.get(location.Pos).Stock < 0 && onValidateTransaction != null){
                        onValidateTransaction.invalid(t.copyTransaction());
                        breakAll = true;
                        isValid = false;
                    }
                }
                if (breakAll){
                    break;
                }
            }
            if (breakAll){
                break;
            }
        }

        if (isValid && onValidateTransaction != null){
            onValidateTransaction.isValid();
        }
    }

    private static FindProductResult findProductPos(Product p,ArrayList<ProductAndStock> productAndStocks){
        FindProductResult result = new FindProductResult(false,0);
        for (int i=0;i<productAndStocks.size();i++){
            if (productAndStocks.get(i).product.Id.equals(p.Id)){
               result.Found = true;
               result.Pos = i;
               return result;
            }
        }
        return result;
    }

    public static void isTransactionDateValid(ArrayList<Transaction> transactions, OnValidateTransaction onValidateTransaction){
        String currentDate = "";
        boolean isValid = true;
        for (Transaction t : transactions){
            if (!currentDate.equals("") && currentDate.equals(t.DateTransaction.toDateString()) && onValidateTransaction != null){

                // found transaction with same date
                onValidateTransaction.invalid(t.copyTransaction());
                isValid = false;
                break;
            }
            currentDate = t.DateTransaction.toDateString();
        }

        if (isValid && onValidateTransaction != null){
            onValidateTransaction.isValid();
        }

    }
}
