package com.example.yasmin.prodigylogin;

import android.net.Uri;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;



public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListenr;
    private static final String TAG = "EmailPassword";
    private EditText EmailEditText ;
    private EditText PassEditText ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //firebase
        EmailEditText = (EditText) findViewById(R.id.editText);
        PassEditText = (EditText) findViewById(R.id.editText2);

        mAuth = FirebaseAuth.getInstance();
        mAuthListenr = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is sign in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    //user is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        //FaceBook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        //logger.logPurchase(BigDecimal.valueOf(4.32), Currency.getInstance("USD"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListenr);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListenr != null)
        {
            mAuth.removeAuthStateListener(mAuthListenr);
        }
    }


    public void Click (View v)
    {



        String Email = EmailEditText.getText().toString();
        String Pass = PassEditText.getText().toString();
        SignUp(Email,Pass);

    }

    public  void ClickSignIn (View v)
    {
        String Email = EmailEditText.getText().toString();
        String Pass = PassEditText.getText().toString();
        SignIn(Email,Pass);
    }

    public void SignIn (String email , String pass)
    {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Log.d(TAG, "signInWithEmail:failed", task.getException());
                    Toast.makeText(MainActivity.this, "Faild",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Log.d(TAG, "Weeeeeeee");
                    Toast.makeText(MainActivity.this, "^___^", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void SignUp (String email , String pass)
    {
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "auth Faild", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void GetUser ()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }
    }



}
