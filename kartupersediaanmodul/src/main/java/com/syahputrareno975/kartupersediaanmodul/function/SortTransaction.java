package com.syahputrareno975.kartupersediaanmodul.function;

import android.os.Build;

import com.syahputrareno975.kartupersediaanmodul.model.transaction.Transaction;
import java.util.ArrayList;
import java.util.Comparator;

public class SortTransaction {


    public static ArrayList<Transaction> sortByDateASC(ArrayList<Transaction> transactions) {
        ArrayList<Transaction> newSorted = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            transactions.sort(new Comparator<Transaction>() {
                @Override
                public int compare(Transaction a, Transaction b) {
                    return b.DateTransaction.isAhead(a.DateTransaction) ?
                            -1
                            : a.DateTransaction.isAhead(b.DateTransaction) ?
                            1
                            : 0;
                }
            });
        }
        for (Transaction t : transactions){
            newSorted.add(t.copyTransaction());
        }
        return newSorted;
    }
    public static ArrayList<Transaction> sortByDateDESC(ArrayList<Transaction> transactions) {
        ArrayList<Transaction> newSorted = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            transactions.sort(new Comparator<Transaction>() {
                @Override
                public int compare(Transaction b, Transaction a) {
                    return b.DateTransaction.isAhead(a.DateTransaction) ?
                            -1
                            : a.DateTransaction.isAhead(b.DateTransaction) ?
                            1
                            : 0;
                }
            });
        }
        for (Transaction t : transactions){
            newSorted.add(t.copyTransaction());
        }
        return newSorted;
    }
}
