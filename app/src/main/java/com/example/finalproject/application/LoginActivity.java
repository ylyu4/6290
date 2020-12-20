package com.example.finalproject.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.finalproject.R;
import com.example.finalproject.service.UserDomainService;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private static final String USERNAME_KEY = "usernameKey";

    private final UserDomainService userDomainService = new UserDomainService(this);

    @BindView(R.id.loginUsername)
    EditText loginUsername;

    @BindView(R.id.loginPassword)
    EditText loginPassword;

    @BindView(R.id.login)
    Button login;

    @BindView(R.id.register)
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.login)
    public void login() {
        boolean successful = userDomainService.login(loginUsername.getText().toString(),
                loginPassword.getText().toString());
        if (successful) {
            Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
            Bundle parameter = new Bundle();
            parameter.putString(USERNAME_KEY, loginUsername.getText().toString());
            intent.putExtras(parameter);
            startActivity(intent);
            finish();
        } else {
            if (userDomainService.validateUsernameExist(loginUsername.getText().toString())) {
                Toast.makeText(this, "Password is incorrect", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Username does not exist",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    @OnClick(R.id.register)
    public void goToRegisterPage() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

}