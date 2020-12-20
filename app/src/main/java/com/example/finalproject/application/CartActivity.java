package com.example.finalproject.application;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.finalproject.R;
import com.example.finalproject.model.Cart;
import com.example.finalproject.model.Course;
import com.example.finalproject.service.CartDomainService;
import com.example.finalproject.service.CourseDomainService;
import com.example.finalproject.service.UserDomainService;
import com.example.finalproject.utils.TextUtil;
import com.example.finalproject.utils.TimeUtil;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartActivity extends AppCompatActivity {


    private final CartDomainService cartDomainService = new CartDomainService(this);

    private final CourseDomainService courseDomainService = new CourseDomainService(this);

    private final UserDomainService userDomainService = new UserDomainService(this);

    private static final String COURSE_NUMBER_KEY = "courseNumber";

    private static final String TERM_KEY = "term";

    private static final String SUBJECT_KEY = "subject";

    private static final String START_RANGE_KEY = "startRange";

    private static final String END_RANGE_KEY = "endRange";

    private static final String FLAG_KEY = "flag";

    private int flag;


    @BindView(R.id.cartLayout)
    LinearLayout cartLayout;

    @BindView(R.id.registerCourses)
    Button registerCourses;

    @BindView(R.id.backSearchPage)
    Button back;

    @BindView(R.id.backHomePage)
    Button home;

    @BindView(R.id.goToSchedulePage)
    Button next;

    @BindView(R.id.userInfo4)
    ImageButton userInfo;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_cart_page);
        ButterKnife.bind(this);
        Bundle parameter = getIntent().getExtras();
        if (parameter != null) {
            flag = Integer.parseInt(Objects.requireNonNull(parameter.get(FLAG_KEY)).toString());
        }
        createCartUI();
    }


    // Create cart user interface
    @SuppressLint({"ResourceType", "SetTextI18n"})
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createCartUI() {
        if (cartDomainService.validateUserExistInCart(MainMenuActivity.USERNAME) && cartDomainService.getCouseList(MainMenuActivity.USERNAME) != null) {
            if (cartDomainService.getCouseList(MainMenuActivity.USERNAME).size() > 0) {
                List<Course> courseList = cartDomainService.getCouseList(MainMenuActivity.USERNAME).stream()
                        .map(courseDomainService::getCourse).collect(Collectors.toList());
                for (int i = 0; i < courseList.size(); i++) {
                    LinearLayout linearLayout = new LinearLayout(this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    linearLayout.setLayoutParams(params);
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout.setId(i);
                    TextView crnTextView = new TextView(this);
                    crnTextView.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics()));
                    crnTextView.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
                    crnTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    crnTextView.setGravity(Gravity.CENTER);
                    crnTextView.setText(TextUtil.parseOnlineCrn(courseList.get(i).getCrn()));
                    linearLayout.addView(crnTextView);

                    TextView classTextView = new TextView(this);
                    classTextView.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics()));
                    classTextView.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
                    classTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    classTextView.setGravity(Gravity.CENTER);
                    classTextView.setText(courseList.get(i).getCourseNumber());
                    linearLayout.addView(classTextView);

                    TextView titleTextView = new TextView(this);
                    titleTextView.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 95, getResources().getDisplayMetrics()));
                    titleTextView.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
                    titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    titleTextView.setGravity(Gravity.CENTER);
                    titleTextView.setText(courseList.get(i).getTitle());
                    linearLayout.addView(titleTextView);

                    TextView dayTextView = new TextView(this);
                    dayTextView.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, getResources().getDisplayMetrics()));
                    dayTextView.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
                    dayTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    dayTextView.setGravity(Gravity.CENTER);
                    dayTextView.setText(courseList.get(i).getDay());
                    linearLayout.addView(dayTextView);

                    TextView timeTextView = new TextView(this);
                    timeTextView.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, getResources().getDisplayMetrics()));
                    timeTextView.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
                    timeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    timeTextView.setGravity(Gravity.CENTER);
                    timeTextView.setText(String.format("%s-%s", courseList.get(i).getStartTime(), courseList.get(i).getEndTime()));
                    linearLayout.addView(timeTextView);

                    TextView sectionTextView = new TextView(this);
                    sectionTextView.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65, getResources().getDisplayMetrics()));
                    sectionTextView.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
                    sectionTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    sectionTextView.setGravity(Gravity.CENTER);
                    sectionTextView.setText(courseList.get(i).getSection());
                    linearLayout.addView(sectionTextView);

                    TextView typeTextView = new TextView(this);
                    typeTextView.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65, getResources().getDisplayMetrics()));
                    typeTextView.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
                    typeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    typeTextView.setGravity(Gravity.CENTER);
                    typeTextView.setText(courseList.get(i).getCrn().length() == 5 ? "Lecture" : "Online/Discussion");
                    linearLayout.addView(typeTextView);

                    Button courseButton = new Button(this);
                    courseButton.setBackgroundResource(R.drawable.button_shape);
                    courseButton.setText("Drop");
                    courseButton.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
                    courseButton.setOnClickListener(view -> {
                        linearLayout.removeAllViews();
                        cartDomainService.dropCourse(MainMenuActivity.USERNAME, classTextView.getText().toString());
                    });
                    linearLayout.addView(courseButton);

                    cartLayout.addView(linearLayout);
                }
            } else {
                TextView emptyCartTextView = new TextView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                emptyCartTextView.setLayoutParams(params);
                emptyCartTextView.setGravity(Gravity.CENTER);
                emptyCartTextView.setText("Your cart is empty");
                cartLayout.addView(emptyCartTextView);
            }

        } else {
            TextView emptyCartTextView = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            emptyCartTextView.setLayoutParams(params);
            emptyCartTextView.setGravity(Gravity.CENTER);
            emptyCartTextView.setText("Your cart is empty");
            cartLayout.addView(emptyCartTextView);
        }

    }

    // register courses and generate the schedule
    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick(R.id.registerCourses)
    public void registerCourses() {
        Cart cart = cartDomainService.getCart(MainMenuActivity.USERNAME);
        if (cart == null || cart.getCourseInCart() == null || cart.getCourseInCart().size() == 0) {
            Toast.makeText(this, "Can't register because course cart is empty",Toast.LENGTH_LONG).show();
        } else {
            List<Course> courseList = cartDomainService.getCouseList(MainMenuActivity.USERNAME).stream()
                    .map(courseDomainService::getCourse).collect(Collectors.toList());
            if (TimeUtil.validateTimeConflicts(courseList)) {
                Toast.makeText(this, "Can't register because course time conflict",Toast.LENGTH_LONG).show();
                return;
            }
            StringBuilder reminderMessage = new StringBuilder();
            for (Course course : courseList) {
                String prerequisites = course.getPrerequisites();
                if (!prerequisites.equals("No")) {
                    reminderMessage.append(course.getCourseNumber()).append(" has ").append(prerequisites).append(" as prerequisites. \n");
                }
            }
            userDomainService.updateUserRegisteredCourse(cart);
            cartDomainService.checkOutCourseInCart(MainMenuActivity.USERNAME);
            Toast.makeText(this, reminderMessage.toString(), Toast.LENGTH_LONG).show();
            startActivity(new Intent(CartActivity.this, ScheduleActivity.class));
        }
    }

    // go back to the last activity you were at
    @OnClick(R.id.backSearchPage)
    public void backResultPage() {
        if (flag == 0) {
            Intent intent = new Intent(CartActivity.this, CourseResultActivity.class);
            Bundle parameter = new Bundle();
            parameter.putString(COURSE_NUMBER_KEY, CourseResultActivity.courseNum);
            parameter.putString(TERM_KEY, CourseResultActivity.term);
            parameter.putString(SUBJECT_KEY, CourseResultActivity.subject);
            intent.putExtras(parameter);
            startActivity(intent);
            finish();
        } else if (flag == 1) {
            Intent intent = new Intent(CartActivity.this, CourseListActivity.class);
            Bundle parameter = new Bundle();
            parameter.putString(START_RANGE_KEY, String.valueOf(CourseListActivity.start));
            parameter.putString(END_RANGE_KEY, String.valueOf(CourseListActivity.end));
            parameter.putString(TERM_KEY, CourseListActivity.term);
            parameter.putString(SUBJECT_KEY, CourseListActivity.subject);
            intent.putExtras(parameter);
            startActivity(intent);
            finish();
        } else {
            startActivity(new Intent(CartActivity.this, SearchActivity.class));
        }

    }

    // go back to main menu
    @OnClick(R.id.backHomePage)
    public void backHomePage() {
        startActivity(new Intent(CartActivity.this, MainMenuActivity.class));
    }

    // go to the schedule page
    @OnClick(R.id.goToSchedulePage)
    public void goToSchedulePage() {
        startActivity(new Intent(CartActivity.this, ScheduleActivity.class));
    }


    // check current user's information
    @OnClick(R.id.userInfo4)
    public void checkUserInfo() {
        PopupMenu popup = new PopupMenu(CartActivity.this, userInfo);
        Menu menu = popup.getMenu();
        popup.getMenuInflater()
                .inflate(R.menu.popup_menu,menu);
        MenuItem usernameItem = menu.getItem(0);
        usernameItem.setTitle("Username: " + MainMenuActivity.USERNAME);
        MenuItem logoutItem = menu.getItem(1);

        logoutItem.setOnMenuItemClickListener(menuItem -> {
            startActivity(new Intent(CartActivity.this, LoginActivity.class));
            return true;
        });

        popup.show();
    }

}
