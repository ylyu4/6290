package com.example.finalproject.application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject.R;
import com.example.finalproject.model.User;
import com.example.finalproject.service.UserDomainService;
import com.example.finalproject.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    private final UserDomainService userDomainService = new UserDomainService(this);

    @BindView(R.id.registerUsername)
    TextView registerUsername;

    @BindView(R.id.registerPassword)
    TextView registerPassword;

    @BindView(R.id.createAccount)
    Button createAccount;

    @BindView(R.id.signIn)
    Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.createAccount)
    public void createAccount() {
        String username = registerUsername.getText().toString();
        String password = registerPassword.getText().toString();
        if (!Utils.validateUsernameLength(username)) {
            Toast.makeText(this, "The length of username is invalid", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Utils.isAlphaNumeric(username)) {
            Toast.makeText(this, "The username should only contain digits and letters",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (userDomainService.validateUsernameExist(username)) {
            Toast.makeText(this, "this username was registered", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Utils.validatePasswordLength(password)) {
            Toast.makeText(this, "The length of password is invalid",Toast.LENGTH_SHORT).show();
            return;
        }

        userDomainService.register(new User(username, password));
        Toast.makeText(this, "Create account successfully",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.signIn)
    public void goToSignInPage() {
        startActivity(new Intent(RegisterActivity.this, SignInActivity.class));
    }
}