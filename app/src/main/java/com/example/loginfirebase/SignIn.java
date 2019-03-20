package com.example.loginfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
	private FirebaseAuth mAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);

		mAuth = FirebaseAuth.getInstance();

		Intent intent = getIntent();
		String email = intent.getStringExtra("email");
		TextView textView = (TextView) findViewById(R.id.textView);
		textView.setText(email);
	}

	public void signOutClick(View view) {
		mAuth.signOut();
		Intent i = new Intent(SignIn.this, MainActivity.class);
		finish();
		startActivity(i);
	}
}
