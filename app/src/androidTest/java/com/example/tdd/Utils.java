package com.example.tdd;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by Pavan.VijayaNar on 30-Mar-16.
 */
public final class Utils {
    static Matcher<View> viewIsVisible() {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                return View.VISIBLE == view.getVisibility();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("View Should be visible");
            }
        };
    }

    static Matcher<View> viewIsNotVisible() {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                return View.VISIBLE != view.getVisibility();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("View Should be hidden");
            }
        };
    }

    static Matcher<View> withError(final String expected) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof EditText)) {
                    return false;
                }
                EditText editText = (EditText) view;
                CharSequence error = editText.getError();
                boolean matches = false;
                if (!TextUtils.isEmpty(error)) {
                    matches = error.toString().equals(expected);
                }
                return matches;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Error Not set: " + expected);
            }
        };
    }
}
