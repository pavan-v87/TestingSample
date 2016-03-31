package com.example.tdd;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.tdd.Utils.viewIsNotVisible;
import static com.example.tdd.Utils.viewIsVisible;
import static com.example.tdd.Utils.withError;

@RunWith(AndroidJUnit4.class)
public class ApplicationTest {

@Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void validateLoginScreenWhenLaunched() {
        onView(withId(R.id.loginButton)) // View Matcher
                .check(matches(viewIsVisible())) // View Assertion
                .perform(click()); // View Action
        onView(withId(R.id.loginStatus)).check(matches(viewIsNotVisible())); // Progress indicator should be hidden at this point
    }


    @Test
    public void validateErrorShownWhenThereIsNoUserName() {
        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.userName)).check(matches(withError(
                InstrumentationRegistry.getInstrumentation().getTargetContext().getString(R.string.userNameEmptyMessage))));

    }

    @Test
    public void validateErrorShownWhenThereIsNoPassword() {
        onView(withId(R.id.userName)).perform(replaceText("pavan.v"));
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.password)).check(matches(withError(
                InstrumentationRegistry.getInstrumentation().getTargetContext().getString(R.string.passwordEmptyMessage))));
    }
}