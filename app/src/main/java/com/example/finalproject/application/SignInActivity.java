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

public class SignInActivity extends AppCompatActivity {

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
        setContentView(R.layout.login_in_page);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.login)
    public void login() {
        boolean successful = userDomainService.login(loginUsername.getText().toString(),
                loginPassword.getText().toString());
        if (successful) {
            startActivity(new Intent(SignInActivity.this, MainMenuActivity.class));
        } else {
            if (userDomainService.validateUsernameExist(loginUsername.getText().toString())) {
                Toast.makeText(this, "Your password is incorrect", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Your username is not exist",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick(R.id.register)
    public void goToRegisterPage() {
        startActivity(new Intent(SignInActivity.this, RegisterActivity.class));
    }

}