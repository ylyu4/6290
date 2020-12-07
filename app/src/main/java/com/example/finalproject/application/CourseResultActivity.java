package com.example.finalproject.application;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

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

    @BindView(R.id.instructorInfo)
    ImageButton instructorInfo;

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
            Instructor instructor = instructorDomainService.getInstructor(course.getInstructor());
            System.out.println(MainMenuActivity.USERNAME);
            if (instructor != null ) {
                if (instructor.getRateMyProfessorId() != null) {
                    String rateMyProfessorId = instructor.getRateMyProfessorId();
                    grade.setText(String.format("%s/5.0", HtmlParseUtil.getProfessorPoint(rateMyProfessorId)));
                    difficulty.setText(String.format("%s/5.0", HtmlParseUtil.getProfessorDifficulty(rateMyProfessorId)));
                }
            }
        }
    }

    @OnClick(R.id.addCourseToCart)
    public void addCourse() {
        if (cartDomainService.validateCourseIsInCart(MainMenuActivity.USERNAME, courseNumber.getText().toString())) {
            Toast.makeText(this, "Can't add this course because it is already in your cart", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cartDomainService.validateMaxNumOfAllCoursesInCart(MainMenuActivity.USERNAME)) {
            Toast.makeText(this, "You are only allowed to select 6 course in your cart", Toast.LENGTH_SHORT).show();
            return;
        }
        cartDomainService.addCourseInCart(MainMenuActivity.USERNAME, courseNumber.getText().toString());
        Toast.makeText(this, "The course is added in your cart",Toast.LENGTH_SHORT).show();
    }


    @SuppressLint("SetTextI18n")
    @OnClick(R.id.instructorInfo)
    public void checkInstructorInfo(View v1) {
        Instructor instructor1 = instructorDomainService.getInstructor(instructor.getText().toString());
        LayoutInflater inflater=LayoutInflater.from( this );
        @SuppressLint("InflateParams") View myView=inflater.inflate(R.layout.instructor_info_popup,null);//引用自定义布局
        LinearLayout linearLayout = (LinearLayout) myView.findViewById(R.id.popup);
        TextView name = new TextView(this);
        TextView phoneNumber = new TextView(this);
        TextView email = new TextView(this);
        TextView office = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        name.setLayoutParams(params);
        name.setText(String.format("Instructor name: %s", instructor1.getName()));
        name.setGravity(Gravity.CENTER);
        linearLayout.addView(name);

        phoneNumber.setLayoutParams(params);
        phoneNumber.setText(String.format("Instructor phone number: %s", instructor1.getPhoneNumber()));
        phoneNumber.setGravity(Gravity.CENTER);
        linearLayout.addView(phoneNumber);

        email.setLayoutParams(params);
        email.setText(String.format("Instructor email: %s", instructor1.getEmail()));
        email.setGravity(Gravity.CENTER);
        linearLayout.addView(email);

        office.setLayoutParams(params);
        office.setText(String.format("Instructor office: %s", instructor1.getOffice()));
        office.setGravity(Gravity.CENTER);
        linearLayout.addView(office);

        Button button = new Button(this);
        button.setText("Close");
        ViewGroup.LayoutParams buttonParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(buttonParams);
        button.setGravity(Gravity.CENTER);

        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.addView(button);

        PopupWindow popupWindow=new PopupWindow(myView,700,450 );//后面是像素大小

        button.setOnClickListener(view -> popupWindow.dismiss());

        popupWindow.showAtLocation(v1, Gravity.CENTER, 0, 0);

    }

    @OnClick(R.id.backToSearchPage)
    public void backToSearchPage() {
        startActivity(new Intent(CourseResultActivity.this, SearchActivity.class));
    }

    @OnClick(R.id.backToHomePage)
    public void setBackToHomePage() {
        startActivity(new Intent(CourseResultActivity.this, MainMenuActivity.class));
    }

    @OnClick(R.id.goToCartPage)
    public void goToCartPage() {
        startActivity(new Intent(CourseResultActivity.this, CartActivity.class));
    }
}
