package com.example.loginpageofrastreo;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.LENGTH_SHORT;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthSateListner;
    @BindView(com.example.loginpageofrastreo.R.id.input_email)
    EditText _emailText;
    @BindView(com.example.loginpageofrastreo.R.id.input_password) EditText _passwordText;
    @BindView(com.example.loginpageofrastreo.R.id.btn_login)
    Button _loginButton;
    @BindView(com.example.loginpageofrastreo.R.id.link_signup)
    TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.loginpageofrastreo.R.layout.activity_login);
        ButterKnife.bind(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mAuth= FirebaseAuth.getInstance();
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               Log.v(TAG,"we are in login page");

                login();
                Log.v(TAG,"we come out of login page");

            }
        });
        //final Activity currentActivity=LoginActivity.this;
        Toast.makeText(LoginActivity.this,"you choose login", LENGTH_SHORT).show();
        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Toast.makeText(LoginActivity.this,"you choose signup", LENGTH_SHORT).show();
              //  currentActivity.finish();
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                //             startActivity(intent);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(com.example.loginpageofrastreo.R.anim.push_left_in, com.example.loginpageofrastreo.R.anim.push_left_out);
            }
        });
      Log.d(TAG,"not in signup");
        mAuthSateListner= new FirebaseAuth.AuthStateListener(){


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser=mAuth.getCurrentUser();
                if(mFirebaseUser!=null)
                {
                    Toast.makeText(LoginActivity.this,"You are Logged In", LENGTH_SHORT).show();
                    Intent i=new Intent(LoginActivity.this,Main2Activity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Please Login", LENGTH_SHORT).show();
                }


            }
        };
       }
//    private void updateUI(FirebaseUser user)
//    {
//       if(user!=null)
//       {
//           mStatusTextView.set
//       }
//    }
        public void login() {
        Log.v(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                com.example.loginpageofrastreo.R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
            final int[] flag = {0};
        String email = _emailText.getText().toString();
            System.out.println(email);

        String password = _passwordText.getText().toString();
            System.out.println(password);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "YOU LOGGED IN FAILED565465", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "failed");
                 //onLoginFailed();
                   // login();
                    flag[0] =1;}
                else {
                    Toast.makeText(LoginActivity.this,"YOU LOGGED IN SUCESSFULLY", Toast.LENGTH_SHORT).show();
                    FirebaseUser user=mAuth.getCurrentUser();
                    //updateUI()
                    Log.v(TAG, "succesfully");
                    startActivity(new Intent(LoginActivity.this,Main2Activity.class));
                    finish();
                }

            }
        });



        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        //
                         if(flag[0]==0)
                        onLoginSuccess();
                         else

                         onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
      }


      @Override
      protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
      }

     @Override
      public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
      }

     public void onLoginSuccess() {
        _loginButton.setEnabled(false);
        startActivity(new Intent(LoginActivity.this,Main2Activity.class));
        finish();
      }

      public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
      }

      public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}