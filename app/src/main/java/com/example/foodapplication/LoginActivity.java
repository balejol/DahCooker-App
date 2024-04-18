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
	private View rectangle_34;
	private TextView sign_up;
	private View _bg__sign_in_button_ek1;
	private View rectangle_27;
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

	TextView _textView3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		_bg__login_ek2 = findViewById(R.id._bg__login_ek2);
		top = findViewById(R.id.top);
		__app_name_ = findViewById(R.id.__app_name_);
		login_box_ek2 = findViewById(R.id.login_box_ek2);
		forgot_password_ = findViewById(R.id.forgot_password_);
		_bg__sign_up_button_ek1 = findViewById(R.id._bg__sign_up_button_ek1);
		rectangle_34 = findViewById(R.id.rectangle_34);
		_bg__sign_in_button_ek1 = findViewById(R.id._bg__sign_in_button_ek1);
		rectangle_27 = findViewById(R.id.rectangle_27);
		write_password = findViewById(R.id.write_password); // Corrected ID
		ellipse_4 = findViewById(R.id.ellipse_4);
		write_username = findViewById(R.id.write_username);
		ellipse_2 = findViewById(R.id.ellipse_2);
		welcome_ = findViewById(R.id.welcome_);
		password_ek2 = findViewById(R.id.password); // Corrected ID
		username_ek2 = findViewById(R.id.username); // Corrected ID

		// Set onClickListener for rectangle_27
		rectangle_27.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (isDataValid(write_username.getText().toString(),
						write_password.getText().toString())) {
					Intent intent = new Intent(getBaseContext(), MainActivity.class);
					startActivity(intent);
				} else {
					_textView3 = findViewById(R.id.textView3);
					_textView3.setVisibility(View.VISIBLE);
				}
			}
		});

		rectangle_34.setOnClickListener(new View.OnClickListener() {
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
