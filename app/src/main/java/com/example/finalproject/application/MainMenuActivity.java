package com.example.finalproject.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject.R;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMenuActivity extends AppCompatActivity {

    private static final String USERNAME_KEY = "usernameKey";

    private static final String FLAG_KEY = "flag";

    public static String USERNAME = null;

    @BindView(R.id.classSearch)
    TextView classSearch;

    @BindView(R.id.myCart)
    TextView myCart;

    @BindView(R.id.classSchedule)
    TextView classSchedule;

    @BindView(R.id.userInfo1)
    ImageButton userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_page);
        ButterKnife.bind(this);
        Bundle parameter = getIntent().getExtras();
        if (parameter != null) {
            USERNAME = Objects.requireNonNull(parameter.get(USERNAME_KEY)).toString();
        }
    }


    // go to the class search page
    @OnClick(R.id.classSearch)
    public void goToClassSearchPage() {
        Intent intent = new Intent(MainMenuActivity.this, SearchActivity.class);
        Bundle parameter = new Bundle();
        parameter.putString(USERNAME_KEY, USERNAME);
        intent.putExtras(parameter);
        startActivity(intent);
        finish();
    }


    // go to the course cart page
    @OnClick(R.id.myCart)
    public void goToMyCartPage() {
        Intent intent = new Intent(MainMenuActivity.this, CartActivity.class);
        Bundle parameter = new Bundle();
        parameter.putString(FLAG_KEY, String.valueOf(2));
        intent.putExtras(parameter);
        startActivity(intent);
        finish();
    }


    // go to the course schedule page
    @OnClick(R.id.classSchedule)
    public void goToSchedulePage() {
        startActivity(new Intent(MainMenuActivity.this, ScheduleActivity.class));
    }


    // check the current user's information
    @OnClick(R.id.userInfo1)
    public void checkUserInfo() {
        PopupMenu popup = new PopupMenu(MainMenuActivity.this, userInfo);
        Menu menu = popup.getMenu();
        popup.getMenuInflater()
                .inflate(R.menu.popup_menu,menu);
        MenuItem usernameItem = menu.getItem(0);
        usernameItem.setTitle("Username: " + USERNAME);
        MenuItem logoutItem = menu.getItem(1);

        logoutItem.setOnMenuItemClickListener(menuItem -> {
            startActivity(new Intent(MainMenuActivity.this, LoginActivity.class));
            return true;
        });

        popup.show();
    }



}
