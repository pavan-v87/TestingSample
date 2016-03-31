package com.example.tdd;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.tdd.login.LoginServiceImpl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.tdd.Utils.viewIsVisible;

/**
 * Created by Pavan.VijayaNar on 30-Mar-16.
 */

@RunWith(AndroidJUnit4.class)
public class LoginTest {

@Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void validateNoInternet() {
        login("", "", R.string.noConnectivityMessage);
    }


    @Test
    public void validateLoginFailed() {
        login("guest", "guest", R.string.loginFailedMessage);
    }

    @Test
    public void validateLoginSuccess() {
        login("admin", "admin", R.string.loginSuccessMessage);
    }

    private void login(String userName, String password, int msgToBeValidated) {
        onView(withId(R.id.userName)).perform(replaceText(userName)); // Has some issue with typeText - Login Button Click is not recognized
        onView(withId(R.id.password)).perform(replaceText(password));

        onView(withId(R.id.loginButton)) // View Matcher
                .check(matches(viewIsVisible())) // View Assertion
                .perform(click()); // View Action

        LoginServiceImpl loginService = activityTestRule.getActivity().getLoginService();
        registerIdlingResources(loginService);

        onView(withId(R.id.loginStatus)).check(matches(viewIsVisible())).check(matches(withText(msgToBeValidated))); // Check if login as failed

        unregisterIdlingResources(loginService);
    }
}
