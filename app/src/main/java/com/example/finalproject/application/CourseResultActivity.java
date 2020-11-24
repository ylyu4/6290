package com.example.finalproject.application;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseResultActivity extends AppCompatActivity {

    @BindView(R.id.courseNumber)
    TextView courseNumber;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.creditHours)
    TextView creditHours;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.crn)
    TextView crn;

    @BindView(R.id.instructor)
    TextView instructor;

    @BindView(R.id.section)
    TextView section;

    @BindView(R.id.grade)
    TextView grade;

    @BindView(R.id.day)
    TextView day;

    @BindView(R.id.difficulty)
    TextView difficulty;

    @BindView(R.id.time)
    TextView time;

    @BindView(R.id.location)
    TextView location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_result_page);
        ButterKnife.bind(this);
    }


}
