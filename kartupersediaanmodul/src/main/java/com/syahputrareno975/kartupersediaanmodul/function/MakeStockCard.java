package com.syahputrareno975.kartupersediaanmodul.function;

import com.syahputrareno975.kartupersediaanmodul.model.product.Product;
import com.syahputrareno975.kartupersediaanmodul.model.stockCard.StockCard;
import com.syahputrareno975.kartupersediaanmodul.model.stockCard.StockCardDetail;
import com.syahputrareno975.kartupersediaanmodul.model.stockCard.StockCardProduct;
import com.syahputrareno975.kartupersediaanmodul.model.transaction.Transaction;
import com.syahputrareno975.kartupersediaanmodul.model.transaction.TransactionDetail;

import java.util.ArrayList;
import java.util.UUID;

import static com.syahputrareno975.kartupersediaanmodul.function.SortTransaction.sortByDateASC;
import static com.syahputrareno975.kartupersediaanmodul.model.transaction.Transaction.TRANSACTION_PRODUCT_IN;
import static com.syahputrareno975.kartupersediaanmodul.model.transaction.Transaction.TRANSACTION_PRODUCT_OUT;

public class MakeStockCard {

    public static ArrayList<StockCard> makeStockCard(int method,ArrayList<Product> products,ArrayList<Transaction> transactions){
        ArrayList<StockCard> preStockCard = sortingByProduct(products,transactions);
        ArrayList<StockCard> stockCards = new ArrayList<>();
        for (StockCard p : preStockCard){
            for (StockCardProduct sp : p.StockCardProducts){
                switch (sp.Flow){
                    case TRANSACTION_PRODUCT_IN:
                        sp.calculateStockProductIn(method,p.CurrentGlobalStock);
                        break;
                    case TRANSACTION_PRODUCT_OUT:
                        sp.calculateStockProductOut(method,p.CurrentGlobalStock);
                        break;
                        default:
                            break;
                }
            }
            stockCards.add(p.copyStockCard());
        }
        return stockCards;
    }

    private static ArrayList<StockCard> sortingByProduct(ArrayList<Product> products,ArrayList<Transaction> oldTransactions){
        ArrayList<Transaction> transactions = sortByDateASC(oldTransactions);
        ArrayList<StockCard> stockCards = new ArrayList<>();
        for (Product p : products){
            if (!isExist(p,stockCards)){
                stockCards.add(new StockCard(
                        UUID.randomUUID().toString(),
                        p.copyProduct(),
                        new ArrayList<StockCardProduct>())
                );
            }
        }
        for (StockCard s : stockCards) {
            for (Transaction t : transactions) {
                for (TransactionDetail d : t.TransactionDetails) {
                    if (s.ProductData.Id.equals(d.ProductData.Id)){

                        StockCardProduct stockCardProduct = new StockCardProduct(
                                UUID.randomUUID().toString(),
                                t.Description,
                                t.DateTransaction.copyTransactionDate(),
                                t.Flow);

                        switch (t.Flow){
                            case TRANSACTION_PRODUCT_IN:
                                stockCardProduct.ProductIn.add(new StockCardDetail(
                                        UUID.randomUUID().toString(),
                                        d.Quantity,
                                        d.Price,
                                        (d.Price * d.Quantity))
                                );
                                break;
                            case TRANSACTION_PRODUCT_OUT:
                                stockCardProduct.ProductOut.add(new StockCardDetail(
                                        UUID.randomUUID().toString(),
                                        d.Quantity,
                                        d.Price,
                                        (d.Price * d.Quantity))
                                );
                                break;
                            default:
                                break;
                        }
                        s.StockCardProducts.add(stockCardProduct);
                    }
                }
            }
        }
        return stockCards;
    }


    private static Boolean isExist(Product p,ArrayList<StockCard> stockCards){
        for (StockCard s : stockCards){
            if (s.ProductData.Id.equals(p.Id)){
                return true;
            }
        }
        return false;
    }
}
