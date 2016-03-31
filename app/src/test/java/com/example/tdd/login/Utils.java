package com.example.tdd.login;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Pavan.VijayaNar on 29-Mar-16.
 */
final class Utils {

    // Helper methods
    static Response getResponse(int code, String sessionId, String msg) {
        RequestBody reqbody = RequestBody.create(null, new byte[0]);
        Request request = new Request.Builder()
                .url(LoginServiceImpl.LOGIN_URL)
                .addHeader("Authorization", "Basic admin:admin")
                .post(reqbody)
                .build();

        String jsonBody = createJsonResponse(sessionId, msg);

        Response.Builder builder = new Response.Builder();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        return builder.protocol(Protocol.HTTP_1_1)
                .request(request)
                .code(code)
                .body(ResponseBody.create(JSON, jsonBody))
                .build();
    }

    // Helper methods
    protected static Response getResponse(int code) {
        return getResponse(code, "Dummy1", "Dummy2");
    }

    static String createJsonResponse(String sessionId, String msg) {
        JSONObject json = new JSONObject();
        if (null != sessionId) {
            try {
                json.put(LoginServiceImpl.SESSION_ID, sessionId);
                json.put(LoginServiceImpl.MESSAGE_KEY, msg);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json.toString();//"{sessionId:\""+ sessionId + "\"," + "message:\""+ msg + "\"}";
    }
}
