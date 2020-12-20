package com.example.finalproject.utils;


import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;

public class SortUtil {


    // sort course textview in the schedule activity
    public static List<TextView> sortTextViewMargin(List<TextView> textViews) {

        for (int i = 0; i < textViews.size() - 1; i++) {
            int low = convertParam(textViews.get(i)).topMargin;
            for (int j = i + 1; j < textViews.size(); j++) {
                if (convertParam(textViews.get(j)).topMargin < low) {
                    Collections.swap(textViews, i, j);
                    low = convertParam(textViews.get(j)).topMargin;
                }
            }
        }
        return textViews;
    }

    //  get layout param from a textview
    public static LinearLayout.LayoutParams convertParam(TextView textView) {
        return (LinearLayout.LayoutParams) textView.getLayoutParams();
    }

}
