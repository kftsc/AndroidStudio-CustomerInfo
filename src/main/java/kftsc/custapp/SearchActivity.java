package kftsc.custapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {
    TextView CompanyText, PhoneText, AddressText;
    EditText SearchName, DurationText, CurTimeText;
    UserDbHelper userDbHelper;
    SQLiteDatabase sqLiteDatabase;
    String search_name;
    public static Long duration_time;
    public static Long current_time;
    public static boolean flag;
    public static final String EXTRA_MESSAGE = "kftsc.customerinfor";
    public static final String EXTRA_MESSAGE2 = "kftsc.customerinfor";
    private String buyerName;

    ListView purchaseView;
    SQLiteDatabase sqLiteDatabase2;
    PurchaseDbHelper purchaseDbHelper;
    Cursor cursor;
    private ListDataAdapter listDataAdapter;

    CheckBox cAll, cOne;
    boolean bAll, bOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.setTitle("搜索");

        CompanyText = (TextView) findViewById(R.id.companyText);
        PhoneText = (TextView) findViewById(R.id.phoneText);
        AddressText = (TextView) findViewById(R.id.addressText);
        SearchName = (EditText) findViewById(R.id.searchText);
        DurationText = (EditText) findViewById(R.id.durationText);
        CurTimeText = (EditText) findViewById(R.id.curTimeText);


        final Button searchBtn = (Button) findViewById(R.id.findBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCustomer(searchBtn);
                displayPurchase(searchBtn);
            }
        });

        final Button newPurchaseBtn = (Button) findViewById(R.id.newPurchaseBtn);
        newPurchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewPurchase(newPurchaseBtn);
            }
        });

        final Button sumBtn = (Button) findViewById(R.id.sumBtn);
        sumBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                manageData(sumBtn);
            }
        });

        purchaseView = (ListView) findViewById(R.id.purchaseList);

        cAll = (CheckBox) findViewById(R.id.cAllInfo);
        cAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cAll.isChecked()) {
                    bAll = true;
                }
                else {
                    bAll = false;
                }
            }
        });

        cOne = (CheckBox) findViewById(R.id.cOneInfo);
        cOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cOne.isChecked()) {
                    bOne = true;
                }
                else {
                    bOne = false;
                }
            }
        });

        final Button deleteBtn = (Button) findViewById(R.id.delete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bOne && bAll) {
                    Toast.makeText(getBaseContext(), "只能选择一个", Toast.LENGTH_LONG).show();
                }
                else if (!bOne && !bAll) {
                    Toast.makeText(getBaseContext(), "请选择删除方式", Toast.LENGTH_LONG).show();
                }
                else {
                    deleteInfo(deleteBtn);
                }
            }
        });

    }

    public void searchCustomer(View v) {
        search_name = SearchName.getText().toString();
        userDbHelper = new UserDbHelper(getApplicationContext());
        sqLiteDatabase = userDbHelper.getWritableDatabase();
        Cursor cursor = userDbHelper.searchInformation(search_name, sqLiteDatabase);
        if (cursor.moveToFirst()) {
            String clientCompany = cursor.getString(0);
            String phoneNum = cursor.getString(1);
            String address = cursor.getString(2);

            CompanyText.setText(clientCompany);
            PhoneText.setText(phoneNum);
            AddressText.setText(address);

            //CompanyText.setVisibility(View.VISIBLE);
            //PhoneText.setVisibility(View.VISIBLE);
            //AddressText.setVisibility(View.VISIBLE);
        }
        else {
            CompanyText.setText("公司");
            PhoneText.setText("手机号");
            AddressText.setText("地址");
            Toast.makeText(getBaseContext(), "没有相关信息", Toast.LENGTH_LONG).show();
        }
    }

    public void displayPurchase (View v) {
        search_name = SearchName.getText().toString();
        if (DurationText.getText().toString().equals("") || CurTimeText.getText().toString().equals("")) {
            flag = false;
        }
        else {
            flag = true;
            duration_time = Long.valueOf(DurationText.getText().toString()) * 100;
            current_time = Long.valueOf(CurTimeText.getText().toString());
        }
        purchaseDbHelper = new PurchaseDbHelper(getApplicationContext());
        sqLiteDatabase2 = purchaseDbHelper.getReadableDatabase();
        cursor = purchaseDbHelper.searchInformation(
                search_name, sqLiteDatabase2);


        listDataAdapter = new ListDataAdapter(getApplicationContext(), R.layout.row_layout);
        purchaseView.setAdapter(listDataAdapter);

        if(cursor.moveToFirst()) {
            while(cursor.moveToNext()) {
                String type, category, amount, time;
                type = cursor.getString(1);
                category = cursor.getString(2);
                amount = cursor.getString(3);
                time = cursor.getString(4);
                // select by time
                if (flag) {
                    if (Integer.valueOf(time) >= timeCalculator(current_time, duration_time)
                            && Integer.valueOf(time) <= current_time ) {
                        PurchaseDataProvider provider = new PurchaseDataProvider(type, category, amount, time);
                        listDataAdapter.add(provider);
                    }
                }
                else {
                    PurchaseDataProvider provider = new PurchaseDataProvider(type, category, amount, time);
                    listDataAdapter.add(provider);
                }

            }
        }

    }

    public Integer timeCalculator(Long curr, Long dur) {
        // System.out.println(curr);
        // System.out.println(dur);
        // System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        int currMon = Integer.valueOf(curr.toString().substring(4,6));
        int currYear = Integer.valueOf(curr.toString().substring(0,4));
        String date= curr.toString().substring(6,8);
        Long durNum = dur / 100;
        while (durNum >= currMon) {
            durNum = durNum - currMon;
            currYear--;
            currMon = 12;
        }
        durNum = currMon - durNum;
        String month;
        if (durNum < 10) {
            month = "0" + String.valueOf(durNum);
        }
        else {
            month = String.valueOf(durNum);
        }
        String result =  String.valueOf(currYear) + month + date;
        return Integer.valueOf(result);
    }

    public void setNewPurchase (View v) {
        buyerName = SearchName.getText().toString();
        Intent purchaseIntent = new Intent(this, SetPurchaseActivity.class);
        purchaseIntent.putExtra(EXTRA_MESSAGE, buyerName);
        startActivity(purchaseIntent);
    }

    public void manageData(View v) {
        buyerName = SearchName.getText().toString();
        Intent sumIntent = new Intent (this, SumActivity.class);
        sumIntent.putExtra(EXTRA_MESSAGE2, buyerName);
        startActivity(sumIntent);
    }

    public void deleteInfo (View v) {
        if (bAll) {
            userDbHelper = new UserDbHelper(getApplicationContext());
            sqLiteDatabase = userDbHelper.getReadableDatabase();
            userDbHelper.deleteCustomer(search_name, sqLiteDatabase);

            purchaseDbHelper = new PurchaseDbHelper(getApplicationContext());
            sqLiteDatabase2 = purchaseDbHelper.getReadableDatabase();
            purchaseDbHelper.deletePurchase(search_name, sqLiteDatabase2);

            Toast.makeText(getBaseContext(), "信息已删除", Toast.LENGTH_LONG).show();


        }

    }
}
