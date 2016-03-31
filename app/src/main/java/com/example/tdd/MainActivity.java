package com.example.tdd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tdd.login.Login;
import com.example.tdd.login.LoginPresenterImpl;
import com.example.tdd.login.LoginServiceImpl;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements Login.View{

    private EditText userName;
    private EditText password;

    private LoginPresenterImpl presenter;
    private LoginServiceImpl loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);

        loginService = new LoginServiceImpl(new OkHttpClient());
        presenter = new LoginPresenterImpl(this, loginService);

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter.doLogin()) {
                    setProgressMessage(R.string.loadingMessage);
                }
            }
        });
    }

    @Override
    public String getUserName() {
        return userName.getText().toString();
    }

    @Override
    public void showUserNameError(int strResId) {
        userName.setError(getString(strResId));
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
    }

    @Override
    public void showPasswordError(int strResId) {
        password.setError(getString(strResId));
    }

    @Override
    public void loginFailed(final int strResId) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setProgressMessage(strResId);
            }
        });
    }

    @Override
    public void loginSuccess(final int strResId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setProgressMessage(strResId);
            }
        });
    }

    public LoginServiceImpl getLoginService() {
        return loginService;
    }

    private void setProgressMessage(int resId) {
        TextView textView = (TextView) findViewById(R.id.loginStatus);
        textView.setVisibility(View.VISIBLE);
        textView.setText(resId);
    }
}
