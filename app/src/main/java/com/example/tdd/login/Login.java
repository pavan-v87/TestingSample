package com.example.tdd.login;

/**
 * Created by Pavan.VijayaNar on 29-Mar-16.
 */
public interface Login {
    interface View {

        String getUserName();

        void showUserNameError(int strResId);

        String getPassword();

        void showPasswordError(int strResId);

        void loginFailed(int strResId);

        void loginSuccess(int strResId);
    }
    interface Callback {

        void onLoginFailed(int strResId);

        void onLoginSuccess(int strResId);
    }
    interface Service {

        void login(String uName, String pass, Callback listener);
    }
}
