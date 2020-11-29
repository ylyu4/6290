package com.example.finalproject.application;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject.R;
import com.example.finalproject.model.Course;
import com.example.finalproject.service.CartDomainService;
import com.example.finalproject.service.CourseDomainService;
import com.example.finalproject.utils.TextUtil;

import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartActivity extends AppCompatActivity {


    private final CartDomainService cartDomainService = new CartDomainService(this);

    private final CourseDomainService courseDomainService = new CourseDomainService(this);


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
            courseButton.setText("Drop");
            courseButton.setOnClickListener(view -> {
                cartDomainService.dropCourse(MainMenuActivity.USERNAME, classTextView.getText().toString());
                startActivity(new Intent(CartActivity.this, CartActivity.class));
            });
            linearLayout.addView(courseButton);

            courseCartLayout.addView(linearLayout);

        }
    }

    @OnClick(R.id.registerCourses)
    public void registerCourses() {

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
        startActivity(new Intent(CartActivity.this, MainMenuActivity.class));
    }

}
