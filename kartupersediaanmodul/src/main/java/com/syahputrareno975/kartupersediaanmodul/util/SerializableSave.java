package com.syahputrareno975.kartupersediaanmodul.util;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializableSave {

    public static String TRANSACTION_DATA_FILENAME = "transaction_data.data";
    public static String PRODUCT_DATA_FILENAME = "product_data.data";

    private Context context;
    private String filename;

    public SerializableSave(Context context, String filename) {
        this.context = context;
        this.filename = filename;
    }

    public Boolean save(Serializable d) {
        Boolean save = false;
        try {

            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(d);
            os.close();
            fos.close();

            save = true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return save;

    }

    public Serializable load() {
        Serializable data = null;

        try {
            FileInputStream fis = context.openFileInput(filename);
            ObjectInputStream file = new ObjectInputStream(fis);
            data = (Serializable) file.readObject();
            file.close();
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return data;
    }

    public  Boolean delete() {
        File f = new File(context.getFilesDir(),this.filename);
        return f.delete();
    }
}