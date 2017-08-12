package com.example.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_VIEW = 1;
    public static final int REQUEST_CODE_ADD = 2;
    public static final int REQUEST_CODE_CLEAR = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnClick(View view) {
        switch(view.getId())
        {
            case R.id.btnViewList:
                Intent viewIntent = new Intent(this, ViewActivity.class);
                startActivityForResult(viewIntent,REQUEST_CODE_VIEW);
                break;
            case R.id.btnAdditem:
                Intent addIntent = new Intent(this, AddActivity.class);
                startActivityForResult(addIntent,REQUEST_CODE_ADD);
                break;
            case R.id.btnClearList:
                Intent clearIntent = new Intent(this, ClearActivity.class);
                startActivityForResult(clearIntent,REQUEST_CODE_CLEAR);
                break;
        }
    }
}
