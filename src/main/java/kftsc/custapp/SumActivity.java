package kftsc.custapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class SumActivity extends AppCompatActivity {
    private String buyerName;
    private PurchaseDbHelper purchaseDbHelper;
    private SQLiteDatabase sqLiteDatabase;
    TextView output;
    private PurchaseList purchaseList;
    private boolean qi, chai;
    CheckBox checkQi, checkChai, cQi93, cQi98, cChai0, cChai_10;
    private String qi93, qi98, chai0, chai_10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum);

        this.setTitle("统计");

        Intent name = getIntent();
        buyerName = name.getStringExtra(SearchActivity.EXTRA_MESSAGE2);

        output = (TextView) findViewById(R.id.output);

        final Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goBack(backBtn);
            }
        });

        checkQi = (CheckBox) findViewById(R.id.checkQi);
        checkQi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkQi.isChecked()) {
                    qi = true;
                }
                else {
                    qi = false;
                }
            }
        });

        checkChai = (CheckBox) findViewById(R.id.checkChai);
        checkChai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkChai.isChecked()) {
                    chai = true;
                }
                else {
                    chai = false;
                }
            }
        });

        cQi93 = (CheckBox) findViewById(R.id.cQi93);
        cQi93.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cQi93.isChecked()) {
                    qi93 = "93";
                }
                else {
                    qi93 = "";
                }
            }
        });

        cQi98 = (CheckBox) findViewById(R.id.cQi98);
        cQi98.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cQi98.isChecked()) {
                    qi98 = "98";
                }
                else {
                    qi98 = "";
                }
            }
        });

        cChai0 = (CheckBox) findViewById(R.id.cChai0);
        cChai0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cChai0.isChecked()) {
                    chai0 = "0";
                }
                else {
                    chai0 = "";
                }
            }
        });

        cChai_10 = (CheckBox) findViewById(R.id.cChai_10);
        cChai_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cChai_10.isChecked()) {
                    chai_10 = "-10";
                }
                else {
                    chai_10 = "";
                }
            }
        });
        final Button doSum = (Button) findViewById(R.id.doSumBtn);
        doSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSum(doSum);
            }
        });
    }

    public void goBack(View v) {
       // Intent back = new Intent(this, SearchActivity.class);
        //startActivity(back);
    }

    public void getSum(View v) {
        purchaseDbHelper = new PurchaseDbHelper(getApplicationContext());
        sqLiteDatabase = purchaseDbHelper.getWritableDatabase();
        Cursor cursor = purchaseDbHelper.searchInformation(buyerName, sqLiteDatabase);
        purchaseList = new PurchaseList();

        if(cursor.moveToFirst()) {
            while(cursor.moveToNext()) {
                String type, category, amount, time;
                type = cursor.getString(1);
                category = cursor.getString(2);
                amount = cursor.getString(3);
                time = cursor.getString(4);
                if (SearchActivity.flag) {
                    if (Integer.valueOf(time) >= timeCalculator(SearchActivity.current_time, SearchActivity.duration_time)
                            && Integer.valueOf(time) <= SearchActivity.current_time ) {
                        PurchaseDataProvider provider = new PurchaseDataProvider(type, category, amount, time);
                        purchaseList.addOne(provider);
                    }
                }
                else {
                    PurchaseDataProvider provider = new PurchaseDataProvider(type, category, amount, time);
                    purchaseList.addOne(provider);
                }
            }
        }

        if (qi93 == null && qi98 == null && chai0 == null && chai_10 == null && !qi && !chai) {
            Toast.makeText(this, "请选择统计条件", Toast.LENGTH_SHORT).show();
        }
        else {
            output.setText(String.valueOf(purchaseList.advanceSum(qi, chai, qi93, qi98, chai0, chai_10)));
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
}
