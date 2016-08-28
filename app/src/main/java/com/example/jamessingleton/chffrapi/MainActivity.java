package com.example.jamessingleton.chffrapi;

import android.*;
import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Credentials;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.common.SignInButton;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.BufferedReader;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.Request;



//Add to git!
public class MainActivity extends AppCompatActivity {

    Context mContext = MainActivity.this;
    private AccountManager mAccountManager;
    private AuthPreferences authPreferences;
    private APIRequests apiRequests;
    EditText emailText;
    TextView responseView;
    ProgressBar progressBar;

    static final String API_URL = "https://api.comma.ai/v1/auth/?access_token=";
    static final String ChffrMe_URL = "https://api.comma.ai/v1/me/";
    static final String SCOPE = "https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/plus.me";
    private static final int AUTHORIZATION_CODE = 1993;
    private static final int ACCOUNT_CODE = 1601;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        responseView = (TextView) findViewById(R.id.responseView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAccountManager = AccountManager.get(this);
        authPreferences = new AuthPreferences(this);
        apiRequests = new APIRequests(authPreferences);
        final Context context = this;

        invalidateToken();
        requestToken();
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (authPreferences.getUser() != null && authPreferences.getToken() != null) {
                    System.out.println(authPreferences.getToken());
                    doCoolAuthenticatedStuff();
//                    Intent intent = new Intent(context, NavDrawerActivity.class);
//                    startActivity(intent);

                    try {
                        apiRequests.run();
                        responseView.setText(apiRequests.MyRoutes);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    chooseAccount();
                }
            }
        });
//        Button queryButton = (Button) findViewById(R.id.queryButton);
//
//        queryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isNetworkAvailable() == true) {
//                    Intent intent = new Intent(context, NavDrawerActivity.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(MainActivity.this, "No Network Service, please check your WiFi or Mobile Data Connection", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        boolean dontShowDialog = sharedPref.getBoolean("DONT_SHOW_DIALOG", false);
        if (!dontShowDialog) {
            WifivsDataDialog myDiag = new WifivsDataDialog();
            myDiag.show(getFragmentManager(), "WiFi");
            myDiag.setCancelable(false);
        }
    }


    private void doCoolAuthenticatedStuff() {
        Log.e("AuthApp", authPreferences.getToken());
    }

    private void chooseAccount() {
        Intent intent = AccountManager.newChooseAccountIntent(null, null, new String[]{"com.google"}, false, null, null, null, null);
        startActivityForResult(intent, ACCOUNT_CODE);
    }

    public void requestToken() {
        Account userAccount = null;
        String user = authPreferences.getUser();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        for (Account account : mAccountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE)) {
            if (account.name.equals(user)) {
                userAccount = account;
                break;
            }
        }

        mAccountManager.getAuthToken(userAccount, "oauth2:" + SCOPE, null, this, new OnTokenAcquired(), null);
    }

    private void invalidateToken() {
        AccountManager mAccountManager = AccountManager.get(this);
        mAccountManager.invalidateAuthToken("com.google", authPreferences.getToken());
        authPreferences.setToken(null);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == AUTHORIZATION_CODE) {
                System.out.println(requestCode);
                requestToken();
            } else if (requestCode == ACCOUNT_CODE) {
                String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                authPreferences.setUser(accountName);

                // invalidate old tokens which might be cached. we want a fresh
                // one, which is guaranteed to work
                invalidateToken();

                requestToken();
            }
        }
    }

    public class OnTokenAcquired implements AccountManagerCallback<Bundle> {
        @Override
        public void run(AccountManagerFuture<Bundle> result) {
            try {
                Bundle bundle = result.getResult();
                Intent launch = (Intent) bundle.get(AccountManager.KEY_INTENT);
                if (launch != null) {
                    startActivityForResult(launch, AUTHORIZATION_CODE);
                } else {
                    String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);


                    authPreferences.setToken(token);
                    System.out.println(authPreferences);
                    doCoolAuthenticatedStuff();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
            }
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.e("Network Testing", "Available");
            return true;
        }
        Log.e("Network Testing", "Not Available");
        return false;
    }
}
