package com.syahputrareno975.kartupersediaanmodul.model.transaction;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionDate implements Serializable {

    @SerializedName("second")
    public int Second = 0;

    @SerializedName("minute")
    public int Minute= 0;

    @SerializedName("hour")
    public int Hour= 0;


    @SerializedName("day")
    public int Day = 0;

    @SerializedName("month")
    public int Month = 0;

    @SerializedName("year")
    public int Year = 0;

    public TransactionDate() {
        super();
    }


    public TransactionDate(int second, int minute, int hour, int day, int month, int year) {
        this.Second = second;
        this.Minute = minute;
        this.Hour = hour;
        this.Day = day;
        this.Month = month;
        this.Year = year;
    }

    public TransactionDate copyTransactionDate() {
        return new TransactionDate(this.Second,this.Minute,this.Hour,this.Day,this.Month,this.Year);
    }

    public Boolean isAhead(TransactionDate transactionDate){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {

            Date d1 = sdf.parse(this.toDateString());
            Date d2 = sdf.parse(transactionDate.toDateString());

            return d1 != null && d1.after(d2);

        } catch (NullPointerException e){
            e.printStackTrace();
        }
        catch (ParseException e){
            e.printStackTrace();
        }

        return false;
    }

    public String toDateString(){
        return this.Day + "/" + this.Month + "/" + this.Year +" "+ this.Hour + ":" + this.Minute;
    }

    public String toOnlyTimeString(){
        return this.Hour + ":" + this.Minute + ":" + this.Second;
    }
    public String toOnlyDateString(){
        return this.Day + "-" + this.Month + "-" + this.Year;
    }
}
