package com.syahputrareno975.kartupersediaanmodul.interfaces;


import com.syahputrareno975.kartupersediaanmodul.model.transaction.Transaction;

public interface OnValidateTransaction {
    void isValid();
    void invalid(Transaction t);
}
