package com.example.loginfirebase;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email, password;
    private Button signin, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        signin = (Button) findViewById(R.id.signin);
        signup = (Button) findViewById(R.id.signup);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        if(mAuth.getCurrentUser() != null) {
            finish();
            startActivity( new Intent(getApplicationContext(), SignIn.class));
        }
//        signin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//							String getEmail = email.getText().toString();
//							String getPassword = password.getText().toString();
//							callSignIn(getEmail, getPassword);
//            }
//        });

//        signup.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                String getEmail = email.getText().toString();
//                String getPassword = email.getText().toString();
//                createAccount(getEmail, getPassword);
//            }
//        });
    }

    public void signInClick(View view) {
	    String getEmail = email.getText().toString();
	    String getPassword = password.getText().toString();
	    email.onEditorAction(EditorInfo.IME_ACTION_DONE);
	    password.onEditorAction(EditorInfo.IME_ACTION_DONE);
	    callSignIn(getEmail, getPassword);
    }

    public void signUpClick(View view) {
	    String getEmail = email.getText().toString();
	    String getPassword = email.getText().toString();
	    email.onEditorAction(EditorInfo.IME_ACTION_DONE);
	    password.onEditorAction(EditorInfo.IME_ACTION_DONE);
	    createAccount(getEmail, getPassword);
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("test", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if(!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Create Account Failed",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            userProfile();
                            Toast.makeText(MainActivity.this, "Account Created",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("Test", "Account Created");
                        }
                    }
                });
    }

    private void userProfile() {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(email.getText().toString()).build();
            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Log.d("Test", "User Profile Updated");
                    }
                }
            });
        }
    }

    private void callSignIn(final String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Test", "Sign in is successful" + task.isSuccessful());

                if(!task.isSuccessful()) {
                    Log.d("Test", "Sign in with Email Failed", task.getException());
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(MainActivity.this, SignIn.class);
                    i.putExtra("email", email);
                    finish();
                    startActivity(i);
                }
            }
        });

    }
}
