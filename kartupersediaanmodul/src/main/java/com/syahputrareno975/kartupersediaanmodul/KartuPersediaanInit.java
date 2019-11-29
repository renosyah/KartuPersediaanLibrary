package com.syahputrareno975.kartupersediaanmodul;

import com.syahputrareno975.kartupersediaanmodul.function.MakeStockCard;
import com.syahputrareno975.kartupersediaanmodul.function.ValidateTransaction;
import com.syahputrareno975.kartupersediaanmodul.interfaces.OnKartuPersediaanInitCallback;
import com.syahputrareno975.kartupersediaanmodul.interfaces.OnValidateTransaction;
import com.syahputrareno975.kartupersediaanmodul.model.product.Product;
import com.syahputrareno975.kartupersediaanmodul.model.stockCard.StockCard;
import com.syahputrareno975.kartupersediaanmodul.model.transaction.Transaction;

import java.util.ArrayList;

import static com.syahputrareno975.kartupersediaanmodul.model.MethodStockCard.METHOD_FIFO;

public class KartuPersediaanInit {
    private static KartuPersediaanInit _instance = null;

    public static final int FLAG_INVALID_NONE = 0;
    public static final int FLAG_INVALID_DATE = 1;
    public static final int FLAG_INVALID_STOCK = 2;

    private int method = METHOD_FIFO;
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private OnKartuPersediaanInitCallback onKartuPersediaanInitCallback = new OnKartuPersediaanInitCallback() {
        @Override
        public void onTransactionValidateResult(Boolean isValid,int flag) {

        }

        @Override
        public void onStockCardResult(ArrayList<StockCard> stockCards) {

        }
    };


    private KartuPersediaanInit() {
        super();
    }

    public static KartuPersediaanInit newInstance() {
        _instance = new KartuPersediaanInit();
        return _instance;
    }

    public KartuPersediaanInit setMethod(int method) {
        this.method = method;
        return _instance;
    }

    public KartuPersediaanInit setProducts(ArrayList<Product> products) {
        this.products = products;
        return _instance;
    }

    public KartuPersediaanInit setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
        return _instance;
    }

    public KartuPersediaanInit setOnKartuPersediaanInitCallback(OnKartuPersediaanInitCallback onKartuPersediaanInitCallback) {
        this.onKartuPersediaanInitCallback = onKartuPersediaanInitCallback;
        return _instance;
    }


    private OnValidateTransaction onValidateDateTransaction = new OnValidateTransaction() {
        @Override
        public void isValid() {
            onKartuPersediaanInitCallback.onTransactionValidateResult(true,FLAG_INVALID_NONE);
            ValidateTransaction.isTransactionForProductStockIsValid(products,transactions,onValidateStockTransaction);
        }

        @Override
        public void invalid(Transaction t) {
            onKartuPersediaanInitCallback.onTransactionValidateResult(false,FLAG_INVALID_DATE);
        }
    };

    private OnValidateTransaction onValidateStockTransaction = new OnValidateTransaction() {
        @Override
        public void isValid() {
            onKartuPersediaanInitCallback.onTransactionValidateResult(true,FLAG_INVALID_NONE);
            onKartuPersediaanInitCallback.onStockCardResult(MakeStockCard.makeStockCard(method,products,transactions));
        }

        @Override
        public void invalid(Transaction t) {
            onKartuPersediaanInitCallback.onTransactionValidateResult(false,FLAG_INVALID_STOCK);
        }
    };

    public void makeStockCard(){
        ValidateTransaction.isTransactionDateValid(this.transactions,onValidateDateTransaction);
    }
}
