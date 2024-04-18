package com.example.foodapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	private View _bg__login_ek2;
	private View top;
	private TextView __app_name_;
	private View _bg__login_box_ek1;
	private View login_box_ek2;
	private TextView forgot_password_;
	private View _bg__sign_up_button_ek1;
	private View rectangle_signup;
	private TextView sign_up;
	private View _bg__sign_in_button_ek1;
	private View rectangle_signin;
	private TextView sign_in;
	private View _bg__password_ek1;
	private EditText write_password;
	private View ellipse_4;
	private TextView password_ek2;
	private View _bg__username_ek1;
	private EditText write_username;
	private View ellipse_2;
	private TextView username_ek2;
	private TextView welcome_;

	private TextView _showingError;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		_bg__login_ek2 = findViewById(R.id._bg_nude);
		top = findViewById(R.id.top);
		__app_name_ = findViewById(R.id._app_name);
		login_box_ek2 = findViewById(R.id.login_box_green);
		forgot_password_ = findViewById(R.id.forgot_password_);
		_bg__sign_up_button_ek1 = findViewById(R.id._bg__sign_up_button_ek1);
		rectangle_signin = findViewById(R.id.rectangle_in);
		rectangle_signup = findViewById(R.id.rectangle_up);
		_bg__sign_in_button_ek1 = findViewById(R.id._bg__sign_in_button_ek1);
		write_password = findViewById(R.id.write_password);
		//ellipse_4 = findViewById(R.id.ellipse);
		write_username = findViewById(R.id.write_username);
		//ellipse_2 = findViewById(R.id.ellipse);
		welcome_ = findViewById(R.id.welcome_);
		password_ek2 = findViewById(R.id.password);
		username_ek2 = findViewById(R.id.username);

		_showingError = findViewById(R.id.error_message);


		rectangle_signin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (isDataValid(write_username.getText().toString(),
						write_password.getText().toString())) {
					Intent intent = new Intent(getBaseContext(), MainActivity.class);
					startActivity(intent);
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
