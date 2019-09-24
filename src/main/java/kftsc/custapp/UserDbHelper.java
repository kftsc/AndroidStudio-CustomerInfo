package kftsc.custapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kftsc on 1/15/2019.
 */

public class UserDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CUSTOMERINFO.DB";
    private static final int DATABASE_VERSION = 1;
    //private static final String PURCHASE_QUERY = PurchaseDbHelper.CREATE_QUERY;
    private static final String CREATE_QUERY =
            "CREATE TABLE " + Customer.CustomerInfo.TABLE_NAME + "(" + Customer.CustomerInfo.CLIENT_COMPANY
                    + " TEXT," + Customer.CustomerInfo.CLIENT_NAME + " TEXT," + Customer.CustomerInfo.PHONE_NUM +
                    " TEXT," + Customer.CustomerInfo.ADDRESS + " TEXT);";

    public UserDbHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DATABASE OPERATION", "Database created / opened...");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.e("DATABASE OPERATION", "Table created...");
    }

    public void addCustomerInfo(String company, String name, String phoneNum, String address, SQLiteDatabase sqLiteDateBase){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Customer.CustomerInfo.CLIENT_COMPANY, company);
        contentValues.put(Customer.CustomerInfo.CLIENT_NAME, name);
        contentValues.put(Customer.CustomerInfo.PHONE_NUM, phoneNum);
        contentValues.put(Customer.CustomerInfo.ADDRESS, address);
        sqLiteDateBase.insert(Customer.CustomerInfo.TABLE_NAME, null,contentValues);
        Log.e("DATABASE OPERATION", "One row is inserted...");
    }

    public Cursor searchInformation (String clientName, SQLiteDatabase sqLiteDatabase) {
        String[] projections = {
                Customer.CustomerInfo.CLIENT_COMPANY,
                Customer.CustomerInfo.PHONE_NUM,
                Customer.CustomerInfo.ADDRESS
        };
        String selection = Customer.CustomerInfo.CLIENT_NAME + " LIKE ?";
        String[] selection_args = {clientName};
        Cursor cursor = sqLiteDatabase.query(Customer.CustomerInfo.TABLE_NAME, projections,
                selection, selection_args,null, null, null);
        return cursor;
    }

    public void deleteCustomer (String clientName, SQLiteDatabase sqLiteDatabase) {
        String selection = Customer.CustomerInfo.CLIENT_NAME + " LIKE ?";
        String[] selection_args = {clientName};
        sqLiteDatabase.delete(Customer.CustomerInfo.TABLE_NAME, selection, selection_args);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
