package com.example.finalproject.application;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.finalproject.R;
import com.example.finalproject.model.Course;
import com.example.finalproject.model.Instructor;
import com.example.finalproject.service.CartDomainService;
import com.example.finalproject.service.CourseDomainService;
import com.example.finalproject.service.InstructorDomainService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseListActivity extends AppCompatActivity {

    private static int start;

    private static int end;

    private static String term;

    private static String subject;

    private static final String START_RANGE = "startRange";

    private static final String END_RANGE = "endRange";

    private static final String LOCATION_KEY = "location";

    private static final String LAST_PAGE_KEY = "lastPage";

    private static final String TERM_KEY = "term";

    private static final String SUBJECT_KEY = "subject";

    private final CourseDomainService courseDomainService = new CourseDomainService(this);

    private final CartDomainService cartDomainService = new CartDomainService(this);

    private final InstructorDomainService instructorDomainService = new InstructorDomainService(this);

    private static List<String> courseNumberList = new ArrayList<>(Arrays.asList("3119", "4121", "6200",
            "6201", "6202", "6204", "6205", "6206", "6209", "6210", "6217", "6218", "6222", "6223", "6225",
            "6233"));

    @BindView(R.id.courseList)
    LinearLayout courseListLayout;

    @BindView(R.id.backSearch)
    Button backSearch;

    @BindView(R.id.backHome)
    Button backHome;

    @BindView(R.id.goToCart)
    Button goCart;

    @BindView(R.id.userInfo5)
    ImageButton userInfo;

    @BindView(R.id.subjectTitle1)
    TextView subjectTitle;

    @BindView(R.id.termTitle1)
    TextView termTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_page);
        ButterKnife.bind(this);
        Bundle parameter = getIntent().getExtras();
        if (parameter != null) {
            start = Integer.parseInt(Objects.requireNonNull(parameter.get(START_RANGE)).toString());
            end = Integer.parseInt(Objects.requireNonNull(parameter.get(END_RANGE)).toString());
            term = Objects.requireNonNull(parameter.get(TERM_KEY)).toString();
            subject = Objects.requireNonNull(parameter.get(SUBJECT_KEY)).toString();
        }
        showCourseList();
    }



    @SuppressLint("ResourceAsColor")
    public void showCourseList() {
        termTitle.setText(term);
        subjectTitle.setText(subject);
        for (int i = start; i <= end; i++) {
            String numberString = String.valueOf(i);
            if (courseNumberList.contains(numberString)) {
                Course course = courseDomainService.getCourse(numberString);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);


                LinearLayout courseNumberLinearLayout = new LinearLayout(this);
                courseNumberLinearLayout.setLayoutParams(params);

                TextView courseNumberText = new TextView(this);
                LinearLayout.LayoutParams courseNumberTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                courseNumberTextParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                courseNumberTextParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                courseNumberText.setLayoutParams(courseNumberTextParams);
                courseNumberText.setText("Course Number: ");
                courseNumberText.setTypeface(courseNumberText.getTypeface(), Typeface.BOLD);
                courseNumberLinearLayout.addView(courseNumberText);

                TextView courseNumber = new TextView(this);
                LinearLayout.LayoutParams courseNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                courseNumberParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                courseNumber.setLayoutParams(courseNumberParams);
                courseNumber.setText(course.getCourseNumber());
                courseNumberLinearLayout.addView(courseNumber);

                courseListLayout.addView(courseNumberLinearLayout);


                LinearLayout courseDescriptionLinearLayout = new LinearLayout(this);
                courseDescriptionLinearLayout.setLayoutParams(params);

                TextView courseDescriptionText = new TextView(this);
                LinearLayout.LayoutParams courseDescriptionTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                courseDescriptionTextParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
                courseDescriptionTextParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                courseDescriptionText.setLayoutParams(courseDescriptionTextParams);
                courseDescriptionText.setText("Description: ");
                courseDescriptionText.setTypeface(courseNumberText.getTypeface(), Typeface.BOLD);
                courseDescriptionLinearLayout.addView(courseDescriptionText);

                TextView courseDescription = new TextView(this);
                LinearLayout.LayoutParams courseDescriptionParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                courseDescriptionParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
                courseDescription.setLayoutParams(courseDescriptionParams);
                courseDescription.setText(course.getDescription());
                courseDescriptionLinearLayout.addView(courseDescription);

                courseListLayout.addView(courseDescriptionLinearLayout);


                HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this);
                horizontalScrollView.setLayoutParams(params);

                LinearLayout firstHorizontalLinearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams horizontalLinearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                horizontalLinearLayoutParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                firstHorizontalLinearLayout.setLayoutParams(horizontalLinearLayoutParams);
                firstHorizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout firstVerticalLinearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams verticalLinearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                firstVerticalLinearLayout.setLayoutParams(verticalLinearLayoutParams);
                firstVerticalLinearLayout.setOrientation(LinearLayout.VERTICAL);

                TextView crnText = new TextView(this);
                LinearLayout.LayoutParams crnTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                crnTextParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                crnText.setLayoutParams(crnTextParams);
                crnText.setText("CRN");
                crnText.setTypeface(crnText.getTypeface(), Typeface.BOLD);
                firstVerticalLinearLayout.addView(crnText);

                TextView crn = new TextView(this);
                LinearLayout.LayoutParams crnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                crnParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                crn.setLayoutParams(crnParams);
                crn.setText(course.getCrn());
                firstVerticalLinearLayout.addView(crn);

                TextView instructorText = new TextView(this);
                LinearLayout.LayoutParams instructorTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                instructorTextParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                instructorTextParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
                instructorText.setLayoutParams(instructorTextParams);
                instructorText.setText("Instructor");
                instructorText.setTypeface(instructorText.getTypeface(), Typeface.BOLD);
                firstVerticalLinearLayout.addView(instructorText);

                LinearLayout secondHorizontalLinearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams secondHorizontalLinearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                secondHorizontalLinearLayout.setLayoutParams(secondHorizontalLinearLayoutParams);
                secondHorizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

                TextView instructor = new TextView(this);
                LinearLayout.LayoutParams instructorParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                instructorParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                instructor.setLayoutParams(instructorParams);
                instructor.setText(course.getInstructor());
                secondHorizontalLinearLayout.addView(instructor);

                Button instructorImageButton = new Button(this);
                LinearLayout.LayoutParams instructorImageButtonParams = new LinearLayout.LayoutParams(((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics())),
                        (((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()))));
                instructorImageButton.setLayoutParams(instructorImageButtonParams);
                instructorImageButton.setBackgroundResource(R.drawable.professor_icon);
                secondHorizontalLinearLayout.addView(instructorImageButton);


                instructorImageButton.setOnClickListener(view -> {
                    Instructor instructorObject = instructorDomainService.getInstructor(course.getInstructor());
                    LayoutInflater inflater=LayoutInflater.from( this );
                    @SuppressLint("InflateParams") View myView=inflater.inflate(R.layout.instructor_info_popup,null);
                    LinearLayout linearLayout = (LinearLayout) myView.findViewById(R.id.popup);
                    TextView name = new TextView(this);
                    TextView phoneNumber = new TextView(this);
                    TextView email = new TextView(this);
                    TextView office = new TextView(this);
                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    name.setLayoutParams(p);
                    name.setText(String.format("Instructor name: %s", instructorObject.getName()));
                    name.setGravity(Gravity.CENTER);
                    linearLayout.addView(name);

                    phoneNumber.setLayoutParams(p);
                    phoneNumber.setText(String.format("Instructor phone number: %s", instructorObject.getPhoneNumber()));
                    phoneNumber.setGravity(Gravity.CENTER);
                    linearLayout.addView(phoneNumber);

                    email.setLayoutParams(p);
                    email.setText(String.format("Instructor email: %s", instructorObject.getEmail()));
                    email.setGravity(Gravity.CENTER);
                    linearLayout.addView(email);

                    office.setLayoutParams(p);
                    office.setText(String.format("Instructor office: %s", instructorObject.getOffice()));
                    office.setGravity(Gravity.CENTER);
                    linearLayout.addView(office);

                    Button button = new Button(this);
                    button.setText("Close");
                    ViewGroup.LayoutParams buttonParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    button.setBackgroundResource(R.drawable.button_shape);
                    button.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
                    button.setLayoutParams(buttonParams);
                    button.setGravity(Gravity.CENTER);

                    linearLayout.setGravity(Gravity.CENTER);
                    linearLayout.addView(button);

                    PopupWindow popupWindow=new PopupWindow(myView,700,450 );

                    button.setOnClickListener(v1 -> popupWindow.dismiss());

                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                });

                firstVerticalLinearLayout.addView(secondHorizontalLinearLayout);
                firstHorizontalLinearLayout.addView(firstVerticalLinearLayout);


                LinearLayout secondVerticalLinearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams secondVerticalLinearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                secondVerticalLinearLayout.setLayoutParams(secondVerticalLinearLayoutParams);
                secondVerticalLinearLayout.setOrientation(LinearLayout.VERTICAL);

                TextView titleText = new TextView(this);
                LinearLayout.LayoutParams titleTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                titleTextParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                titleText.setLayoutParams(titleTextParams);
                titleText.setText("Title");
                titleText.setTypeface(titleText.getTypeface(), Typeface.BOLD);
                secondVerticalLinearLayout.addView(titleText);

                TextView title = new TextView(this);
                LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                titleParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                title.setLayoutParams(titleParams);
                title.setText(course.getTitle());
                secondVerticalLinearLayout.addView(title);


                TextView sectionText = new TextView(this);
                LinearLayout.LayoutParams sectionTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                sectionTextParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                sectionTextParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
                sectionText.setLayoutParams(titleTextParams);
                sectionText.setText("Section");
                sectionText.setTypeface(titleText.getTypeface(), Typeface.BOLD);
                secondVerticalLinearLayout.addView(sectionText);

                TextView section = new TextView(this);
                LinearLayout.LayoutParams sectionParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                sectionParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                section.setLayoutParams(sectionParams);
                section.setText(course.getSection());
                secondVerticalLinearLayout.addView(section);

                firstHorizontalLinearLayout.addView(secondVerticalLinearLayout);


                LinearLayout thirdVerticalLinearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams thirdVerticalLinearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                thirdVerticalLinearLayout.setLayoutParams(thirdVerticalLinearLayoutParams);
                thirdVerticalLinearLayout.setOrientation(LinearLayout.VERTICAL);

                TextView dayText = new TextView(this);
                LinearLayout.LayoutParams dayTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                dayTextParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                dayText.setLayoutParams(dayTextParams);
                dayText.setText("Day");
                dayText.setTypeface(dayText.getTypeface(), Typeface.BOLD);
                thirdVerticalLinearLayout.addView(dayText);

                TextView day = new TextView(this);
                LinearLayout.LayoutParams dayParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                dayParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                day.setLayoutParams(dayParams);
                day.setText(course.getTitle());
                thirdVerticalLinearLayout.addView(day);


                TextView creditHourText = new TextView(this);
                LinearLayout.LayoutParams creditHourTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                creditHourTextParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                creditHourTextParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
                creditHourText.setLayoutParams(creditHourTextParams);
                creditHourText.setText("Credit Hour");
                creditHourText.setTypeface(creditHourText.getTypeface(), Typeface.BOLD);
                thirdVerticalLinearLayout.addView(creditHourText);

                TextView creditHour = new TextView(this);
                LinearLayout.LayoutParams creditHourParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                creditHourParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                creditHour.setLayoutParams(creditHourParams);
                creditHour.setText(course.getCreditHours());
                thirdVerticalLinearLayout.addView(creditHour);

                firstHorizontalLinearLayout.addView(thirdVerticalLinearLayout);


                LinearLayout forthVerticalLinearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams forthVerticalLinearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                forthVerticalLinearLayout.setLayoutParams(forthVerticalLinearLayoutParams);
                forthVerticalLinearLayout.setOrientation(LinearLayout.VERTICAL);

                TextView timeText = new TextView(this);
                LinearLayout.LayoutParams timeTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                timeTextParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                timeText.setLayoutParams(timeTextParams);
                timeText.setText("Time");
                timeText.setTypeface(timeText.getTypeface(), Typeface.BOLD);
                forthVerticalLinearLayout.addView(timeText);

                TextView time = new TextView(this);
                LinearLayout.LayoutParams timeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                timeParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                time.setLayoutParams(timeParams);
                time.setText(String.format("%s-%s", course.getStartTime(), course.getEndTime()));
                forthVerticalLinearLayout.addView(time);

                TextView locationText = new TextView(this);
                LinearLayout.LayoutParams locationTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                locationTextParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                locationTextParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
                locationText.setLayoutParams(locationTextParams);
                locationText.setText("Location");
                locationText.setTypeface(locationText.getTypeface(), Typeface.BOLD);
                forthVerticalLinearLayout.addView(locationText);

                LinearLayout thirdHorizontalLinearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams thirdHorizontalLinearLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                thirdHorizontalLinearLayout.setLayoutParams(thirdHorizontalLinearLayoutParam);
                thirdHorizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

                TextView location = new TextView(this);
                LinearLayout.LayoutParams locationParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                locationParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                location.setLayoutParams(locationParams);
                location.setText(course.getLocation());
                thirdHorizontalLinearLayout.addView(location);

                Button locationImageButton = new Button(this);
                LinearLayout.LayoutParams locationImageButtonParams = new LinearLayout.LayoutParams(((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics())),
                        (((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()))));
                locationImageButton.setLayoutParams(locationImageButtonParams);
                locationImageButton.setBackgroundResource(R.drawable.map);
                thirdHorizontalLinearLayout.addView(locationImageButton);

                locationImageButton.setOnClickListener(view -> {
                    try {
                        Intent intent = new Intent(CourseListActivity.this, MapActivity.class);
                        Bundle parameter = new Bundle();
                        parameter.putString(LOCATION_KEY, course.getLocation());
                        parameter.putString(LAST_PAGE_KEY, "courseList");
                        intent.putExtras(parameter);
                        startActivity(intent);
                        finish();
                    } catch (Exception ex) {
                        Toast.makeText(this, "This course is an online course", Toast.LENGTH_SHORT).show();
                        return;
                    }

                });

                forthVerticalLinearLayout.addView(thirdHorizontalLinearLayout);
                firstHorizontalLinearLayout.addView(forthVerticalLinearLayout);
                horizontalScrollView.addView(firstHorizontalLinearLayout);
                courseListLayout.addView(horizontalScrollView);

                Button addCourseButton = new Button(this);
                LinearLayout.LayoutParams addCourseButtonParams = new LinearLayout.LayoutParams(((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics())),
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                addCourseButtonParams.gravity = Gravity.CENTER;
                addCourseButton.setBackgroundResource(R.drawable.button_shape);
                addCourseButton.setText("Add To Cart");
                addCourseButton.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
                addCourseButton.setLayoutParams(addCourseButtonParams);

                addCourseButton.setOnClickListener(view -> {
                    if (cartDomainService.validateCourseIsInCart(MainMenuActivity.USERNAME, courseNumber.getText().toString())) {
                        Toast.makeText(this, "Can't add this course because it is already in your cart", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (cartDomainService.validateMaxNumOfAllCoursesInCart(MainMenuActivity.USERNAME)) {
                        Toast.makeText(this, "You are only allowed to select 6 courses in your cart", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    cartDomainService.addCourseInCart(MainMenuActivity.USERNAME, courseNumber.getText().toString());
                    Toast.makeText(this, "The course is added to your cart",Toast.LENGTH_SHORT).show();
                });
                courseListLayout.addView(addCourseButton);

                TextView line = new TextView(this);
                LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, getResources().getDisplayMetrics())),
                        (((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()))));
                lineParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
                lineParams.gravity = Gravity.CENTER;
                line.setBackgroundColor(R.color.colorGrey);
                line.setLayoutParams(lineParams);
                courseListLayout.addView(line);
            }
        }
    }

    @OnClick(R.id.backSearch)
    public void backToSearchPage() {
        startActivity(new Intent(CourseListActivity.this, SearchActivity.class));
    }

    @OnClick(R.id.backHome)
    public void setBackToHomePage() {
        startActivity(new Intent(CourseListActivity.this, MainMenuActivity.class));
    }

    @OnClick(R.id.goToCart)
    public void goToCartPage() {
        startActivity(new Intent(CourseListActivity.this, CartActivity.class));
    }

    @OnClick(R.id.userInfo5)
    public void checkUserInfo() {
        PopupMenu popup = new PopupMenu(CourseListActivity.this, userInfo);
        Menu menu = popup.getMenu();
        popup.getMenuInflater()
                .inflate(R.menu.popup_menu,menu);
        MenuItem usernameItem = menu.getItem(0);
        usernameItem.setTitle("Username: " + MainMenuActivity.USERNAME);
        MenuItem logoutItem = menu.getItem(1);



        logoutItem.setOnMenuItemClickListener(menuItem -> {
            startActivity(new Intent(CourseListActivity.this, LoginActivity.class));
            return true;
        });

        popup.show();
    }

}
