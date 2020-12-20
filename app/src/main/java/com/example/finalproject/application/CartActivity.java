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
import com.example.finalproject.R;
import com.example.finalproject.model.Cart;
import com.example.finalproject.model.Course;
import com.example.finalproject.service.CartDomainService;
import com.example.finalproject.service.CourseDomainService;
import com.example.finalproject.service.UserDomainService;
import com.example.finalproject.utils.TextUtil;
import com.example.finalproject.utils.TimeUtil;

import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartActivity extends AppCompatActivity {


    private final CartDomainService cartDomainService = new CartDomainService(this);

    private final CourseDomainService courseDomainService = new CourseDomainService(this);

    private final UserDomainService userDomainService = new UserDomainService(this);


    @BindView(R.id.courseCartLayout)
    LinearLayout courseCartLayout;

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
        createCartUI();
    }

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
                    courseButton.setTextColor(R.color.colorWhite);
                    courseButton.setOnClickListener(view -> {
                        linearLayout.removeAllViews();
                        cartDomainService.dropCourse(MainMenuActivity.USERNAME, classTextView.getText().toString());
                    });
                    linearLayout.addView(courseButton);

                    courseCartLayout.addView(linearLayout);
                }
            } else {
                TextView emptyCartTextView = new TextView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                emptyCartTextView.setLayoutParams(params);
                emptyCartTextView.setGravity(Gravity.CENTER);
                emptyCartTextView.setText("Your cart is empty");
                courseCartLayout.addView(emptyCartTextView);
            }

        } else {
            TextView emptyCartTextView = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            emptyCartTextView.setLayoutParams(params);
            emptyCartTextView.setGravity(Gravity.CENTER);
            emptyCartTextView.setText("Your cart is empty");
            courseCartLayout.addView(emptyCartTextView);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick(R.id.registerCourses)
    public void registerCourses() {
        Cart cart = cartDomainService.getCart(MainMenuActivity.USERNAME);
        if (cart == null || cart.getCourseInCart() == null || cart.getCourseInCart().size() == 0) {
            Toast.makeText(this, "Can't register because course cart is empty",Toast.LENGTH_SHORT).show();
        } else {
            List<Course> courseList = cartDomainService.getCouseList(MainMenuActivity.USERNAME).stream()
                    .map(courseDomainService::getCourse).collect(Collectors.toList());
            if (TimeUtil.validateTimeConflicts(courseList)) {
                Toast.makeText(this, "Can't register because course time conflict",Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.backSearchPage)
    public void backResultPage() {
        startActivity(new Intent(CartActivity.this, SearchActivity.class));
    }

    @OnClick(R.id.backHomePage)
    public void backHomePage() {
        startActivity(new Intent(CartActivity.this, MainMenuActivity.class));
    }

    @OnClick(R.id.goToSchedulePage)
    public void goToSchedulePage() {
        startActivity(new Intent(CartActivity.this, ScheduleActivity.class));
    }

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
