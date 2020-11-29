package com.example.finalproject.application;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject.R;
import com.example.finalproject.model.Course;
import com.example.finalproject.model.Instructor;
import com.example.finalproject.service.CartDomainService;
import com.example.finalproject.service.CourseDomainService;
import com.example.finalproject.service.InstructorDomainService;
import com.example.finalproject.utils.HtmlParseUtil;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.SneakyThrows;

public class CourseResultActivity extends AppCompatActivity {

    private final CourseDomainService courseDomainService = new CourseDomainService(this);

    private final InstructorDomainService instructorDomainService = new InstructorDomainService(this);

    private final CartDomainService cartDomainService = new CartDomainService(this);

    private static final String COURSE_NUMBER_KEY = "courseNumber";

    private static final String TERM_KEY = "term";

    private static final String SUBJECT_KEY = "subject";

    @BindView(R.id.termTitle)
    TextView termTitle;

    @BindView(R.id.subjectTitle)
    TextView subjectTitle;


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

    @BindView(R.id.addCourseToCart)
    Button addCourseToCart;

    @BindView(R.id.backToSearchPage)
    Button backToSearchPage;

    @BindView(R.id.backToHomePage)
    Button backToHomePage;

    @BindView(R.id.goToCartPage)
    Button goToCartPage;

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.course_result_page);
        ButterKnife.bind(this);
        Bundle parameter = getIntent().getExtras();
        if (parameter == null) {
            startActivity(new Intent(CourseResultActivity.this, SearchActivity.class));
        } else {
            String courseNum = Objects.requireNonNull(parameter.get(COURSE_NUMBER_KEY)).toString();
            termTitle.setText(String.format("Term: %s", Objects.requireNonNull(parameter.get(TERM_KEY)).toString()));
            subjectTitle.setText(String.format("Subject: %s", Objects.requireNonNull(parameter.get(SUBJECT_KEY)).toString()));
            getResult(courseNum);
        }
    }

    @SneakyThrows
    public void getResult(String courseNum) {
        Course course = courseDomainService.getCourse(courseNum);
        if (course != null) {
            courseNumber.setText(String.format("Course Number: %s", course.getCourseNumber()));
            title.setText(String.format("Title: %s", course.getTitle()));
            creditHours.setText(String.format("Credit Hours: %s", course.getCreditHours()));
            description.setText(String.format("Description: %s", course.getDescription()));
            crn.setText(course.getCrn());
            instructor.setText(course.getInstructor());
            section.setText(course.getSection());
            day.setText(course.getDay());
            time.setText(String.format("%s-%s", course.getStartTime(), course.getEndTime()));
            location.setText(course.getLocation());
            Instructor instructor = instructorDomainService.getInstructor(course.getInstructor());
            if (instructor != null) {
                String rateMyProfessorId = instructor.getRateMyProfessorId();
                grade.setText(String.format("%s/5.0", HtmlParseUtil.getProfessorPoint(rateMyProfessorId)));
                difficulty.setText(String.format("%s/5.0", HtmlParseUtil.getProfessorDifficulty(rateMyProfessorId)));
            }
        }
    }

    @OnClick(R.id.addCourseToCart)
    public void addCourse() {
        cartDomainService.addCourseInCart(MainMenuActivity.USERNAME, courseNumber.getText().toString());
    }

    @OnClick(R.id.backToSearchPage)
    public void backToSearchPage() {
        startActivity(new Intent(CourseResultActivity.this, SearchActivity.class));
    }

    @OnClick(R.id.backToHomePage)
    public void setBackToHomePage() {
        startActivity(new Intent(CourseResultActivity.this, MainMenuActivity.class));
    }
}
