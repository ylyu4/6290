package com.example.finalproject.utils;

import org.jsoup.Jsoup;
import java.io.IOException;
import java.net.URL;



public class HtmlParseUtil {

    private static final String URL_PREFIX = "https://www.ratemyprofessors.com/ShowRatings.jsp?tid=";


    public static String getProfessorPoint(String id) throws IOException {
        String url = URL_PREFIX + id;
        return Jsoup.parse(new URL(url), 30000).getElementsByClass("RatingValue__Numerator-qw8sqy-2").eq(0).text();

    }

    public static String getProfessorDifficulty(String id) throws IOException {
        String url = URL_PREFIX + id;
        return Jsoup.parse(new URL(url), 30000).getElementsByClass("FeedbackItem__FeedbackNumber-uof32n-1").eq(0).text();
    }
}
