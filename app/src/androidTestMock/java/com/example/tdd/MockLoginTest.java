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
public class MockLoginTest {

@Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private void login(int msgToBeValidated) {
        onView(withId(R.id.userName)).perform(replaceText("John")); // Has some issue with typeText - Login Button Click is not recognized
        onView(withId(R.id.password)).perform(replaceText("Doe"));

        onView(withId(R.id.loginButton)) // View Matcher
                .check(matches(viewIsVisible())) // View Assertion
                .perform(click()); // View Action

        onView(withId(R.id.loginStatus)).check(matches(viewIsVisible())).check(matches(withText(R.string.loadingMessage))); // Check if login as failed


        LoginServiceImpl loginService = activityTestRule.getActivity().getLoginService();
        registerIdlingResources(loginService);

        onView(withId(R.id.loginStatus)).check(matches(viewIsVisible())).check(matches(withText(msgToBeValidated))); // Check if login as failed

        unregisterIdlingResources(loginService);
    }

    @Test
    public void validateNoInternet() {

        LoginServiceImpl.CONDITION = LoginServiceImpl.NO_INTERNET;
        LoginServiceImpl.MESSAGE = R.string.noConnectivityMessage;

        login(R.string.noConnectivityMessage);
    }


    @Test
    public void validateLoginFailed() {

        LoginServiceImpl.CONDITION = LoginServiceImpl.LOGIN_FAILED;
        LoginServiceImpl.MESSAGE = R.string.loginFailedMessage;

        login(R.string.loginFailedMessage);
    }

    @Test
    public void validateLoginSuccess() {

        LoginServiceImpl.CONDITION = LoginServiceImpl.LOGIN_SUCCESS;
        LoginServiceImpl.MESSAGE = R.string.loginSuccessMessage;

        login(R.string.loginSuccessMessage);
    }
}
