package com.example.finalproject.utils;

import org.jsoup.Jsoup;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;


public class HtmlParseUtil {

    private static final String URL_PREFIX = "https://www.ratemyprofessors.com/ShowRatings.jsp?tid=";

    private static final HashMap<String, String> NAME_ID = new HashMap<String, String>() {{

    }};

    public String getProfessorPoint(String id) throws IOException {
        String url = URL_PREFIX + id;
        return Jsoup.parse(new URL(url), 30000).getElementsByClass("RatingValue__Numerator-qw8sqy-2").eq(0).text();

    }

    public String getProfessorDifficulty(String id) throws IOException {
        String url = URL_PREFIX + id;
        return Jsoup.parse(new URL(url), 30000).getElementsByClass("FeedbackItem__FeedbackNumber-uof32n-1").eq(0).text();
    }
}
