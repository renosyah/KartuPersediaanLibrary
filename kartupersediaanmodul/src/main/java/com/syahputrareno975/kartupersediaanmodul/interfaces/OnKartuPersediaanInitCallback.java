package com.syahputrareno975.kartupersediaanmodul.interfaces;

import com.syahputrareno975.kartupersediaanmodul.model.stockCard.StockCard;

import java.util.ArrayList;

public interface OnKartuPersediaanInitCallback {
    void onTransactionValidateResult(Boolean isValid,int flag);
    void onStockCardResult(ArrayList<StockCard> stockCards);
}
