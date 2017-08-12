package com.example.project1;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ClearActivity extends AppCompatActivity {

    ArrayList<ShoppingItem> shoppingItems;
    ShoppingItemAdaptor adaptor;
    ListView lvItemList;
    ShoppingDataSource dataSource;
    Button btnClearCrossed;
    Button btnClearAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);

        shoppingItems = new ArrayList<>();
        lvItemList = (ListView)findViewById(R.id.lvItemList);
        btnClearCrossed = (Button)findViewById(R.id.btnClearCrossed);

        dataSource = new ShoppingDataSource(this);
        dataSource.open();

        shoppingItems = dataSource.getAll();
        if(shoppingItems.size() == 0)
        {
            btnClearCrossed.setEnabled(false);
            Toast.makeText(this, "No shopping list found in the database", Toast.LENGTH_LONG).show();
        }
        else
        {
            btnClearCrossed.setEnabled(true);
            adaptor = new ShoppingItemAdaptor(this,shoppingItems,true);
            lvItemList.setAdapter(adaptor);
//            for(ShoppingItem shoppingItem : shoppingItems)
//            {
//                adaptor.add(shoppingItem);
//            }
        }

        lvItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                ShoppingItem item = (ShoppingItem) adapterView.getItemAtPosition(position);
                String itemName = item.getName();
                String msg = "You deleted: " + itemName;
                Toast.makeText(ClearActivity.this,msg,Toast.LENGTH_LONG).show();

                adaptor.remove(item);
                dataSource.open();
                dataSource.delete(item);
                dataSource.close();
            }
        });
    }

    public void onClearAll(View view) {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setTitle("Delete All")
                .setMessage("Delete all the items from shopping list?")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dataSource.open();
                        dataSource.deleteAll();
                        dataSource.close();

                        String msg = "You deleted all the items.";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();

    }

    public void onClearCrossed(View view) {

        dataSource.open();

        ArrayList<Integer> indexArray = new ArrayList<>();
        int count = 0;
        for (ShoppingItem item: dataSource.getAll())
        {
            if(item.getIsPurchased()!= 0)
            {
                dataSource.delete(item);
//                adaptor.remove(item);
//                shoppingItems.remove(item);
                count++;
            }
        }

        updateList();

        dataSource.close();

        String msg = "You deleted " + count + " items.";
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void updateList() {

        adaptor.clear();
        dataSource.open();
        for(ShoppingItem item : dataSource.getAll())
        {
            adaptor.add(item);
        }
        dataSource.close();
        adaptor.notifyDataSetChanged();
    }
}
