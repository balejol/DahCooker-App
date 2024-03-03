package com.example.foodapplication;
/*
 *	This content is generated from the API File Info.
 *	(Alt+Shift+Ctrl+I).
 *
 *	@desc
 *	@file 		login
 *	@date 		Sunday 03rd of March 2024 01:01:19 PM
 *	@title 		Page 1
 *	@author
 *	@keywords
 *	@generator 	Export Kit v1.3.figma
 *
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

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



		_bg__login_ek2 = (View) findViewById(R.id._bg__login_ek2);
		top = (View) findViewById(R.id.top);
		__app_name_ = (TextView) findViewById(R.id.__app_name_);
		login_box_ek2 = (View) findViewById(R.id.login_box_ek2);
		forgot_password_ = (TextView) findViewById(R.id.forgot_password_);
		_bg__sign_up_button_ek1 = (View) findViewById(R.id._bg__sign_up_button_ek1);
		rectangle_34 = (View) findViewById(R.id.rectangle_34);
		_bg__sign_in_button_ek1 = (View) findViewById(R.id._bg__sign_in_button_ek1);
		rectangle_27 = (View) findViewById(R.id.rectangle_27);
		write_password = (EditText) findViewById(R.id.write_password);
		ellipse_4 = (View) findViewById(R.id.ellipse_4);
		password_ek2 = (TextView) findViewById(R.id.password_ek2);
		write_username = (EditText) findViewById(R.id.write_username);
		ellipse_2 = (View) findViewById(R.id.ellipse_2);
		username_ek2 = (TextView) findViewById(R.id.username_ek2);
		welcome_ = (TextView) findViewById(R.id.welcome_);


		//custom code goes here


		// Set onClickListener for rectangle_27
		rectangle_27.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				//if(isDataValid(write_username.toString(),
				//		write_password.toString()))
				if(isDataValid(write_username.getText().toString(),
						write_password.getText().toString()))
				{
					Intent intent = new Intent(getBaseContext(), MainActivity.class);
					startActivity(intent);

				}
				else
				{
					_textView3 = findViewById(R.id.textView3);

					//_textView3.setText(write_username.getText().toString());

					_textView3.setVisibility(View.VISIBLE);


				}
			}


		});

	}

	private boolean isDataValid(String name, String password) {

		if (Objects.equals(name, "aa") && Objects.equals(password, "password"))
		{
			return true;
		}
		return false;
	}
}
	
	