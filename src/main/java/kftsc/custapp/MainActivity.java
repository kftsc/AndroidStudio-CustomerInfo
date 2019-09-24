package kftsc.custapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("客户档案");

        final Button setNewButton = (Button) findViewById(R.id.setNewBtn);
        setNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNewAct(setNewButton);
            }
        });

        final Button searchButton = (Button) findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAct(searchButton);
            }
        });
    }

    public void setNewAct (View view) {
        Intent setNewIntent = new Intent(this,SetNewActivity.class);
        startActivity(setNewIntent);
    }

    public void searchAct (View view) {
        Intent searchIntent = new Intent(this, SearchActivity.class);
        startActivity(searchIntent);
    }
}
