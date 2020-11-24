package com.example.finalproject.application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMenuActivity extends AppCompatActivity {

    @BindView(R.id.classSearch)
    TextView textView;

    @BindView(R.id.myCart)
    TextView myCart;

    @BindView(R.id.classSchedule)
    TextView classSchedule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_page);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.classSearch)
    public void goToClassSearchPage() {
        startActivity(new Intent(MainMenuActivity.this, SearchActivity.class));
    }

}
