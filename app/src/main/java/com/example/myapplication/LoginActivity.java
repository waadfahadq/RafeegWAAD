package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
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

import com.example.myapplication.admin_portal.MainActivityAdmin;
import com.example.myapplication.shopowner.MainActivityShopowner;
import com.example.myapplication.shopowner.shopowner_info;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    static String ID;
    final Context context = this;
    Button Login;
    static EditText UserName;
    EditText Password;
    TextView createACC;
    TextView forgetPass;
    private TextView newStore;
    static String EMAILP;
    int x;
    private boolean itIsActiveStore = false;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("User");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            Log.e("test","Test ad");
            checkStoreExist(firebaseAuth.getCurrentUser().getEmail(), new MyCallback() {
                @Override
                public void onCallback(boolean value) {
                    if(!value){
                        ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }


        Login = (Button) findViewById(R.id.loginButton);
        UserName = (EditText) findViewById(R.id.editTextID);
        Password = (EditText) findViewById(R.id.editTextPassword);
        Login.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);


        createACC = findViewById(R.id.signup_txt);
        createACC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Signup.class);
                startActivity(intent);

            }
        });

        newStore = findViewById(R.id.Shop_ownerٍSignup_txt);
        newStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Signup.class);
                intent.putExtra("ID", "Store creation");
                startActivity(intent);
            }
        });

        forgetPass = findViewById(R.id.forgot_password_tv);
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
                        .setPositiveButton("إرسال",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        firebaseAuth = FirebaseAuth.getInstance();

                                        String string = userInput.getText().toString();
                                        if (userInput == null) {

                                            if (string != null) {
                                                firebaseAuth.sendPasswordResetEmail(userInput.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                                        } else {
                                                            userInput.setError("كلمة المرور خاطئة !");
                                                            userInput.requestFocus();
                                                        }
                                                    }
                                                });
                                            }
                                        }//if


                                        else {
                                            userInput.setError("فضلًا أدخل بريدك الإلكتروني");
                                            userInput.requestFocus();
                                        }

                                    }
                                }).setNegativeButton("إلغاء", null);


                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();

            }
        });//end Forget Password button

    }//end onCreate


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginButton:
                final String EMAIL = UserName.getText().toString().trim();
                final String PASS = Password.getText().toString().trim();
                checkStoreExist(UserName.getText().toString().trim(), new MyCallback() {
                    @Override
                    public void onCallback(boolean value) {
                        if(value){
                            checkStoreActive(EMAIL,     new MyCallback() {
                                @Override
                                public void onCallback(boolean value) {
                                    if (value){
                                        storeLogin(EMAIL,PASS);
                                    }else{
                                        Toast.makeText(LoginActivity.this, "لم يتم تفعيل حسابك بعد أو انه محظور٬ الرجاء التفضل بزيارتنا لمزيد من المعلومات", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else{
                            othersLogin(EMAIL,PASS);
                        }
                    }
                });
                break;
        }
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
                Password.setError("فضلًا ادخل كلمة المرور");
                return false;

            }

            if (isEmpty(UserName)) {
                UserName.setError("فضلًا ادخل البريد الإلكتروني");
                return false;

            }

            if (!isEmail(UserName)) {
                UserName.setError("فضلًا ادخل بريد إلكتروني صحيح !");
                return false;

            }

            return true;
        }

        public void storeLogin(String email,String pass){
            firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                EMAILP = UserName.getText().toString();
                                ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                MySharedPreference.putString(LoginActivity.this, Constance.key.USER_EMAIL, EMAILP);
                                Intent intent = new Intent(getApplicationContext(), MainActivityShopowner.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, " كلمة المرور أو البريد الإلكتروني غير صحيح !", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

        public void othersLogin(String EMAIL,String PASS){
            final boolean validate = checkDataEntered();
            if (EMAIL.equalsIgnoreCase("admin") & PASS.equalsIgnoreCase("admin")) {
                Intent intent = new Intent(getApplicationContext(), MainActivityAdmin.class);
                startActivity(intent);
                finish();
            } else {
                if (validate) {
                    firebaseAuth.signInWithEmailAndPassword(EMAIL, PASS)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        EMAILP = UserName.getText().toString();
                                        ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        MySharedPreference.putString(LoginActivity.this, Constance.key.USER_EMAIL, EMAILP);
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, " كلمة المرور أو البريد الإلكتروني غير صحيح !", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    // showDialog();
                    //finish();
                }//close if
                else {
                    Toast.makeText(this, " كلمة المرور أو البريد الإلكتروني غير صحيح !", Toast.LENGTH_LONG).show();
                }//close else
            }//end else of admin checking
        }

        private void checkStoreExist(final String email, final MyCallback myCallback){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            Query query = rootRef.child("shipowners").orderByChild("email").equalTo(email.toLowerCase());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        myCallback.onCallback(true);
                    } else{
                        myCallback.onCallback(false);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });
        }
        private void checkStoreActive(final String email, final MyCallback myCallback){
        FirebaseDatabase.getInstance().getReference().child("shipowners").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    shopowner_info user = snapshot.getValue(shopowner_info.class);
                    if(user.getEmail().equalsIgnoreCase(email)) {
                        if (user.isActive()) {
                            myCallback.onCallback(true);
                            Log.e("Store " + String.valueOf(user.getEmail()), String.valueOf(user.isActive()));
                        } else {
                            myCallback.onCallback(false);
                            Log.e("Store " + String.valueOf(user.getEmail()), String.valueOf(user.isActive()));
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public interface MyCallback {
        void onCallback(boolean value);
    }
}

/*

 */