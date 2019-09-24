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

public class SetNewActivity extends AppCompatActivity {
    EditText ClientCompany, ClientName, PhoneNum, Address;
    Context context = this;
    UserDbHelper userDbHelper;
    SQLiteDatabase sqLiteDatabase;
    //PurchaseDbHelper purchaseDbHelper;
    //SQLiteDatabase sqLiteDatabase2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new);

        this.setTitle("新建");

        ClientCompany = (EditText) findViewById(R.id.clientCompany);
        ClientName = (EditText) findViewById(R.id.clientName);
        PhoneNum = (EditText) findViewById(R.id.phoneNum);
        Address = (EditText) findViewById(R.id.address);

        final Button saveBtn = (Button) findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact(saveBtn);
            }
        });
    }

    public void addContact(View v) {
        String company = ClientCompany.getText().toString();
        String name = ClientName.getText().toString();
        String phoneNum = PhoneNum.getText().toString();
        String address = Address.getText().toString();

        userDbHelper = new UserDbHelper(context);
        sqLiteDatabase = userDbHelper.getWritableDatabase();
        userDbHelper.addCustomerInfo(company, name,phoneNum, address, sqLiteDatabase);
        Toast.makeText(getBaseContext(), "以存储", Toast.LENGTH_LONG).show();
        userDbHelper.close();

        Intent back = new Intent (this, MainActivity.class);
        startActivity(back);
    }
}
