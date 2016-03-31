package com.example.tdd.login;

import android.support.test.espresso.IdlingResource;

import com.example.tdd.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Pavan.VijayaNar on 29-Mar-16.
 */
public class LoginServiceImpl implements Login.Service, Callback, IdlingResource {

    public static final String SESSION_ID = "SessionId";
    public static final String MESSAGE_KEY = "Message";

    public static final String LOGIN_URL = "http://192.168.2.1";


    private final OkHttpClient httpClient;
    private Login.Callback loginCallback;
    private IdlingResource.ResourceCallback idleResourceCallback;

    private boolean idle = true;

    public LoginServiceImpl(OkHttpClient client) {
        this.httpClient = client;
    }

    @Override
    public void login(String uName, String pass, Login.Callback listener) {
        if (null != listener) {
            idle = false;

            loginCallback = listener;
            RequestBody reqbody = RequestBody.create(null, new byte[0]);
            Request request = new Request.Builder()
                    .url(LOGIN_URL)
                    .addHeader("Authorization", "Basic "+ uName +":" + pass)
                    .post(reqbody)
                    .build();


            Call call = httpClient.newCall(request);
            call.enqueue(this);
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        loginCallback.onLoginFailed(R.string.noConnectivityMessage);
        idle = true;

        if (null != idleResourceCallback) {
            idleResourceCallback.onTransitionToIdle();
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        int code = response.code();
        String sessionId = null;
        String message = null;

        String responseString = response.body().string();
        try {
            JSONObject json = new JSONObject(responseString);
            sessionId = json.getString(SESSION_ID);
            message = json.getString(MESSAGE_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (code > 300) { // Login was not success
            loginCallback.onLoginFailed(R.string.loginFailedMessage);
        }
        else {
            loginCallback.onLoginSuccess(R.string.loginSuccessMessage);
        }

        idle = true;

        if (null != idleResourceCallback) {
            idleResourceCallback.onTransitionToIdle();
        }
    }

    @Override
    public String getName() {
        return "LoginService";
    }

    @Override
    public boolean isIdleNow() {
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(IdlingResource.ResourceCallback callback) {
        this.idleResourceCallback = callback;
    }
}
