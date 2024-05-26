package com.example.foodapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	private View rectangle_signup;
	private View rectangle_signin;
	private EditText write_password;
	private EditText write_username;

	private TextView _showingError;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		rectangle_signin = findViewById(R.id.rectangle_in);
		rectangle_signup = findViewById(R.id.rectangle_up);
		write_password = findViewById(R.id.write_password);
		write_username = findViewById(R.id.write_username);

		_showingError = findViewById(R.id.error_message);

		rectangle_signin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (isDataValid(write_username.getText().toString(),
						write_password.getText().toString())) {
					Intent intent = new Intent(getBaseContext(), MainActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				} else {
					_showingError.setVisibility(View.VISIBLE);
				}
			}
		});

		rectangle_signup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getBaseContext(), RegistrationActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			}
		});
	}

	private boolean isDataValid(String name, String password) {
		for (User user : RegistrationActivity.userList) {
			if (user.getUserName().equals(name) && user.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}
}
