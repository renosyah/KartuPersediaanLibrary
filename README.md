# Android Library : Kartu Persediaan

 
 
## how to use

first of all, you need 3 parameter to generate your report : method,products, and transaction
 
```

        
        KartuPersediaanInit.newInstance()
            .setMethod(MethodStockCard.METHOD_FIFO)
            .setProducts(products)
            .setTransactions(transactions)
            .setOnKartuPersediaanInitCallback(object : OnKartuPersediaanInitCallback{
                override fun onTransactionValidateResult(isValid: Boolean, flag: Int) {
                   
                    // if transaction is not valid 
                    // this callback will show it
                    // and return a flag of invalid
                    
                }

                override fun onStockCardResult(stockCards: ArrayList<StockCard>) {
                    
                    // result will be array list
                    // stock card of each product
                    
                }

            }).makeStockCard()
            

``` 

## other library use in this example

* library pdf viewer to view report as image :
 [github.com/renosyah/EBookLibrary](https://github.com/renosyah/EBookLibrary)
* library invoice maker to make report in pdf :
 [github.com/renosyah/InvoiceMakerLibrary](https://github.com/renosyah/InvoiceMakerLibrary)