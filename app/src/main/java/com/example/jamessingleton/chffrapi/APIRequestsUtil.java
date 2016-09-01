package com.example.jamessingleton.chffrapi;

import com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffapi.common.JacksonWrapper;
import com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data.PersonalInfo;
import com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data.Route;
import com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data.RoutesWrapper;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by James Singleton on 8/23/2016.
 */

public final class APIRequestsUtil {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String API_URL = "https://api.comma.ai/v1/auth/?access_token=";
    private static final String ChffrMe_URL = "https://api.comma.ai/v1/me/";
    private static final String MyRoutes_URL = "https://api.comma.ai/v1/me/routes/";
    private static AuthPreferences mAuthPreferences;
    private static String commatoken;
    private static  ObjectMapper objectMapper = JacksonWrapper.getObjectMapper();
    private static  JsonFactory factory = objectMapper.getFactory();
    private static JsonParser jp = null;
    private static PersonalInfo personalInfo = null;
    private static RoutesWrapper routesWrapper = null;

    public static APIRequestResponseListener mListener;


    private APIRequestsUtil(){};

    public static void setAuthPreferences(AuthPreferences authPreferences){
        mAuthPreferences = authPreferences;
    }

    public static  void setOnNetWorkListener(APIRequestResponseListener listener) {
        mListener = listener;
    }

    public static void run() throws Exception {
        Request request = new Request.Builder()
                .url(API_URL + mAuthPreferences.getToken())
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

                                String myInfo = responseMe.body().string();
                                jp = factory.createParser(myInfo);
                                personalInfo = objectMapper.readValue(jp, PersonalInfo.class);


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

                                String routes = responseRoutes.body().string();
                                jp = factory.createParser(routes);
                                routesWrapper = objectMapper.readValue(jp, RoutesWrapper.class);

                            if (mListener != null) {
                                mListener.onResponse(response);
                            }

                        }
                    });
                }
            }

        });
    }

    public static Map<String, Route> getRoutes(){
        if(mListener !=null && routesWrapper !=null)
        return routesWrapper.getRoutes();
        return null;
    }

    public static PersonalInfo getPersonalInfo(){
        if(mListener !=null && personalInfo !=null)
        return personalInfo;
        return null;
    }

    public interface APIRequestResponseListener {
        void onFailure(Request request, Throwable throwable);
        void onResponse(Response response);
    }

}