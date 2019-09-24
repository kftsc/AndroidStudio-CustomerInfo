package kftsc.custapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetPurchaseActivity extends AppCompatActivity {

    EditText TypeText, CategoryText, Amount, Time;
    Context context = this;
    PurchaseDbHelper purchaseDbHelper;
    SQLiteDatabase sqLiteDatabase;
    private String buyerName;
    public static final String EXTRA_MESSAGE2 = "kftsc.customerinfor";
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_purchase);

        this.setTitle("购油信息");

        Intent buyer = getIntent();
        if (index == 0) {
            buyerName = buyer.getStringExtra(SearchActivity.EXTRA_MESSAGE);
        }
        else {
            buyerName = buyer.getStringExtra(SetPurchaseActivity.EXTRA_MESSAGE2);
        }


        TypeText = (EditText) findViewById(R.id.typeText);
        CategoryText = (EditText) findViewById(R.id.categoryText);
        Amount = (EditText) findViewById(R.id.amount);
        Time = (EditText) findViewById(R.id.time);

        final Button saveBtn = (Button) findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePurchaseInfo(saveBtn);
            }
        });

        final Button nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                saveNextInfo(nextBtn);
            }
        });
    }

    public void savePurchaseInfo(View v) {
        purchaseDbHelper = new PurchaseDbHelper(context);
        sqLiteDatabase = purchaseDbHelper.getWritableDatabase();
        String buyerName = this.buyerName;
        String gasType = TypeText.getText().toString();
        String gasCategory = CategoryText.getText().toString();
        String gasAmount = Amount.getText().toString();
        String time = Time.getText().toString();
        purchaseDbHelper.addPurchaseInfo(buyerName, gasType,gasCategory,gasAmount,time,sqLiteDatabase);
        Toast.makeText(getBaseContext(), "以存储", Toast.LENGTH_LONG).show();
        purchaseDbHelper.close();
        Intent back = new Intent(this, SearchActivity.class);
        startActivity(back);
    }

    public void saveNextInfo (View v) {
        purchaseDbHelper = new PurchaseDbHelper(context);
        sqLiteDatabase = purchaseDbHelper.getWritableDatabase();
        String buyerName = this.buyerName;
        String gasType = TypeText.getText().toString();
        String gasCategory = CategoryText.getText().toString();
        String gasAmount = Amount.getText().toString();
        String time = Time.getText().toString();
        purchaseDbHelper.addPurchaseInfo(buyerName, gasType,gasCategory,gasAmount,time,sqLiteDatabase);
        Toast.makeText(getBaseContext(), "以存储", Toast.LENGTH_LONG).show();
        purchaseDbHelper.close();
        index = 1;
        Intent next = new Intent(this, SetPurchaseActivity.class);
        next.putExtra(EXTRA_MESSAGE2, buyerName);
        startActivity(next);

    }

}
