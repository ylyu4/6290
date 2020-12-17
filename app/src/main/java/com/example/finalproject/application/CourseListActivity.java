package com.example.finalproject.application;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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

    private static final String START_RANGE = "startRange";

    private static final String END_RANGE = "endRange";

    private static final String LOCATION_KEY = "location";

    private static final String LAST_PAGE_KEY = "lastPage";

    private final CourseDomainService courseDomainService = new CourseDomainService(this);

    private final CartDomainService cartDomainService = new CartDomainService(this);

    private final InstructorDomainService instructorDomainService = new InstructorDomainService(this);

    private static List<String> courseNumberList = new ArrayList<>(Arrays.asList("3119", "4121", "6200",
            "6201", "6202", "6204", "6205", "6206", "6209", "6210", "6217", "6218", "6222", "6223", "6225",
            "6233"));

    @BindView(R.id.courseListLayout)
    LinearLayout courseListLayout;

    @BindView(R.id.backSearch)
    Button backSearch;

    @BindView(R.id.backHome)
    Button backHome;

    @BindView(R.id.goToCart)
    Button goCart;

    @BindView(R.id.userInfo5)
    ImageButton userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_page);
        ButterKnife.bind(this);
        Bundle parameter = getIntent().getExtras();
        if (parameter != null) {
            start = Integer.parseInt(Objects.requireNonNull(parameter.get(START_RANGE)).toString());
            end = Integer.parseInt(Objects.requireNonNull(parameter.get(END_RANGE)).toString());
        }
        showCourseList();
    }



    public void showCourseList() {
        for (int i = start; i <= end; i++) {
            String numberString = String.valueOf(i);
            if (courseNumberList.contains(numberString)) {
                Course course = courseDomainService.getCourse(numberString);
                LinearLayout linearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER;
                linearLayout.setLayoutParams(params);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                TextView classNumberView = new TextView(this);
                classNumberView.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics()));
                classNumberView.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, getResources().getDisplayMetrics()));
                classNumberView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                classNumberView.setGravity(Gravity.CENTER);
                classNumberView.setText(String.valueOf(i));
                linearLayout.addView(classNumberView);

                TextView classTitleView = new TextView(this);
                classTitleView.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, getResources().getDisplayMetrics()));
                classTitleView.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, getResources().getDisplayMetrics()));
                classTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                classTitleView.setGravity(Gravity.CENTER);
                classTitleView.setText(course.getTitle());
                linearLayout.addView(classTitleView);

                TextView classProfessorView = new TextView(this);
                classProfessorView.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics()));
                classProfessorView.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, getResources().getDisplayMetrics()));
                classProfessorView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                classProfessorView.setGravity(Gravity.CENTER);
                classProfessorView.setText(course.getInstructor());
                classProfessorView.setOnClickListener(view -> {
                    showInstructorPopup((TextView) view);
                });
                linearLayout.addView(classProfessorView);

                TextView classDayView = new TextView(this);
                classDayView.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()));
                classDayView.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, getResources().getDisplayMetrics()));
                classDayView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                classDayView.setGravity(Gravity.CENTER);
                classDayView.setText(course.getDay());
                linearLayout.addView(classDayView);

                TextView classTimeView = new TextView(this);
                classTimeView.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics()));
                classTimeView.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, getResources().getDisplayMetrics()));
                classTimeView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                classTimeView.setGravity(Gravity.CENTER);
                classTimeView.setText(String.format("%s-%s", course.getStartTime(), course.getEndTime()));
                linearLayout.addView(classTimeView);

                ImageButton mapView = new ImageButton(this);
                mapView.setBackgroundResource(R.drawable.map);
                LinearLayout.LayoutParams mapParams = new LinearLayout.LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()),
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, getResources().getDisplayMetrics()));
                mapView.setLayoutParams(mapParams);
                mapView.setOnClickListener(view -> {
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
                linearLayout.addView(mapView);

                Button addCourseButton = new Button(this);
                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics()),
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, getResources().getDisplayMetrics()));
                addCourseButton.setText("Add");
                addCourseButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                addCourseButton.setLayoutParams(buttonParams);
                addCourseButton.setOnClickListener(view -> {
                    if (cartDomainService.validateCourseIsInCart(MainMenuActivity.USERNAME, classNumberView.getText().toString())) {
                        Toast.makeText(this, "Can't add this course because it is already in your cart", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (cartDomainService.validateMaxNumOfAllCoursesInCart(MainMenuActivity.USERNAME)) {
                        Toast.makeText(this, "You are only allowed to select 6 courses in your cart", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    cartDomainService.addCourseInCart(MainMenuActivity.USERNAME, classNumberView.getText().toString());
                    Toast.makeText(this, "The course is added to your cart",Toast.LENGTH_SHORT).show();
                });
                linearLayout.addView(addCourseButton);

                courseListLayout.addView(linearLayout);
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
            startActivity(new Intent(CourseListActivity.this, SignInActivity.class));
            return true;
        });

        popup.show();
    }

    public void showInstructorPopup(TextView view) {
        Instructor instructor = instructorDomainService.getInstructor(view.getText().toString());
        LayoutInflater inflater=LayoutInflater.from( this );
        @SuppressLint("InflateParams") View myView=inflater.inflate(R.layout.instructor_info_popup,null);
        LinearLayout linearLayout = (LinearLayout) myView.findViewById(R.id.popup);
        TextView name = new TextView(this);
        TextView phoneNumber = new TextView(this);
        TextView email = new TextView(this);
        TextView office = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        name.setLayoutParams(params);
        name.setText(String.format("Instructor name: %s", instructor.getName()));
        name.setGravity(Gravity.CENTER);
        linearLayout.addView(name);

        phoneNumber.setLayoutParams(params);
        phoneNumber.setText(String.format("Instructor phone number: %s", instructor.getPhoneNumber()));
        phoneNumber.setGravity(Gravity.CENTER);
        linearLayout.addView(phoneNumber);

        email.setLayoutParams(params);
        email.setText(String.format("Instructor email: %s", instructor.getEmail()));
        email.setGravity(Gravity.CENTER);
        linearLayout.addView(email);

        office.setLayoutParams(params);
        office.setText(String.format("Instructor office: %s", instructor.getOffice()));
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

        PopupWindow popupWindow=new PopupWindow(myView,700,450 );

        button.setOnClickListener(v1 -> popupWindow.dismiss());

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

}
