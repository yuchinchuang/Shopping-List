package com.example.project1;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    private String name;
    private int quantity;
    EditText etName;
    EditText etQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etName = (EditText)findViewById(R.id.etAddName);
        etQuantity = (EditText)findViewById(R.id.etAddQuantity);
    }

    public void onAdd(View view) {
        ShoppingDataSource dataSource = new ShoppingDataSource(this);
        dataSource.open();

        name = etName.getText().toString();
        String quantityTest = etQuantity.getText().toString();

        String msg ="" ;

        if(name.equals(""))
        {
            msg = "Name cannot be empty!";
        }
        else
        {
            if(quantityTest.equals(""))
            {
                quantity = 0;
            }
            else
            {
                quantity = Integer.parseInt(quantityTest);
            }

            dataSource.insert(name,  quantity);

            dataSource.close();

            msg = "You added " + name + ", " + quantity + " into the shopping list.";

            super.finish();
        }

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

    }
}
