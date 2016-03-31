package com.example.tdd.login;


import com.example.tdd.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.tdd.login.Utils.getResponse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Pavan.VijayaNar on 29-Mar-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginServiceImplTest {

   Login.Service loginService;

    @Mock
    private OkHttpClient client;

    @Mock
    private Login.Callback mockedCallback;

    ArgumentCaptor<Callback> argumentCaptor = ArgumentCaptor.forClass(Callback.class);

    @Before
    public void initialize() {
        loginService = new LoginServiceImpl(client);
    }

    @Test
    public void shouldNotEnqueueRequestForNullCallBack() {
        Call call = mock(Call.class);
        when(client.newCall(any(Request.class))).thenReturn(call);

        loginService.login("abc", "xyz", null);
        verifyZeroInteractions(call);
    }

    @Test
    public void shouldCallbackOnFailure() {
        Call call = mock(Call.class);

        when(client.newCall(any(Request.class))).thenReturn(call);

        loginService.login("abc", "xyz", mockedCallback);

        verify(call).enqueue(argumentCaptor.capture());

        argumentCaptor.getValue().onFailure(call, new IOException());

        verify(mockedCallback).onLoginFailed(R.string.noConnectivityMessage);
    }


    @Test
    public void shouldCallbackOnLoginSuccess() throws IOException {

        Call call = mock(Call.class);
        when(client.newCall(any(Request.class))).thenReturn(call);

        loginService.login("abc", "xyz", mockedCallback);

        verify(call).enqueue(argumentCaptor.capture());

        Response response = getResponse(200);

        argumentCaptor.getValue().onResponse(call, response);

        verify(mockedCallback).onLoginSuccess(R.string.loginSuccessMessage);
    }


    @Test
    public void shouldCallbackOnAuthFailed() throws IOException {

        Call call = mock(Call.class);
        when(client.newCall(any(Request.class))).thenReturn(call);

        loginService.login("abc", "xyz", mockedCallback);

        verify(call).enqueue(argumentCaptor.capture());

        Response response = getResponse(404);

        argumentCaptor.getValue().onResponse(call, response);

        verify(mockedCallback, times(0)).onLoginSuccess(anyInt());

        verify(mockedCallback).onLoginFailed(R.string.loginFailedMessage);
    }
}