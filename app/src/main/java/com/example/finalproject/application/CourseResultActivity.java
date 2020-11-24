package com.example.finalproject.application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject.R;
import com.example.finalproject.model.Course;
import com.example.finalproject.service.CourseDomainService;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseResultActivity extends AppCompatActivity {

    private final CourseDomainService courseDomainService = new CourseDomainService(this);

    private static final String KEY = "courseNumber";

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
        Bundle parameter = getIntent().getExtras();
        if (parameter == null) {
            startActivity(new Intent(CourseResultActivity.this, SearchActivity.class));
        } else {
            String courseNum = Objects.requireNonNull(parameter.get(KEY)).toString();
            getResult(courseNum);
        }
    }

    public void getResult(String courseNum) {
        Course course = courseDomainService.getCourse(courseNum);
        courseNumber.setText(course.getCourseNumber());
        title.setText(course.getTitle());
        creditHours.setText(course.getCreditHours());
        description.setText(course.getDescription());
        crn.setText(course.getCrn());
        instructor.setText(course.getInstructor());
        section.setText(course.getSection());
        day.setText(course.getDay());
        time.setText(String.format("%s-%s", course.getStartTime(), course.getEndTime()));
        location.setText(course.getLocation());
    }


}
