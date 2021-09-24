package com.anonymous.loginsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText username, password, email, dob, country;
    RadioGroup gender;
    Button register, cancel;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        dob = findViewById(R.id.dateOfBirth);
        country = findViewById(R.id.country);
        gender = findViewById(R.id.gender);
        register = findViewById(R.id.register);
        cancel = findViewById(R.id.cancel);

        databaseHelper = new DatabaseHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameValue = username.getText().toString().trim();
                String passwordValue = password.getText().toString().trim();
                String emailValue = email.getText().toString().trim();
                String countryValue = country.getText().toString().trim();
                String dobValue = dob.getText().toString().trim();

                RadioButton checkedBtn = findViewById(gender.getCheckedRadioButtonId());
                String genderValue = checkedBtn.getText().toString();

                if(usernameValue.length() > 1){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("username", usernameValue);
                    contentValues.put("password", passwordValue);
                    contentValues.put("email", emailValue);
                    contentValues.put("country", countryValue);
                    contentValues.put("dob", dobValue);
                    contentValues.put("gender", genderValue);

                    databaseHelper.insertUser(contentValues);

                    Toast.makeText(RegisterActivity.this, "User Registered!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Enter The Values.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}