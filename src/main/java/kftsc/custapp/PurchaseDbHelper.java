package kftsc.custapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kftsc on 1/17/2019.
 */

public class PurchaseDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PURCHASEINFO.DB";
    private static final int DATABASE_VERSION = 1;
    public  static final String CREATE_QUERY =
            "CREATE TABLE " + Customer.GasPurchase.TABLE_NAME + "(" + Customer.GasPurchase.Buyer + " TEXT," +
                    Customer.GasPurchase.GAS_TYPE + " TEXT,"
                    + Customer.GasPurchase.GAS_CATEGORY + " TEXT," + Customer.GasPurchase.AMOOUNT + " TEXT," +
                    Customer.GasPurchase.TIME + " TEXT);";

    public PurchaseDbHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DATABASE OPERATION", "Database created / opened...");

    }

    public String getCreateQuery() {
        return this.CREATE_QUERY;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.e("DATABASE OPERATION", "Table created...");
    }

    public void addPurchaseInfo (String buyerName, String gasType, String gasCategory, String amount, String time,
                                 SQLiteDatabase sqLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Customer.GasPurchase.Buyer, buyerName);
        contentValues.put(Customer.GasPurchase.GAS_TYPE, gasType);
        contentValues.put(Customer.GasPurchase.GAS_CATEGORY, gasCategory);
        contentValues.put(Customer.GasPurchase.AMOOUNT, amount);
        contentValues.put(Customer.GasPurchase.TIME, time);
        sqLiteDatabase.insert(Customer.GasPurchase.TABLE_NAME, null, contentValues);
        Log.e("DATABASE OPERATION", "One row is inserted...");
    }

    public Cursor searchInformation (String buyerName, SQLiteDatabase sqLiteDatabase) {
        String[] projections = {
                Customer.GasPurchase.Buyer,
                Customer.GasPurchase.GAS_TYPE,
                Customer.GasPurchase.GAS_CATEGORY,
                Customer.GasPurchase.AMOOUNT,
                Customer.GasPurchase.TIME
        };
        String selection = Customer.GasPurchase.Buyer + " LIKE ?";
        String[] selection_args = {buyerName};
        Cursor cursor = sqLiteDatabase.query(Customer.GasPurchase.TABLE_NAME, projections,
                selection, selection_args,null, null, null);
        return cursor;
    }

    public void deletePurchase (String buyerName, SQLiteDatabase sqLiteDatabase) {
        String selection = Customer.GasPurchase.Buyer + " LIKE ?";
        String[] selection_args = {buyerName};
        sqLiteDatabase.delete(Customer.GasPurchase.TABLE_NAME, selection, selection_args);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
