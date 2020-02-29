package com.example.voteapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.voteapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

//import com.google.android.material.snackbar.Snackbar;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText editName, editEmail, editPassword;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editName = (EditText)findViewById(R.id.editName);
        editEmail = (EditText)findViewById(R.id.editEmail);
        editPassword = (EditText)findViewById(R.id.editPassword);

        findViewById(R.id.textView3).setOnClickListener(this);
        findViewById(R.id.btnRegister).setOnClickListener(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    private void registerUser(View view){
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
//        Validating user input.
        if(name.isEmpty()){
            Snackbar.make(view, "Username should not be empty. ", Snackbar.LENGTH_LONG).show();
            return;
        }
        if(email.isEmpty()){
            Snackbar.make(view, "Email address field should not be Empty. ", Snackbar.LENGTH_LONG).show();
            return;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Snackbar.make(view, "Email address formatting is incorrect. ", Snackbar.LENGTH_LONG).show();
            return;
        }
        if(password.isEmpty()){
            Snackbar.make(view, "Password Field is Empty. ", Snackbar.LENGTH_LONG).show();
            return;
        }if(password.length() < 8){
            Snackbar.make(view, "Password Field should not be less than 8 letters. ", Snackbar.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignUpActivity.this, "User registration successful", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                            startActivity(new Intent(SignUpActivity.this, ElectionList.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                        } else {
                            // If sign in fails, display a message to the user.
//                            Toast.makeText(SignUpActivity.this, "User registration failed", Toast.LENGTH_SHORT).show();
                            if(task.getException()instanceof FirebaseAuthUserCollisionException){
//                                Snackbar.make(, "Password Field should not be less than 8 letters. ", Snackbar.LENGTH_LONG).show();
                                Toast.makeText(SignUpActivity.this, "Email already, exists.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textView3:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.btnRegister:
                registerUser(view);
                break;
        }
    }
}
