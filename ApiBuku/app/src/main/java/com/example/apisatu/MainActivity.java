package com.example.apisatu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GithubAuthProvider;
import com.google.firebase.auth.OAuthProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText negaraInput;
    private TextView namaNegara;
    private TextView kodeNegara2;
    private TextView kodeNegara3;
    private TextView ibukotaNegara;
    private TextView wilayah;
    private TextView subwilayah;
    private TextView jumlahPenduduk;
    private Button lihat;
    private Button signOut;
    AmbilJson jsonPlaceHolderApi;
    GoogleSignInClient mGoogleSignInClient;
    Button signOutFb, logoutGithub;
    OAuthProvider.Builder provider;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        namaNegara = findViewById(R.id.namaNegara);
        kodeNegara2 = findViewById(R.id.kdNegara2);
        kodeNegara3 = findViewById(R.id.kdNegara3);
        ibukotaNegara = findViewById(R.id.ibukota);
        wilayah = findViewById(R.id.wilayah);
        subwilayah = findViewById(R.id.subWilayah);
        jumlahPenduduk = findViewById(R.id.jumlahPenduduk);
        negaraInput = findViewById(R.id.inputNamaNegara);
        lihat = findViewById(R.id.lihat);
        signOutFb = findViewById(R.id.logout_button);
        logoutGithub = findViewById(R.id.sign_out_github);

        FirebaseApp.initializeApp(this);

        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ambilData();
            }
        });
        provider = OAuthProvider.newBuilder("github.com");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        signOut = findViewById(R.id.sign_out_button);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.sign_out_button:
                        signOut();
                        break;
                    // ...
                }
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount x = GoogleSignIn.getLastSignedInAccount(this);


        if (x != null) {
            signOutFb.setVisibility(View.GONE);
            logoutGithub.setVisibility(View.GONE);
        }else if(firebaseUser!=null){
            signOutFb.setVisibility(View.GONE);
            signOut.setVisibility(View.GONE);
        }
        else {
            signOut.setVisibility(View.GONE);
            logoutGithub.setVisibility(View.GONE);
        }


    }


    public void ambilData() {
        String negara = negaraInput.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl("https://restcountries-v1.p.rapidapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(AmbilJson.class);

        Call<List<Pojo>> call = jsonPlaceHolderApi.getDetail("https://restcountries-v1.p.rapidapi.com/name/" + negara);
        call.enqueue(new Callback<List<Pojo>>() {
            @Override
            public void onResponse(Call<List<Pojo>> call, Response<List<Pojo>> response) {
                List<String> content = new ArrayList<String>();
                List<Pojo> posts = response.body();
                for (Pojo post : posts) {
                    String namaNegaraOutput = post.getName();
                    String ibukotaNegaraOutput = post.getCapital();
                    String kodeNegara2Output = post.getAlpha2code();
                    String kodeNegara3Output = post.getAlpha3code();
                    String wilayahOutput = post.getRegion();
                    String subWilayahOutput = post.getSubregion();
                    String jumlahPendudukOutput = post.getPopulation();


                    namaNegara.setText(namaNegaraOutput);
                    ibukotaNegara.setText(ibukotaNegaraOutput);
                    kodeNegara2.setText(kodeNegara2Output);
                    kodeNegara3.setText(kodeNegara3Output);
                    wilayah.setText(wilayahOutput);
                    subwilayah.setText(subWilayahOutput);
                    jumlahPenduduk.setText(jumlahPendudukOutput);
                }
            }

            @Override
            public void onFailure(Call<List<Pojo>> call, Throwable t) {

            }
        });
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null) {
                startActivity(new Intent(MainActivity.this, LoginDadi.class));
            } else {
            }
        }
    };

    public void logoutFacebook(View view){
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(MainActivity.this, LoginDadi.class));
                    }
                });
    }

    public void logoutGithub(View view){
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivity.this, LoginDadi.class));
    }

}
