package com.example.tdd.login;

import com.example.tdd.R;

/**
 * Created by Pavan.VijayaNar on 29-Mar-16.
 */
public class LoginPresenterImpl implements Login.Callback {

    private final Login.View view;
    private final Login.Service service;

    public boolean loggedIn = false;

    public LoginPresenterImpl(Login.View view, Login.Service service) {
        this.view = view;
        this.service = service;
    }

    public boolean doLogin() {
        String userName = view.getUserName();
        String password = view.getPassword();
        if (null == userName || userName.length()<=0) {
            view.showUserNameError(R.string.userNameEmptyMessage);
        }
        else if (null == password || password.length()<=0) {
            view.showPasswordError(R.string.passwordEmptyMessage);
        }
        else {
            service.login(userName, password, this);
        }
        return false;
    }


    @Override
    public void onLoginFailed(int strResId) {
        view.loginFailed(strResId);
    }

    @Override
    public void onLoginSuccess(int strResId) {
        view.loginSuccess(strResId);
        loggedIn = true;
    }
{
/*



        boolean loginInitiated = false;


        loginInitiated = true;
        return loginInitiated;
    }

    @Override
    public void onLoginFailed(final int strResId) {
    }

    @Override
    public void onLoginSuccess(final int strResId) {
    }*/
}

}
