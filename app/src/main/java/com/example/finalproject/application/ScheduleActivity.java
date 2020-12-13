package com.example.finalproject.application;

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
import com.example.finalproject.service.CourseDomainService;
import com.example.finalproject.service.UserDomainService;
import com.example.finalproject.utils.SortUtil;
import com.example.finalproject.utils.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.time.temporal.ChronoUnit.MINUTES;


public class ScheduleActivity extends AppCompatActivity {


    private static HashMap<String, Integer> marginMap = new HashMap<String, Integer>() {{
        put("19:10", 415);
        put("16:30", 310);
        put("15:45", 270);
        put("19:30", 430);
        put("09:30", 15);
        put("09:35", 17);
    }};


    private final UserDomainService userDomainService = new UserDomainService(this);

    private final CourseDomainService courseDomainService = new CourseDomainService(this);


    @BindView(R.id.content)
    LinearLayout content;

    @BindView(R.id.sunday)
    LinearLayout sundayLayout;

    @BindView(R.id.monday)
    LinearLayout mondayLayout;

    @BindView(R.id.tuesday)
    LinearLayout tuesdayLayout;

    @BindView(R.id.wednesday)
    LinearLayout wednesdayLayout;

    @BindView(R.id.thursday)
    LinearLayout thursdayLayout;

    @BindView(R.id.friday)
    LinearLayout fridayLayout;

    @BindView(R.id.saturday)
    LinearLayout saturdayLayout;

    @BindView(R.id.startOver)
    Button startOver;



    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_page);
        ButterKnife.bind(this);
        createUI();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Course> getRegisteredCourse() {
        List<String> courseNameList = userDomainService.getCourseListByUsername(MainMenuActivity.USERNAME);
        if (courseNameList == null || courseNameList.size() == 0) {
            return null;
        }
        return courseNameList.stream().map(courseDomainService::getCourse).collect(Collectors.toList());
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createUI() {
        List<Course> courseList = getRegisteredCourse();
        if (courseList != null) {
            for (int i = 0; i < courseList.size(); i++) {
                Course course = courseList.get(i);
                List<LinearLayout> scheduleDay = addScheduleForCourse(course);
                for (LinearLayout linearLayout : scheduleDay) {
                    TextView textView = new TextView(this);
                    LinearLayout.LayoutParams params;
                    long timeDiff = MINUTES.between(TimeUtil.parseTime(course.getStartTime()), TimeUtil.parseTime(course.getEndTime()));
                    if (timeDiff == 150) {
                        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 115, getResources().getDisplayMetrics())));
                    } else if (timeDiff == 90) {
                        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics())));
                    } else {
                        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics())));
                    }
                    params.setMargins(0, ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginMap.get(course.getStartTime()), getResources().getDisplayMetrics())),
                            0, 0);
                    textView.setLayoutParams(params);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,9);
                    textView.setText(String.format("ISTM %s   %s-%s", course.getCourseNumber(), course.getStartTime(), course.getEndTime()));
                    textView.setBackgroundResource(R.color.colorYellow);
                    textView.setGravity(Gravity.CENTER);
                    List<TextView> textViewsTmpList = new ArrayList<>();
                    for (int j = 0; j < linearLayout.getChildCount(); j++) {
                        textViewsTmpList.add((TextView) linearLayout.getChildAt(j));
                    }
                    textViewsTmpList.add(textView);
                    linearLayout.removeAllViews();
                    SortUtil.sortTextViewMargin(textViewsTmpList);
                    int totalMargin = 0;
                    for (int index = 0; index < textViewsTmpList.size(); index++) {
                        TextView tv = textViewsTmpList.get(index);
                        if (totalMargin > 0) {
                            LinearLayout.LayoutParams newParam = SortUtil.convertParam(tv);
                            newParam.setMargins(0, newParam.topMargin - totalMargin, 0, 0);
                            tv.setLayoutParams(newParam);
                        }
                        linearLayout.addView(tv);
                        totalMargin += SortUtil.convertParam(tv).topMargin;
                        totalMargin += SortUtil.convertParam(tv).height;
                    }

                }
            }
        }

    }

    private List<LinearLayout> addScheduleForCourse(Course course) {
        List<LinearLayout> res = new ArrayList<>();
        String[] days = course.getDay().split("/");
        for (String day : days) {
            if (day.equals("M")) {
                res.add(mondayLayout);
            }
            if (day.equals("T")) {
                res.add(tuesdayLayout);
            }
            if (day.equals("W")) {
                res.add(wednesdayLayout);
            }
            if (day.equals("Th")) {
                res.add(thursdayLayout);
            }
            if (day.equals("F")) {
                res.add(fridayLayout);
            }
            if (day.equals("Sa")) {
                res.add(saturdayLayout);
            }
            if (day.equals("Su")) {
                res.add(sundayLayout);
            }
        }

        return res;
    }

    @OnClick(R.id.startOver)
    public void startOver() {
        startActivity(new Intent(ScheduleActivity.this, MainMenuActivity.class));
    }


}

