package com.example.project1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ViewActivity extends AppCompatActivity {

    ArrayList<ShoppingItem> shoppingList;
    ShoppingItemAdaptor adaptor;
    ListView lvShoppingItem;
    ShoppingDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        shoppingList = new ArrayList<>();
        adaptor = new ShoppingItemAdaptor(this,shoppingList, false);
        lvShoppingItem = (ListView)findViewById(R.id.lvShoppingItem);
        lvShoppingItem.setAdapter(adaptor);

        dataSource = new ShoppingDataSource(this);
        dataSource.open();

        shoppingList = dataSource.getAll();
        if(shoppingList.size() == 0)
        {
            Toast.makeText(this, "No shopping list found in the database", Toast.LENGTH_LONG).show();
        }

        sortList();

        dataSource.close();

        lvShoppingItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ShoppingItem item = (ShoppingItem)parent.getItemAtPosition(position);

                dataSource.open();
                dataSource.update(item);
                dataSource.close();
                updateList();
            }
        });
    }

    private void updateList() {

        adaptor.clear();
        dataSource.open();

        shoppingList = dataSource.getAll();

        dataSource.close();
        sortList();
        adaptor.notifyDataSetChanged();
    }

    private void sortList(){

        Collections.sort(shoppingList, new Comparator<ShoppingItem>() {
            @Override
            public int compare(ShoppingItem item1, ShoppingItem item2) {
                int isPurchased1 = item1.getIsPurchased();
                int isPurchased2 = item2.getIsPurchased();
                return isPurchased1 > isPurchased2 ? 1:-1;
            }
        });

        for(ShoppingItem shoppingItem : shoppingList)
        {
            adaptor.add(shoppingItem);
        }
    }
}
