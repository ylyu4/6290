package com.example.finalproject.application;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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





}
