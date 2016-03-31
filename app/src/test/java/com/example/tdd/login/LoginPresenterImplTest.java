package com.example.tdd.login;

import com.example.tdd.R;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Pavan.VijayaNar on 29-Mar-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterImplTest {
    private LoginPresenterImpl loginPresenter;

    @Mock
    private Login.View mockedView;

    @Mock
    private Login.Service mockedService;

    ArgumentCaptor<Login.Callback> argumentCaptor = ArgumentCaptor.forClass(Login.Callback.class);

    @Before
    public void initialize() {
        loginPresenter = new LoginPresenterImpl(mockedView, mockedService);
    }

    @Test
    public void shouldShowErrorWhenUserNameIsEmpty() {
        when(mockedView.getUserName()).thenReturn("");
        loginPresenter.doLogin();
        verify(mockedView).showUserNameError(R.string.userNameEmptyMessage); // Add only what is required
    }

    @Test
    public void shouldShowErrorWhenPasswordIsEmpty() {
        when(mockedView.getUserName()).thenReturn("John");
        when(mockedView.getPassword()).thenReturn("");
        loginPresenter.doLogin();
        verify(mockedView).showPasswordError(R.string.passwordEmptyMessage);
    }


    @Test
    public void shouldCallServiceLogin() {
        when(mockedView.getUserName()).thenReturn("John");
        when(mockedView.getPassword()).thenReturn("Doe");
        loginPresenter.doLogin();
        verify(mockedService).login(eq("John"), eq("Doe"), any(Login.Callback.class));
    }

    @Test public void shouldCallLoginFailed() {
        when(mockedView.getUserName()).thenReturn("John");
        when(mockedView.getPassword()).thenReturn("Doe");
        Assert.assertEquals(false, loginPresenter.loggedIn);
        loginPresenter.doLogin();
        verify(mockedService).login(eq("John"), eq("Doe"), argumentCaptor.capture());

        argumentCaptor.getValue().onLoginFailed(R.string.loginFailedMessage);

        verify(mockedView).loginFailed(R.string.loginFailedMessage);
        Assert.assertEquals(false, loginPresenter.loggedIn);
    }

    @Test
    public void shouldCallLoginSuccess() {
        when(mockedView.getUserName()).thenReturn("John");
        when(mockedView.getPassword()).thenReturn("Doe");


        Assert.assertEquals(false, loginPresenter.loggedIn);

        loginPresenter.doLogin();

        verify(mockedService).login(eq("John"), eq("Doe"), argumentCaptor.capture());

        argumentCaptor.getValue().onLoginSuccess(R.string.loginSuccessMessage);

        verify(mockedView, times(0)).loginFailed(anyInt());
        verify(mockedView, times(1)).loginSuccess(R.string.loginSuccessMessage);
        Assert.assertEquals(true, loginPresenter.loggedIn);
    }
}