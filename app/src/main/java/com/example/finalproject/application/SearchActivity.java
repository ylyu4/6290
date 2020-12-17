package com.example.finalproject.application;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.term)
    Spinner term;

    @BindView(R.id.subject)
    Spinner subject;

    @BindView(R.id.spinnerCourse)
    Spinner spinnerCourse;

    @BindView(R.id.searchCourse)
    Button searchCourse;

    @BindView(R.id.clearForm)
    Button clearForm;

    @BindView(R.id.home)
    Button home;

    @BindView(R.id.userInfo2)
    ImageButton userInfo;

    @BindView(R.id.range1)
    EditText startRange;

    @BindView(R.id.range2)
    EditText endRange;

    @BindView(R.id.searchCourses)
    Button searchCourses;

    @BindView(R.id.clearRange)
    Button clearRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.searchCourse)
    public void searchCourse() {
        if (term.getSelectedItem().toString().length() == 0) {
            Toast.makeText(this, "Please select a term",Toast.LENGTH_SHORT).show();
            return;
        }
        if (subject.getSelectedItem().toString().length() == 0) {
            Toast.makeText(this, "Please select a subject",Toast.LENGTH_SHORT).show();
            return;
        }
        if (spinnerCourse.getSelectedItem().toString().length() == 0) {
            Toast.makeText(this, "Please select a course",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(SearchActivity.this, CourseResultActivity.class);
        Bundle parameter = new Bundle();
        parameter.putString("courseNumber", spinnerCourse.getSelectedItem().toString());
        parameter.putString("term", term.getSelectedItem().toString());
        parameter.putString("subject", subject.getSelectedItem().toString());
        intent.putExtras(parameter);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.clearForm)
    public void clearForm() {
        term.setSelection(0);
        subject.setSelection(0);
        spinnerCourse.setSelection(0);
    }


    @OnClick(R.id.home)
    public void goBackToHomePage() {
        startActivity(new Intent(SearchActivity.this, MainMenuActivity.class));
    }


    @OnClick(R.id.userInfo2)
    public void checkUserInfo() {
        PopupMenu popup = new PopupMenu(SearchActivity.this, userInfo);
        Menu menu = popup.getMenu();
        popup.getMenuInflater()
                .inflate(R.menu.popup_menu,menu);
        MenuItem usernameItem = menu.getItem(0);
        usernameItem.setTitle("Username: " + MainMenuActivity.USERNAME);
        MenuItem logoutItem = menu.getItem(1);



        logoutItem.setOnMenuItemClickListener(menuItem -> {
            startActivity(new Intent(SearchActivity.this, SignInActivity.class));
            return true;
        });

        popup.show();
    }

    @OnClick(R.id.searchCourses)
    public void searchCoursesByRange() {
        String startText = startRange.getText().toString();
        String endText = endRange.getText().toString();
        if (startText.isEmpty() || endText.isEmpty()) {
            Toast.makeText(this, "Please fill the course number in the box", Toast.LENGTH_SHORT).show();
            return;
        }

        if (startText.length() != 4 || endText.length() != 4) {
            Toast.makeText(this, "The length of course number should be 4 digits", Toast.LENGTH_SHORT).show();
            return;
        }

        if (startText.charAt(0) == '0' || endText.charAt(0) == '0') {
            Toast.makeText(this, "The course number can not start with 0", Toast.LENGTH_SHORT).show();
            return;
        }

        int start = Integer.parseInt(startText);
        int end = Integer.parseInt(endText);

        if (start > end) {
            Toast.makeText(this, "Input range is invalid", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(SearchActivity.this, CourseListActivity.class);
        Bundle parameter = new Bundle();
        parameter.putString("startRange", startText);
        parameter.putString("endRange", endText);
        intent.putExtras(parameter);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.clearRange)
    public void clearRange() {
        startRange.setText("");
        endRange.setText("");
    }



}
