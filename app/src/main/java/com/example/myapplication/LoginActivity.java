package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    static String ID ;
    final Context context = this ;
    Button Login ;
    static EditText UserName ;
    EditText  Password;
    TextView createACC;
    TextView forgetPass;
    static String EMAILP ;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){

            ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Intent intent =new Intent(getApplicationContext() , MainActivity.class );
            startActivity(intent);
            finish();
        }


        Login= (Button)findViewById(R.id.login_btn);
        UserName=(EditText)findViewById(R.id.username_login);
        Password=(EditText)findViewById(R.id.password_login);
        Login.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);



        createACC = findViewById(R.id.createACC);
        createACC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,Signup.class);
                startActivity(intent);

            }
        });

        forgetPass = findViewById(R.id.forget);
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.forget, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.email2restpass);
                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Send",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {

                                        firebaseAuth = FirebaseAuth.getInstance();

                                        String string =  userInput.getText().toString();
                                        if(userInput == null){

                                            if(string != null) {
                                                firebaseAuth.sendPasswordResetEmail(userInput.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                                        }
                                                        else {
                                                            userInput.setError("Error With This Password  ");
                                                            userInput.requestFocus();
                                                        }
                                                    }
                                                });
                                            }}//if

                                        else if(string != null) {
                                            firebaseAuth.sendPasswordResetEmail(userInput.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                                    }
                                                    else {
                                                        userInput.setError("Error With This Password  ");
                                                        userInput.requestFocus();
                                                    }
                                                }
                                            });
                                        }
                                        else {
                                            userInput.setError("Please enter The Email Address !!");
                                            userInput.requestFocus();
                                        }

                                    }
                                }).setNegativeButton("Cencel",null);




                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();

            }
        });//end Forget Password button

    }//end onCreate



    @Override
    public void onClick(View view){


        switch(view.getId()){
            case R.id.login_btn:

////
                String EMAIL = UserName.getText().toString().trim();
                String PASS = Password.getText().toString().trim();





                boolean validate = checkDataEntered();

                if(validate) {
                    firebaseAuth.signInWithEmailAndPassword(EMAIL,PASS)
                            .addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {


                                        EMAILP = UserName.getText().toString();
                                        ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        MySharedPreference.putString(LoginActivity.this , Constance.key.USER_EMAIL,EMAILP);
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {

                                        Toast.makeText(LoginActivity.this, " Email or password wrong", Toast.LENGTH_LONG).show();

                                    }
                                } });

                    // showDialog();

                    //finish();

                }//close if

                else {

                    Toast.makeText(this, " Email or password wrong", Toast.LENGTH_LONG).show();
                }//close else
                break;




        }//end switch()


    }



    public void onPause(){
        super.onPause();
        finish();
    }



    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean checkDataEntered() {


        if (isEmpty(Password)) {
            Password.setError("password is required!");
            return false;

        }

        if (isEmpty(UserName)) {
            UserName.setError("Email is required!");
            return false;

        }

        if (!isEmail(UserName)) {
            UserName.setError("Enter valid email!");
            return false;

        }


        return true;
    }


}
