package com.example.project1;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShoppingItemAdaptor extends ArrayAdapter<ShoppingItem>{

    private boolean isClearActivity;

    public ShoppingItemAdaptor(Context context, ArrayList<ShoppingItem> shoppingList, boolean isClearActivity) {
        super(context, 0, shoppingList);

        this.isClearActivity = isClearActivity;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ShoppingItem shoppingItem = getItem(position);

        if(convertView == null)
        {
            int layout;
            if(isClearActivity)
            {
                layout = R.layout.row_layout_delete;
            }
            else
            {
                layout = R.layout.row_layout_view;
            }
            convertView = LayoutInflater.from(getContext()).inflate(layout,parent,false);
        }

        if(isClearActivity)
        {
            TextView tvClearName = (TextView)convertView.findViewById(R.id.tvClearName);
            tvClearName.setText(shoppingItem.getName());
            ImageView ivBullet = (ImageView)convertView.findViewById(R.id.ivBullet);
            if(shoppingItem.getIsPurchased() == 1 )
            {
                tvClearName.setPaintFlags(tvClearName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                ivBullet.setImageResource(R.drawable.red_ball);
            }
            else
            {
                tvClearName.setPaintFlags(tvClearName.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                ivBullet.setImageResource(R.drawable.blue_sphere);
            }
        }
        else
        {
            TextView tvName =(TextView)convertView.findViewById(R.id.tvName);
            tvName.setText(shoppingItem.getName());

            TextView tvQuantity = (TextView)convertView.findViewById(R.id.tvQuantity);
            tvQuantity.setText(Integer.toString(shoppingItem.getQuantity()));

            CheckBox ckbIsPurchased = (CheckBox)convertView.findViewById(R.id.ckbCheck);
            if(shoppingItem.getIsPurchased() == 1)
            {
                ckbIsPurchased.setChecked(true);
            }
            else
            {
                ckbIsPurchased.setChecked(false);
            }
        }


        return convertView;
    }
}
