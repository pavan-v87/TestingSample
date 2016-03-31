package com.example.tdd.login;

import android.support.test.espresso.IdlingResource;

import okhttp3.OkHttpClient;

/**
 * Created by Pavan.VijayaNar on 29-Mar-16.
 */
public class LoginServiceImpl implements Login.Service, IdlingResource, Runnable{

    public static final int NO_INTERNET =0, LOGIN_SUCCESS = 1, LOGIN_FAILED =2;

    public static int CONDITION;
    public static int MESSAGE;


    private Login.Callback loginCallback;

    private ResourceCallback idleResourceCallback;

    private boolean idle = true;

    public LoginServiceImpl(OkHttpClient client) {
        
    }

    @Override
    public void login(String uName, String pass, Login.Callback listener) {
        if (null != listener) {
            idle = false;
            loginCallback = listener;
            Thread thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switch (CONDITION) {
            case NO_INTERNET:
                loginCallback.onLoginFailed(MESSAGE);
                break;
            case LOGIN_SUCCESS:
                loginCallback.onLoginSuccess(MESSAGE);
                break;
            case LOGIN_FAILED:
                loginCallback.onLoginFailed(MESSAGE);
                break;
        }
        idle = true;
        if (null != idleResourceCallback) {
            idleResourceCallback.onTransitionToIdle();
        }
    }

    @Override
    public String getName() {
        return "DummyLoginService";
    }

    @Override
    public boolean isIdleNow() {
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.idleResourceCallback = callback;
    }
}
