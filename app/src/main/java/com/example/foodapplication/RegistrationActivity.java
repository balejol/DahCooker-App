package com.example.foodapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {
    private EditText userNameEdt, passwordEdt, emailEdt;
    private Button registerBtn;

    public static List<User> userList = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        userNameEdt = findViewById(R.id.write_username);
        emailEdt = findViewById(R.id.write_mail);
        passwordEdt = findViewById(R.id.write_password);
        registerBtn = findViewById(R.id.rectangle_in);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String userName = userNameEdt.getText().toString();
                String email = emailEdt.getText().toString();
                String password = passwordEdt.getText().toString();

                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(RegistrationActivity.this, "Please enter a correct username", Toast.LENGTH_SHORT).show();
                }
                else if( TextUtils.isEmpty(password) || password.length() < 5 )
                {
                    Toast.makeText(RegistrationActivity.this, "Please enter a correct password", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(email)|| !isValidEmail(email))
                {
                    Toast.makeText(RegistrationActivity.this, "Please enter a correct email", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    registerUser(userName, email, password);
                    User user = new User(userName, email, password);
                    userList.add(user);

                }
            }
        });
    }

    private boolean isValidEmail(String email) {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        return email.matches(emailPattern);
    }
    private void registerUser(String userName, String email, String password) {

            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}