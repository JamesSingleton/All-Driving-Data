package com.example.jamessingleton.chffrapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by James Singleton on 8/23/2016.
 */

public class APIRequests {
    private final OkHttpClient client = new OkHttpClient();
    static final String API_URL = "https://api.comma.ai/v1/auth/?access_token=";
    static final String ChffrMe_URL = "https://api.comma.ai/v1/me/";
    static final String MyRoutes_URL = "https://api.comma.ai/v1/me/routes/";
    private final AuthPreferences authPreferences;
    String commatoken;
    String commaMyInfo;
    String MyRoutes;

    public APIRequests(AuthPreferences authPreferences) {
        this.authPreferences = authPreferences;
    }

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url(API_URL + authPreferences.getToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }


            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                try
                {
                    String token = response.body().string();
                    JSONObject json = new JSONObject(token);
                    commatoken = json.getString("access_token");
                } catch (JSONException e)
                {

                }

                if(response.isSuccessful())
                {
                    final Request dataRequest = new Request.Builder()
                            .header("content-type", "application/x-www-form-urlencoded")
                            .header("authorization", "JWT "+ commatoken)
                            .url(ChffrMe_URL).build();

                    client.newCall(dataRequest).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response responseMe) throws IOException {
                            if (!responseMe.isSuccessful()) throw new IOException("Unexpected code " + responseMe);

                            Headers responseHeaders = responseMe.headers();
                            for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                            }
                            try
                            {
                                String myInfo = responseMe.body().string();
                                JSONObject json = new JSONObject(myInfo);
                                commaMyInfo = json.getString("points");
                                System.out.println("Comma Points: " +commaMyInfo);
                            } catch (JSONException e)
                            {

                            }
                        }
                    });
                    final Request routeRequest = new Request.Builder()
                            .header("content-type", "application/x-www-form-urlencoded")
                            .header("authorization", "JWT "+ commatoken)
                            .url(MyRoutes_URL).build();

                    client.newCall(routeRequest).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response responseRoutes) throws IOException {
                            if (!responseRoutes.isSuccessful()) throw new IOException("Unexpected code " + responseRoutes);

                            Headers responseHeaders = responseRoutes.headers();
                            for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                            }
                            try
                            {
                                String myInfo = responseRoutes.body().string();
                                JSONObject json = new JSONObject(myInfo);
                                MyRoutes = json.toString();
                                System.out.println("URL: " +MyRoutes);
                            } catch (JSONException e)
                            {

                            }
                        }
                    });
                }
            }

        });
    }
}
